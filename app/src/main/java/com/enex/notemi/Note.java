package com.enex.notemi;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    Context context;
    String title,content,date;
    Note(String title,String content){
        this.title = title;
        this.content = content;
        this.date = new SimpleDateFormat("yyyy/mm/dd").format(new Date());
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
