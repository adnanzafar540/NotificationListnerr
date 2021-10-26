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
import java.util.List;

import static android.content.ContentValues.TAG;

public class ShowAllNotifications extends AppCompatActivity {
    AdapterAllNotifications adapter;
    Database db;
    static int size=10;
    RecyclerView rv_main;
    TextView pakage_name;
    ImageView imageView;
    String pakagename;
    String Appname;
    List<Model> list;
    List<Model> listOf20elements;
    LinearLayout linearLayout_child;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_all_notifications);
        db=new Database(this);
        rv_main=findViewById(R.id.rv_main1);

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
        list=MainActivity.reverseList(db.checkPakageName_GetData(pakagename));
        if(list.size()>=20){
        listOf20elements=new ArrayList<>();
        for(int i=0; i<20; i++){
            Model m=list.get(i);
            listOf20elements.add(m);
        }}else {
            listOf20elements=list;
        }
        adapter = new AdapterAllNotifications((listOf20elements),getApplicationContext());
        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        rv_main.setLayoutManager(mLayoutManager);
        rv_main.setAdapter(adapter);
        rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    totalItemCount = mLayoutManager.getItemCount();
                    fetchData(MainActivity.reverseList(db.checkPakageName_GetData(pakagename)),totalItemCount);
                }
            }
        });
       /* rv_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            Log.v("...", "Last Item Wow !");
                            fetchData(MainActivity.reverseList(db.checkPakageName_GetData(pakagename)),totalItemCount);

                        }

                }
            }
        });**/

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
       public void fetchData(List<Model> list,int totalItemCount){
        int Iteratelimit;
           int limit;
           List<Model> newList=new ArrayList<>();
           if(totalItemCount>=20 && list.size()>=20) {
                limit = totalItemCount + 20;
               if(limit>=list.size()){
                   Iteratelimit=list.size();
               }else {
                   Iteratelimit=limit;
               }
               for (int i = totalItemCount ; i <Iteratelimit; i++) {
                   Model model = list.get(i);
                   listOf20elements.add(model);
               }
               adapter.notifyDataSetChanged();




           }
}
}

