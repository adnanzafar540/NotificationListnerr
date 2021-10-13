package com.example.notificationlistnerr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CustomListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Model> modelList;
    Set<String> setPakageName;


    public CustomListAdapter(Context context, ArrayList<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView txt = (TextView) rowView.findViewById(R.id.Itemtext);
        TextView date = (TextView) rowView.findViewById(R.id.date_txt);
        Model m = modelList.get(position);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClickedPakage(m.getPackaename());

            }
        });   txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClickedPakage(m.getPackaename());

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClickedPakage(m.getPackaename());

            }
        });

        //TextView NotificationChannelGroup = (TextView) rowView.findViewById(R.id.notichannel);

        //NotificationChannelGroup.setText(m.getNotificationChannelGroup());
        txtTitle.setText(m.getName());
        txt.setText(m.getText());
        date.setText(m.getPosttime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageView.setImageDrawable(getIcon(m.packaename));

        }

        /*if(m != null && m.getImage() !=null)
            imageView.setImageBitmap(m.getImage());

        **/return rowView;

    }

        public Drawable getIcon (String pakagename){
            Drawable appIcon = null;
            try {

                appIcon = context.getPackageManager().getApplicationIcon(pakagename);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return appIcon;
        }
        public void checkNotificationForPakagename(String pakagename){
        setPakageName=new HashSet<>();
            setPakageName.add(pakagename);



        }
        public void openClickedPakage(String pakagename){
            PackageManager pm = context.getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(pakagename);
            if (intent != null) {
                context.startActivity(intent);
            }
        }
        }

