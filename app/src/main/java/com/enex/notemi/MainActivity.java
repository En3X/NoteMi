package com.enex.notemi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enex.notemi.Adapter.NotesRecyclerViewAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            switch (item.getItemId()){
                case R.id.noteNavIcon:
                    showToast("Already in notes page",Toast.LENGTH_LONG);
                    break;
                case R.id.todoNavIcon:
                    Intent intent = new Intent(this,Todo.class);
                    startActivity(intent);
                    break;
            }
            return true;
         });
        // Card setup
        noteView = findViewById(R.id.todoRecyclarView);
        database = new Database(this);
        FloatingActionButton addBtn = findViewById(R.id.createBtn);
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
    public void showToast(String msg,int len){
        Toast.makeText(this,msg,len).show();
    }
    public void setupNoteView(RecyclerView noteView, Database database){
        Cursor allNotes = database.getAllNotes();
        if (allNotes.getCount()==0){
            Note exampleNote = new Note("Example Note","You have not added any note so we are showing you some of our own.\n\nThis is how your notes will appear! Keep reading to learn more or start adding notes!\n" +
                    "1. Adding note in NoteMi is very simple, just click the add button on the buttom right corner of the screen!" +
                    "\n\n2. Wanna know hoe to delete your notes? You can simply click the dustbin icon on the top right corner of this specific note!" +
                    "\n\n3. If you find any bugs, it would be a lot of help if you could report them. To do so, you can DM me in my instagram @_maneesh_pandey!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            );
            noteList.add(exampleNote);
        }else {
            while(allNotes.moveToNext()){
                String title = allNotes.getString(1);
                String description = allNotes.getString(2);
                String date = allNotes.getString(3);
                Integer id = allNotes.getInt(0);
                noteList.add(new Note(title,description,date,id));
            }
        }
        adapter = new NotesRecyclerViewAdapter(noteList,this,database);
        noteView.setLayoutManager(new LinearLayoutManager(this));
        noteView.setAdapter(adapter);
    }
}