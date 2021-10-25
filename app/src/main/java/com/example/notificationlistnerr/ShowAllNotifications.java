package com.example.notificationlistnerr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationlistnerr.Databases.Database;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ShowAllNotifications extends AppCompatActivity {
    AdapterAllNotifications adapter;
    Database db;
    static int size=15;
    RecyclerView rv_main;
    TextView pakage_name;
    ImageView imageView;
    String pakagename;
    String Appname;
    LinearLayout linearLayout_child;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_all_notifications);
        db=new Database(this);
        rv_main=findViewById(R.id.rv_main1);
        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    size=size+20;
                    Log.d(TAG, "onScrolled: ");
                    // Scrolling up
                } else {
                    Log.d(TAG, "onScrolled: ");
                    // Scrolling down
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });
        linearLayout_child=findViewById(R.id.ll_child);
        pakage_name = findViewById(R.id.packagename);
        imageView = findViewById(R.id.icon);


    //    Bundle extras = getIntent().getExtras();
        Intent intent = this.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle extras = intent.getExtras();
        if (extras == null) {
            finish();
            return;
        }
        pakagename = extras.getString("Pakage Name");
        Appname = extras.getString("App Name");
        pakage_name.setText(Appname);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageView.setImageDrawable(getIcon_setname(pakagename));
        }
        adapter = new AdapterAllNotifications((MainActivity.reverseList(db.checkPakageName_GetData(pakagename))),getApplicationContext());
        rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv_main.setAdapter(adapter);

        linearLayout_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pakagename);
                    startActivity(launchIntent);
                }catch (Exception e){

                }
            }
        });


    }
    public Drawable getIcon_setname (String pakagename){
        Drawable appIcon = null;
        try {
            appIcon = this.getPackageManager().getApplicationIcon(pakagename);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
return appIcon;
    }
    public void openApp(String pakagename){


    }
}

