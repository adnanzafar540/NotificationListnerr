package com.example.notificationlistnerr;

import android.graphics.Bitmap;

public class Model {String name;

    public String getPosttime() {
        return Posttime;
    }

    public void setPosttime(String posttime) {
        Posttime = posttime;
    }

    String Posttime;

    public String getNotificationChannelGroup() {
        return NotificationChannelGroup;
    }

    public void setNotificationChannelGroup(String notificationChannelGroup) {
        NotificationChannelGroup = notificationChannelGroup;
    }

    String NotificationChannelGroup;

    public String getPackaename() {
        return packaename;
    }

    public void setPackaename(String packaename) {
        this.packaename = packaename;
    }

    String packaename;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;
    Bitmap imaBitmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return imaBitmap;
    }

    public void setImage(Bitmap imaBitmap) {
        this.imaBitmap = imaBitmap;
    }
}
