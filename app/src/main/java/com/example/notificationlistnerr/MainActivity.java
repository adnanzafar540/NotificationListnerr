package com.example.notificationlistnerr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.notificationlistnerr.Databases.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
	ListView list;
	RecyclerView rv_main;
	ParentRecyclerViewAdapter adapter;
	ArrayList<Model> modelList;
	Button btn;
	Database db;
	String iterateOverPakageName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		modelList = new ArrayList<Model>();
		db = new Database(this);
		rv_main = findViewById(R.id.rv_main);
		if (!db.isDatabaseEmpty()) {
			adapter = new ParentRecyclerViewAdapter(sortData(), getApplicationContext());
			rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
			rv_main.setAdapter(adapter);
		}

		Intent intent = new Intent(
				"android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
		startActivity(intent);
		LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
	}

	private BroadcastReceiver onNotice = new BroadcastReceiver() {

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
			if(shouldDisplayNotification(db.checkPakageName_GetData(pack),model)) {
				db.insertData(model);
			}
			adapter = new ParentRecyclerViewAdapter(sortData(), getApplicationContext());
			rv_main.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
			rv_main.setAdapter(adapter);
		}
	};

	public List<List<Model>> sortData() {
		List<Model> Test_data = db.readAllData();
		Set<String> pakageName = new HashSet<>();
		List<List<Model>> main_innerlist = new ArrayList<>();
		List<Model> child_innerlist = new ArrayList<>();
		for (Model item : Test_data) {
			pakageName.add(item.packaename);
		}
		for (String value : pakageName) {
			child_innerlist = reverseList(db.checkPakageName_GetData(value));


			main_innerlist.add(child_innerlist);
		}

		return main_innerlist;

	}

	public static List<Model> reverseList(List<Model> list) {
		Collections.reverse(list);
		return list;
	}


	public  boolean shouldDisplayNotification(List<Model> list, Model newmodel) {
		for (Model model : list) {
			if (model.getName()==(newmodel.getName())) {
				if (model.getPosttime() == newmodel.getPosttime()) {
					if (model.getText() == newmodel.getText()) {
						return false;
					}
				}
			} else {
				return true;
			}
		}
		return true;
	}
}