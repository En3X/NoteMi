package com.enex.notemi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enex.notemi.Adapter.NotesRecyclerViewAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomAppBar appBar;
    Database database;
    RecyclerView noteView;
    NotesRecyclerViewAdapter adapter;
    ArrayList<Note> noteList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         appBar = findViewById(R.id.bottomAppBar);
         appBar.setOnMenuItemClickListener(item -> {
             Log.d("click","Menu item clicked");
             return true;
         });
        // Card setup
        noteView = findViewById(R.id.notesRecyclerView);
        database = new Database(this);
        FloatingActionButton addBtn = findViewById(R.id.saveNoteBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Createnote.class);
                startActivity(intent);
            }
        });
        setupNoteView(noteView,database);

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteList.clear();
        setupNoteView(noteView,database);
    }

    public void setupNoteView(RecyclerView noteView, Database database){
        Cursor allNotes = database.getAllNotes();
        if (allNotes.getCount()==0){
            Toast.makeText(this,"Start creating notes!",Toast.LENGTH_SHORT).show();
            noteList.add(new Note("No Notes Available","Create one to view your note here..."));
        }else {
            while(allNotes.moveToNext()){
                String title = allNotes.getString(1);
                String description = allNotes.getString(2);
                noteList.add(new Note(title,description));
            }
        }
        adapter = new NotesRecyclerViewAdapter(noteList,this);
        noteView.setLayoutManager(new LinearLayoutManager(this));
        noteView.setAdapter(adapter);
    }
}