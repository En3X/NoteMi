package com.enex.notemi.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.enex.notemi.Database;
import com.enex.notemi.MainActivity;
import com.enex.notemi.NotificationSchedule;
import com.enex.notemi.R;
import com.enex.notemi.Todo;
import com.enex.notemi.TodoManager;
import com.enex.notemi.ViewNote;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    ArrayList<TodoManager> noteArrayList;
    Context context;
    Database database;
    AlarmManager manager;
    PendingIntent pendingIntent;
    public TodoAdapter(ArrayList<TodoManager> todo, Context context, Database database) {
        this.noteArrayList = todo;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card,parent,false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        TodoManager todo = noteArrayList.get(position);
        holder.title.setText(todo.getTitle());
        TodoManager exampleTodo = new TodoManager("Example Todo Title!");
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteArrayList.remove(todo);
                notifyItemRemoved(position);
                Boolean result = database.delete(todo.getTitle());
                if (result){
                    if (noteArrayList.size() < 1){
                        Toast.makeText(context,"You deleted all your todo, showing from our side!",Toast.LENGTH_SHORT).show();
                        noteArrayList.add(exampleTodo);
                        notifyItemInserted(position);
                        return;
                    }
//                    ((Todo)context).cancelAlarm();
                    Toast.makeText(context,"Note Deleted successfully!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Could not delete note, please try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });



    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (context instanceof Todo){
//                    ((Todo)context).showTimePicker();
//                }
//            }
//        });
}
    public void clear() {
        int size = noteArrayList.size();
        noteArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }
    public class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        FloatingActionButton delBtn;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("notesSize", "NoteViewHolder: "+noteArrayList.size());
            title = itemView.findViewById(R.id.todoTitle);
            delBtn = itemView.findViewById(R.id.delete_button);
        }

    }
}
