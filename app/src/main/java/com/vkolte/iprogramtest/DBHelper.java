package com.vkolte.iprogramtest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ImageData.db";
    public static final String TABLE_NAME = "Photo";
    public static final String SR_NO = "SR_NO";
    public static final String ID = "ID";
    public static final String URL = "URL";
    public static final String TITLE = "TITLE";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(
                    "create table "+TABLE_NAME +
                            "(SR_NO text primary key, ID text,URL text,TITLE text)"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRecord (String sr_no, String id, String url, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SR_NO, sr_no);
        contentValues.put(ID, id);
        contentValues.put(URL, url);
        contentValues.put(TITLE, title);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteRecord (String sr_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "SR_NO = ? ",
                new String[] { sr_no });
    }

    public void dropTableIfExist(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public String getTableAsString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableString = String.format("Table %s:\n", TABLE_NAME);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

}
