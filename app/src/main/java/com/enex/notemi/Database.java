package com.enex.notemi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Database extends SQLiteOpenHelper {

    public Database(Context context){
        super(context, "noteme.db" ,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Notes(id integer primary key,title TEXT,content TEXT,date TEXT)");
        db.execSQL("create table Todo(id integer primary key,title TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Notes");
        db.execSQL("drop Table if exists Todo");
    }

    // Adding to Notes

    public Boolean insertData(String title, String content){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("date",dateFormat.format(new Date()));
        long result = database.insert("Notes",null,contentValues);
        return result != -1;
    }

    // Adding to To do list
    public Boolean insertTodo(String title){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
        contentValues.put("title",title);
//        contentValues.put("date",dateFormat.format(new Date()));
        long result = database.insert("Todo",null,contentValues);
        return result != -1;
    }
    // Updating data
    public Boolean updateData(String title, String content,String oldTitle,String oldContent,String oldDate){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("content",content);
        long result = database.update("Notes",contentValues,"title=? and content = ? and date=?",new String[]{oldTitle,oldContent,oldDate});
        return result != -1;
    }

    // Deleting data
    public Boolean delete(String title,String date,String des){
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete("Notes","title=? and date=? and content=?",new String[]{title,date,des});
        return result != -1;
    }
    public Boolean delete(String title){
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete("Todo","title=?",new String[]{title});
        return result != -1;
    }

    // Fetch all data from notes
    public Cursor getAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Notes",null);
        return cursor;
    }

    // Fetch all data from todo
    public Cursor getAllTodo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Todo",null);
        return cursor;
    }
}
