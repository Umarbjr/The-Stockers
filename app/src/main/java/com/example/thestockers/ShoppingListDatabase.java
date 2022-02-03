package com.example.thestockers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ShoppingListDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ShoppingList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_shopping_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "list_title";
    private static final String COLUMN_STORE = "store_name";
    private static final String COLUMN_ITEMS = "item_count";

    public ShoppingListDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_STORE + " TEXT, " +
                        COLUMN_ITEMS + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
