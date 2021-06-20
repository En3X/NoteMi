package com.enex.notemi.Adapter;

import android.content.Context;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder> {
    ArrayList<Note> noteArrayList;
    Context context;
    Database database;

    public NotesRecyclerViewAdapter(ArrayList<Note> notes, Context context,Database database) {
        this.noteArrayList = notes;
        this.context = context;
        this.database = database;
        Log.d("notesSize", "In constructor: "+noteArrayList.size());

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("notesSize", "Before Context: "+noteArrayList.size());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerViewAdapter.NoteViewHolder holder, int position) {
        Note note = noteArrayList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getContent());
        holder.dateCreated.setText(note.getDate());
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = database.delete(note.getTitle(),note.getDate(),note.getContent());
                if (result){
                    noteArrayList.remove(note);
                    notifyItemRemoved(position);
                    if (noteArrayList.size() < 1){
                        noteArrayList.add(new Note("Example Notes","You have not added any note, so we are showing you some of our own!\n\nThis is how your notes will appear! Keep reading to learn more or start adding notes!",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        noteArrayList.add(new Note("How to add note?","Click the + icon in the bottom right corner of the screen to start adding note!",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        noteArrayList.add(new Note("How to delete note?","Click the dustbin icon in the top right of this card to delete that specific card!",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        noteArrayList.add(new Note("What are these?","These are example notes, if you delete this, it will reappear once you re-lunch app or refresh it.",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        noteArrayList.add(new Note("How can I get rid of these?","You cannot!" +
                                "\n\nTechnically you can by deleting these but it is just temporary as it will re-appear once you refresh or restart the application!\n" +
                                "\nThese notes will disappear once you add your own note!",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        noteArrayList.add(new Note("Developer!","Developed by Maneesh Pandey\n\nCatch me on instagram @_maneesh_pandey or on twitter with same username!",
                                new SimpleDateFormat("dd, MMM yyyy").format(new Date()),
                                null
                        ));
                        notifyDataSetChanged();
                    }
                    Toast.makeText(context,"Note Deleted successfully!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Could not delete note, please try again!",Toast.LENGTH_SHORT).show();
                }
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
            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.noteDes);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            delBtn = itemView.findViewById(R.id.delete_button);
        }
    }
}
