package com.example.notificationlistnerr;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notificationlistnerr.Adapters.ChildRecyclerViewAdapter;

import java.util.List;

public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.ViewHolder> {

        private static final String TAG = "PostAdapter";

    Context context;
    List<List<Model>> modelList;
    String applicationName;

        public ParentRecyclerViewAdapter( List<List<Model>> modelList,Context context) {
            this.modelList = modelList;
            this.context = context;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_parent_rv, viewGroup, false);
            return new ViewHolder(view);
        }

    @NonNull



    @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        if(modelList!=null){
            Model mainModl=modelList.get(i).get(0);
            String PackName=mainModl.getPackaename();
            //String substringPackName = PackName.substring(PackName.lastIndexOf(".")+1);

            viewHolder.pakage_name.setText(getApplicationName(PackName));
                viewHolder.imageView.setImageDrawable(getIcon(mainModl.packaename));
                viewHolder.date_time.setText(mainModl.getPosttime());

                    ChildRecyclerViewAdapter childRecyclerviewAdapter = new ChildRecyclerViewAdapter(modelList.get(i), context);
                    viewHolder.rv_parent.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    viewHolder.rv_parent.setAdapter(childRecyclerviewAdapter);
                    childRecyclerviewAdapter.notifyDataSetChanged();



        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Model mainModl=modelList.get(position).get(0);
                String pakage_name= String.valueOf(mainModl.getPackaename());
                Intent i = new Intent(context, ShowAllNotifications.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Pakage Name", pakage_name);
                String ApplicationName=getApplicationName(pakage_name);
                i.putExtra("App Name", ApplicationName);
                context.startActivity(i);
            }
        });
        }

        @Override
        public int getItemCount() {
            return modelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RecyclerView rv_parent;
            TextView date_time;
            TextView pakage_name;
            ImageView imageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                date_time = itemView.findViewById(R.id.date_txt);
                pakage_name = itemView.findViewById(R.id.packagename);
                rv_parent = itemView.findViewById(R.id.rv_parent);
                imageView = (ImageView) itemView.findViewById(R.id.icon);

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
    public Drawable getIcon (String pakagename){
        Drawable appIcon = null;
        try {
            appIcon = context.getPackageManager().getApplicationIcon(pakagename);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }
              public String getApplicationName(String pakagename){
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



