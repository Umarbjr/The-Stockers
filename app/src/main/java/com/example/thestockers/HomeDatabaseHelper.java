package com.example.thestockers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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

    void addItem(String name, int quantity, String uom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_HOME_PRODUCT_NAME, name);
        cv.put(COLUMN_HOME_QUANTITY, quantity);
        cv.put(COLUMN_UNIT_OF_MEASURE, uom);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed" , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully" , Toast.LENGTH_SHORT).show();
        }
    }

    // Returned cursor will contain all the data in the db
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



}
