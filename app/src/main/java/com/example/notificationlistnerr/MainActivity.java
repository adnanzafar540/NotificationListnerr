package com.example.notificationlistnerr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.notificationlistnerr.Databases.Database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView list;
    CustomListAdapter adapter;
    ArrayList<Model> modelList;
    Button btn;
    Database db;
    String iterateOverPakageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelList = new ArrayList<Model>();
        db=new Database(this);
        if(db.isDatabaseEmpty()) {
            adapter = new CustomListAdapter(getApplicationContext(), sortData());
        }
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
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
                Model model = new Model();
                model.setName(title);
                model.setText(text);
                model.setPosttime(postime);
                model.setPackaename(pack);
                model.setNotificationChannelGroup(NotificationChannelGroup);
                db.insertData(model);

                    adapter = new CustomListAdapter(getApplicationContext(), sortData());
                    list=(ListView)findViewById(R.id.list);
                    list.setAdapter(adapter);



        }
    };
       public List<List<Model>> sortData(){
           List<Model> Test_data=db.readAllData();
           Set<String> pakageName=new HashSet<>();
           List<List<Model>> main_innerlist=new ArrayList<>();
           List<Model> child_innerlist=new ArrayList<>();
           for (Model item:Test_data){
               pakageName.add(item.packaename);
           }
           for(String value:pakageName){
               main_innerlist.add( db.checkPakageName_GetData(value));
           }

           return main_innerlist;

       }
}