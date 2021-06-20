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
                    showToast("Solti note page maai xau, aba kaa jaanxau yaa baata ajjai!",Toast.LENGTH_LONG);
                    break;
                case R.id.todoNavIcon:
                    showToast("La makadox, todo page banaakai xaina ni solti!",Toast.LENGTH_LONG);
                    break;
            }
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
    public void showToast(String msg,int len){
        Toast.makeText(this,msg,len).show();
    }
    public void setupNoteView(RecyclerView noteView, Database database){
        Cursor allNotes = database.getAllNotes();
        if (allNotes.getCount()==0){
            Toast.makeText(this,"Start creating notes!",Toast.LENGTH_SHORT).show();

            noteList.add(new Note("Example Notes","You have not added any note, so we are showing you some of our own!\n\nThis is how your notes will appear! Keep reading to learn more or start adding notes!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            ));
            noteList.add(new Note("How to add note?","Click the + icon in the bottom right corner of the screen to start adding note!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
                    ));
            noteList.add(new Note("How to delete note?","Click the dustbin icon in the top right of this card to delete that specific card!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            ));
            noteList.add(new Note("What are these?","These are example notes, if you delete this, it will reappear once you re-lunch app or refresh it.",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            ));
            noteList.add(new Note("How can I get rid of these?","You cannot!" +
                    "\n\nTechnically you can by deleting these but it is just temporary as it will re-appear once you refresh or restart the application!\n" +
                    "\nThese notes will disappear once you add your own note!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            ));
            noteList.add(new Note("Developer!","Developed by Maneesh Pandey\n\nCatch me on instagram @_maneesh_pandey or on twitter with same username!",
                    new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                    null
            ));
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