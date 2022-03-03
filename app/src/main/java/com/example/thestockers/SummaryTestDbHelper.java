package com.example.thestockers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SummaryTestDbHelper extends SQLiteOpenHelper {
    private static final String db_name = "Summary Group Test";
    private static final int db_version = 4;

    public static final String table = "table_group";
    public static final String id = "id";
    public static final String items = "items";
    public static final String costs = "costs";

    private SQLiteDatabase db;

    public SummaryTestDbHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table + " (" +
                id + " INTEGER primary key autoincrement, " +
                items + " text not null, " +
                costs + " text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table);
    }

    public void insertDataGroup (ContentValues values) {
        db.insert(table, null, values);
    }

    public List<SummaryGroup> getGroup() {
        List<SummaryGroup> groupList = new ArrayList<>();
        List<SummaryGroupItem> groupItemList = new ArrayList<>();
        String getGroupStr = "";
        Cursor getGroup = db.rawQuery("select substr(" + costs + ", 1, 10) as " + costs + " from " +
                table + " group by substr("+ costs + "1, 10) order by "+ costs + "desc", null);
        getGroup.moveToFirst();
        int groupInt;
        for (groupInt = 0; groupInt < getGroup.getCount(); groupInt++) {
            getGroupStr = getGroup.getString(getGroup.getColumnIndex(costs));
            String[] splitGroup = getGroupStr.split("-");

            Cursor getGroupItem = db.rawQuery("select * from " +
                    table + " where " + costs + " like '" + getGroupStr + "%' order by " + costs + "item", null);
            getGroupItem.moveToFirst();

            int groupItemInt;
            for (groupItemInt=0; groupItemInt < getGroupItem.getCount(); groupItemInt++) {
                String getItem = getGroupItem.getString(getGroupItem.getColumnIndex(items));
                String getCost = getGroupItem.getString(getGroup.getColumnIndex(costs));
                int len = getCost.length();

                groupItemList.add(new SummaryGroupItem(getItem, getCost));
                getGroupItem.moveToNext();
            }
//            Pause here and continue in https://www.youtube.com/watch?v=hXAD9Hip5pY.
//
//            groupList.add(new SummaryGroup(setGroupStr, groupItemList));
            getGroup.moveToNext();
        }



        return groupList;

    }
}
