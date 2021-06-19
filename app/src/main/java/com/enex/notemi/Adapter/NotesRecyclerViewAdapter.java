package com.enex.notemi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enex.notemi.Note;
import com.enex.notemi.R;

import java.util.ArrayList;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder> {
    ArrayList<Note> noteArrayList;
    Context context;

    public NotesRecyclerViewAdapter(ArrayList<Note> notes, Context context) {
        this.noteArrayList = notes;
        this.context = context;
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
        TextView title,description;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("notesSize", "NoteViewHolder: "+noteArrayList.size());
            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.noteDes);
        }
    }
}
