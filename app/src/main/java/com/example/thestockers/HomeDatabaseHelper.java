package com.example.thestockers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class HomeDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Stockers.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "home_inventory";
    private static final String COLUMN_ID = "home_inv_id";
    private static final String COLUMN_HOME_PRODUCT_NAME = "home_product_name" ;
    private static final String COLUMN_HOME_QUANTITY = "home_quantity";
    private static final String COLUMN_UNIT_OF_MEASURE = "product_uom";

    public HomeDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_HOME_PRODUCT_NAME + " TEXT, " +
                        COLUMN_HOME_QUANTITY + " INTEGER, " +
                        COLUMN_UNIT_OF_MEASURE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
