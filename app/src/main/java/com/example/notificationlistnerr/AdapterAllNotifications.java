package com.example.notificationlistnerr;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAllNotifications  extends RecyclerView.Adapter<AdapterAllNotifications.ViewHolder> {
    private List<Model> arrayList;
    Context mContext;
    int size=20;
    int ScrollItemSize;

    public AdapterAllNotifications(List<Model> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.size=size;
    }

    @NonNull
    @Override
    public AdapterAllNotifications.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_show_all_notifications, viewGroup, false);
        return new AdapterAllNotifications.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull AdapterAllNotifications.ViewHolder holder, int position) {

        try {
            Model m = arrayList.get(position);
            holder.txt_name.setText(m.getName());
            holder.txt_detail.setText(m.getText());
            holder.date_time.setText(m.getPosttime());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(m.getPackaename());
                        mContext.startActivity(launchIntent);
                    } catch (Exception e) {

                    }
                }
            });

        } catch (Exception e) {
        }
        ;
    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        TextView txt_detail;
        TextView date_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.Itemname);
            txt_detail = (TextView) itemView.findViewById(R.id.Itemtext);
            date_time = itemView.findViewById(R.id.date_txt);


        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
