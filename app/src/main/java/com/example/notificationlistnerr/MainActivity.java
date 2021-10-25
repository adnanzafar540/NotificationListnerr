package com.example.notificationlistnerr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.notificationlistnerr.Databases.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_main;
    ParentRecyclerViewAdapter adapter;
    ArrayList<Model> modelList;
    Database db;
    SwipeRefreshLayout swipeRefreshLayout;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelList = new ArrayList<Model>();
        db = new Database(this);
        rv_main = findViewById(R.id.rv_main);
        assessPermissions();
        if (!db.isDatabaseEmpty()) {
            adapter = new ParentRecyclerViewAdapter(sortData(), getApplicationContext());
            rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rv_main.setAdapter(adapter);
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String postime = intent.getStringExtra("PostTime");
            Model model = new Model();
            model.setName(title);
            model.setText(text);
            model.setPosttime(postime);
            model.setPackaename(pack);
            if (shouldDisplayNotification(db.checkPakageName_GetData(pack), model)) {
                db.insertData(model);
            }
            adapter = new ParentRecyclerViewAdapter(sortData(), getApplicationContext());
            rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rv_main.setAdapter(adapter);
        }
    };

    public List<List<Model>> sortData() {
        List<Model> Test_data = db.readAllData();
        Set<String> pakageName = new HashSet<>();
        List<List<Model>> main_innerlist = new ArrayList<>();
        List<Model> child_innerlist = new ArrayList<>();
        for (Model item : Test_data) {
            pakageName.add(item.packaename);
        }
        for (String value : pakageName) {
            child_innerlist = reverseList(db.checkPakageName_GetData(value));


            main_innerlist.add(child_innerlist);
        }

        return main_innerlist;

    }

    public static List<Model> reverseList(List<Model> list) {
        Collections.reverse(list);
        return list;
    }


    public boolean shouldDisplayNotification(List<Model> list, Model newmodel) {
        if (newmodel.getName() == null) {
            return false;
        }
        for (Model model : list) {

            if (model.getName().equals(newmodel.getName())) {
                if (model.getPosttime().equals(newmodel.getPosttime())) {
                    if (model.getText().equals(newmodel.getText())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void onRefresh() {
        fillNotificationsData();
    }

    public void fillNotificationsData() {
        ParentRecyclerViewAdapter adapter1 = new ParentRecyclerViewAdapter(sortData(), getApplicationContext());
        rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv_main.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @SuppressLint("ResourceType")
    private void assessPermissions() {
        if (isPermissionRequired()) {
            requestNotificationPermission();
        } else {

        }
    }


    public boolean isPermissionRequired() {
        ComponentName cn = new ComponentName(this, NotificationService.class);
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        final boolean enabled = flat != null && flat.contains(cn.flattenToString());
        return !enabled;
    }

    @SuppressLint("ResourceType")
    private void requestNotificationPermission() {
        AlertDialog.Builder Builder;
        Builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.drawable.roundbutton));
        Builder.setTitle("Permision Restricted");
        Builder.setMessage("Allow notifications read permission from the settings!");
        Builder.setNeutralButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        });
        AlertDialog alert = Builder.create();
        alert.show();

    }
    public static String getApplicationName(String pakagename,Context context){
        String applicationName;
        final PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo( pakagename, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");

        return applicationName;
    }
}