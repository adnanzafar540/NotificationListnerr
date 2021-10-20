package com.example.notificationlistnerr.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notificationlistnerr.Model;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Database";
    public static final String TABLE_NAME = "particulars_table";
    private static final int DATABASE_VERSION = 3;
    public static final String COL_1 = "AppTitle";
    public static final String COL_2 = "NotificationText";
    public static final String COL_3 = "Date";
    public static final String COL_4 = "Notification_AppIcon";
    public static final String COL_5 = "pakage_name";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Table = "CREATE TABLE " + TABLE_NAME +
                " (id integer primary key," + COL_1 + " VARCHAR," +
                COL_2 + " VARCHAR," + COL_3 + " VARCHAR," + COL_4 + " VARCHAR," +
                COL_5 + " VARCHAR);";
        db.execSQL(Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, model.getName());
        contentValues.put(COL_2, model.getText());
        contentValues.put(COL_3, model.getPosttime());
        contentValues.put(COL_4, model.getName());
        contentValues.put(COL_5, model.getPackaename());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Model> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<Model> list = new ArrayList<>();
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        while ((cursor.moveToNext())) {
            Model Model = new Model();
            Model.setName(cursor.getString(1));
            Model.setText(cursor.getString(2));
            Model.setPosttime(cursor.getString(3));
            Model.setName(cursor.getString(4));
            Model.setPackaename(cursor.getString(5));
            list.add(Model);
        }
        return list;
    }

    public List<Model> checkPakageName_GetData(String pkgName) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Model> modelList = new ArrayList<>();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery("SELECT * FROM particulars_table WHERE pakage_name = '" + pkgName + "'", null);
            while (cursor.moveToNext()) {
                Model model = new Model();
                model.setName(cursor.getString(1));
                model.setText(cursor.getString(2));
                model.setPosttime(cursor.getString(3));
                model.setPackaename(cursor.getString(5));
                cursor.moveToNext();
                modelList.add(model);

            }
        }
        return modelList;

    }

    public boolean isDatabaseEmpty() {
        boolean chk = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (mCursor != null) {
            if (mCursor.getCount() == 0) {

                chk = false;
            }
        } else {
            chk = true;
        }
        return chk;
    }
}