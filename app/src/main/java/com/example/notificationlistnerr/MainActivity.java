package com.example.notificationlistnerr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notificationlistnerr.Databases.Database;

import java.security.Permission;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    CustomListAdapter adapter;
    ArrayList<Model> modelList;
    Button btn;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelList = new ArrayList<Model>();
        db=new Database(this);
       // btn=findViewById(R.id.btn_save);
        adapter = new CustomListAdapter(getApplicationContext(),db.readAllData());
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // addDatainDatabase();
            }
        });
        Intent intent = new Intent(
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
             String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String postime = intent.getStringExtra("PostTime");
            String NotificationChannelGroup = intent.getStringExtra("NotificationChannelGroup");
          //  Drawable drawable = intent.getExtra("icon");
            //int id = intent.getIntExtra("icon",0);

            Context remotePackageContext = null;

                Model model = new Model();
                model.setName(title);
                model.setText(text);
                model.setPosttime(postime);
                model.setPackaename(pack);
                model.setNotificationChannelGroup(NotificationChannelGroup);
                db.insertData(model);

                    adapter = new CustomListAdapter(getApplicationContext(), db.readAllData());
                    list=(ListView)findViewById(R.id.list);
                    list.setAdapter(adapter);



        }
    };
    public void addDatainDatabase(Model m){
        db.insertData(m);

    }
}