package com.enex.notemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Editnote extends AppCompatActivity {
    String title,content,date;
    TextView titleView,contentView;
    FloatingActionButton saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        Intent i = getIntent();
        title = i.getStringExtra("title");
        content = i.getStringExtra("content");
        date = i.getStringExtra("date");

        titleView = findViewById(R.id.titleInput);
        contentView = findViewById(R.id.contentInputField);

        titleView.setText(title);
        contentView.setText(content);
        findViewById(R.id.backBtn).setOnClickListener(v->finish());

        saveBtn = findViewById(R.id.createBtn);
        saveBtn.setOnClickListener(v->{
            saveNewContent();
        });

    }

    public void saveNewContent(){
        String newContent, newTitle;
        newTitle = titleView.getText().toString();
        newContent = contentView.getText().toString();
        if (newTitle.equals("")){
            Toast.makeText(this,"Title is necessary, though description can be optional",Toast.LENGTH_SHORT).show();
            return;
        }
        if (newContent.equals("")){
            newContent = content;
        }
        Database database = new Database(this);
        Boolean result = database.updateData(newTitle,newContent,title,content,date);
        if (result){
            Toast.makeText(this,"Edited the data successfully",Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
//            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(resultIntent);
            resultIntent.putExtra("title",newTitle);
            resultIntent.putExtra("content",newContent);
            setResult(RESULT_OK,resultIntent);
            finish();
        }else {
            Toast.makeText(this,"There was some error trying to save the data!",Toast.LENGTH_SHORT).show();
        }
    }
}