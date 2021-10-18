package com.example.notificationlistnerr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

