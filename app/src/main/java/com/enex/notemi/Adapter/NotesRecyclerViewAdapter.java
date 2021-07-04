package com.enex.notemi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enex.notemi.Database;
import com.enex.notemi.Note;
import com.enex.notemi.R;
import com.enex.notemi.ViewNote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder> {
    ArrayList<Note> noteArrayList;
    Context context;
    Database database;
    Note exampleNote;
    public NotesRecyclerViewAdapter(ArrayList<Note> notes, Context context,Database database) {
        this.noteArrayList = notes;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerViewAdapter.NoteViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.title.setText(note.getTitle());
        String content = note.getContent();
        String temp = note.getContent();
        if(content.length() > 80){
            temp = content.substring(0,80)+"....";
        }
        holder.description.setText(temp);
        holder.dateCreated.setText(note.getDate());
        exampleNote = new Note("Example Note","You have not added any note so we are showing you some of our own.\n\nThis is how your notes will appear! Keep reading to learn more or start adding notes!\n" +
                "1. Adding note in NoteMi is very simple, just click the add button on the buttom right corner of the screen!" +
                "\n\n2. Wanna know hoe to delete your notes? You can simply click the dustbin icon on the top right corner of this specific note!" +
                "\n\n3. If you find any bugs, it would be a lot of help if you could report them. To do so, you can DM me in my instagram @_maneesh_pandey!",
                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                null
        );
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteArrayList.remove(note);
                notifyItemRemoved(position);
                Boolean result = database.delete(note.getTitle(),note.getDate(),note.getContent());
                if (result){
                    if (noteArrayList.size() < 1){
                        Toast.makeText(context,"You deleted all your notes, showing from our side!",Toast.LENGTH_SHORT).show();
                        noteArrayList.add(exampleNote);
                        notifyItemInserted(position);
                        return;
                    }
                    Toast.makeText(context,"Note Deleted successfully!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Could not delete note, please try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewNote.class);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("date",note.getDate());
                intent.putExtra("content",note.getContent());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }
    public void clear() {
        int size = noteArrayList.size();
        noteArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,dateCreated;
        FloatingActionButton delBtn;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("notesSize", "NoteViewHolder: "+noteArrayList.size());
            title = itemView.findViewById(R.id.todoTitle);
            description = itemView.findViewById(R.id.noteDes);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            delBtn = itemView.findViewById(R.id.delete_button);
        }
    }
}
