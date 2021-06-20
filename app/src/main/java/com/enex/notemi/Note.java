package com.enex.notemi;

import android.content.Context;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    Context context;
    String date;
    Integer id;
    String title,content,publishedDate;
    public Note(String title, String content, String date, @Nullable Integer id){
        this.title = title;
        this.content = content;
        this.date = date;
        this.id = id;
    }
    public Integer getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

}
