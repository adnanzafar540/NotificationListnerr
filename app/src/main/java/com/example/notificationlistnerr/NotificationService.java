package com.example.notificationlistnerr;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.notificationlistnerr.Databases.Database;

import java.io.ByteArrayOutputStream;
import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {
	private String TAG = this .getClass().getSimpleName() ;
	Context context ;
	String title;
	Database db;
	String text1;
	public static final  int          USER_OWNER = 0;
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager notificationManager;

	@Override
	public void onCreate () {
		super .onCreate() ;
		context = getApplicationContext() ;
	}
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@SuppressLint("UseCompatLoadingForDrawables")
	@Override
	public void onNotificationPosted (StatusBarNotification sbn) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);


		if ((sbn.getNotification().flags & Notification.FLAG_GROUP_SUMMARY) == 0) {
			addNotification(sbn); }
	}
	@Override
	public void onNotificationRemoved (StatusBarNotification sbn) {
		Log.i("Msg","Notification Removed");

	}
	public static String getDate(long milliSeconds, String dateFormat)
	{
		// Create a DateFormatter object for displaying date in specified format.
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
	public static void cancelNotification(Context context, int notifyId) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		boolean cancelSummary = false;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N ) {
			StatusBarNotification[] statusBarNotifications = notificationManager.getActiveNotifications();
			String groupKey = null;

			for (StatusBarNotification statusBarNotification : statusBarNotifications) {
				if (notifyId == statusBarNotification.getId()) {
					groupKey = statusBarNotification.getGroupKey();
					break;
				}
			}

			int counter = 0;
			for (StatusBarNotification statusBarNotification : statusBarNotifications) {
				if (statusBarNotification.getGroupKey().equals(groupKey)) {
					counter++;
				}
			}

			if (counter == 2) {
				cancelSummary = true;
			}
		}

		if (cancelSummary) {
			//notificationManager.cancel(summeryId);
			Log.d("msg", "cancelNotification: ");

		} else {
			notificationManager.cancel(notifyId);
		}
	}

public void addNotification(StatusBarNotification sbn){

	{
		String pack = sbn.getPackageName();
		String ticker = "";
		if (sbn.getNotification().tickerText != null) {
			ticker = sbn.getNotification().tickerText.toString();
		}
		Bundle extras = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			extras = sbn.getNotification().extras;

		}
		long NotificationPosttime = sbn.getPostTime();
		String NotPosttime = getDate(NotificationPosttime, "EEEE/MM hh:mm aa");
		if (extras.get(Notification.EXTRA_TITLE) != null) {
			title = (String) extras.get(Notification.EXTRA_TITLE);
		}

		try {
			Object text = extras.get(Notification.EXTRA_TEXT);
			text1 = text.toString();
		} catch (Exception e) {

		}


		Intent msgrcv = new Intent("Msg");
		msgrcv.putExtra("package", pack);
		msgrcv.putExtra("ticker", ticker);
		msgrcv.putExtra("title", title);
		msgrcv.putExtra("text", text1);
		msgrcv.putExtra("PostTime", NotPosttime);
		LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
	}

}
}