package com.enex.notemi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enex.notemi.Adapter.TodoAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.android.material.transition.FadeProvider;

import java.util.ArrayList;
import java.util.Calendar;

public class Todo extends AppCompatActivity implements TodoDialog.TodoDataListener {
    BottomAppBar appBar;
    FloatingActionButton addBtn;
    RecyclerView todoRecyclarView;
    ArrayList<TodoManager> list = new ArrayList<>();
    Database database;
    RecyclerView.Adapter adapter;

    AlarmManager manager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        createNotificationChanel();

        appBar = findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.todoNavIcon:
                    showToast("Already in todo page", Toast.LENGTH_LONG);
                    break;
                case R.id.noteNavIcon:
                    finish();
                    break;
            }
            return true;
        });

        addBtn = findViewById(R.id.createBtn);
        addBtn.setOnClickListener(v->{
            showDialog();
        });

        todoRecyclarView = findViewById(R.id.todoRecyclarView);
        database = new Database(this);

        setupTodo(todoRecyclarView,database);
    }

    public void setupTodo(RecyclerView view,Database database){
        Cursor allTodo = database.getAllTodo();
        if (allTodo.getCount() == 0){
            TodoManager todo = new TodoManager("Example Todo Title!");
            list.add(todo);
        }else{
            list.clear();
            while(allTodo.moveToNext()){
                String title = allTodo.getString(1);
                list.add(new TodoManager(title));
            }
        }
        adapter = new TodoAdapter(list,this,database);
        todoRecyclarView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclarView.setAdapter(adapter);
    }

    public void showDialog() {
        // Dialog
        TodoDialog dialog = new TodoDialog(this);
        dialog.show(getSupportFragmentManager(),"Todo Dialog");
    }

    @Override
    public String getTodo(String todoText) {
        Database db = new Database(this);
        if (todoText.equals("")){
            showToast("Todo text is required",Toast.LENGTH_SHORT);
        }else{

            Boolean result = db.insertTodo(todoText);
            if (result){
                showToast("Todo added successfully",Toast.LENGTH_SHORT);
                setupTodo(todoRecyclarView,database);
            }else {
                showToast("There was some error trying to process your request",Toast.LENGTH_LONG);
            }
        }
        return null;
    }

    public void createNotificationChanel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelName = "todochannel";
            String description = "To show the todo notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("todo",channelName,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    public void showTimePicker(){
//        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
//                .setTimeFormat(TimeFormat.CLOCK_12H).setHour(12)
//                .setMinute(0)
//                .setTitleText("Notify Todo")
//                .build();
//        timePicker.show(
//                ((AppCompatActivity) this).getSupportFragmentManager(),
//                "todo"
//        );
//        Calendar calendar = Calendar.getInstance();
//
//        timePicker.addOnPositiveButtonClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setupNotification(calendar,timePicker);
//                    }
//                }
//        );
//        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
//        calendar.set(Calendar.MINUTE,timePicker.getMinute());
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND,0);
//    }
//    public void setupNotification(Calendar calendar,MaterialTimePicker timePicker){
//        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
//        calendar.set(Calendar.MINUTE,timePicker.getMinute());
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND,0);
//        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(this, NotificationSchedule.class);
//        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
//        manager.set(AlarmManager.ELAPSED_REALTIME,calendar.getTimeInMillis(),pendingIntent);
//        Toast.makeText(this,"We scheduled your todo!",Toast.LENGTH_SHORT).show();
//    }
//    public void cancelAlarm(){
//        Intent intent = new Intent(this, NotificationSchedule.class);
//        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
//        if (manager==null){
//            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        }
//        manager.cancel(pendingIntent);
//        showToast("Notification Cancelled",Toast.LENGTH_SHORT);
//    }
    public void showToast(String msg,int len){
        Toast.makeText(this,msg,len).show();
    }
}