package com.example.notificationlistnerr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationlistnerr.Databases.Database;

import java.util.ArrayList;

public class ShowAllNotifications extends AppCompatActivity {
    AdapterAllNotifications adapter;
    Database db;
    RecyclerView rv_main;
    TextView pakage_name;
    ImageView imageView;
    String pakagename;
    String Appname;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_all_notifications);
        db=new Database(this);
        rv_main=findViewById(R.id.rv_main);
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
}
