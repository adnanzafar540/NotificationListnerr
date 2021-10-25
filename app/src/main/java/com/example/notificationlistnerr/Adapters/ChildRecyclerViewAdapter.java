package com.example.notificationlistnerr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationlistnerr.MainActivity;
import com.example.notificationlistnerr.Model;
import com.example.notificationlistnerr.R;
import com.example.notificationlistnerr.ShowAllNotifications;

import java.util.List;

public class  ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.ViewHolder> {
        private List<Model> arrayList;
        Context mContext;
        int limitOfItems=5;

        public ChildRecyclerViewAdapter(List<Model> arrayList, Context mContext) {
            this.arrayList = arrayList;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_chid, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Model m=arrayList.get(i);
            viewHolder.txt_name.setText(m.getName());
            viewHolder.date.setText(m.getText());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Model mainModl=arrayList.get(i);
                    String pakage_name= String.valueOf(mainModl.getPackaename());
                    Intent i = new Intent(mContext, ShowAllNotifications.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("Pakage Name", pakage_name);
                    String ApplicationName= MainActivity.getApplicationName(pakage_name,mContext);
                    i.putExtra("App Name", ApplicationName);
                    mContext.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            if(arrayList.size() > limitOfItems){
                return limitOfItems;
            }
            else
            {
                return arrayList.size();
            }

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_name;
            TextView date;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                 txt_name = (TextView) itemView.findViewById(R.id.Itemname);
                 date = (TextView) itemView.findViewById(R.id.Itemtext);
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

