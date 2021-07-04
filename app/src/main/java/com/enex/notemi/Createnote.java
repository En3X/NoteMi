package com.enex.notemi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Createnote extends AppCompatActivity {
    Database database;
    TextInputLayout layout;
    EditText contentField;
    String title,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        database = new Database(this);
        layout = findViewById(R.id.titleInputLayout);
        contentField = findViewById(R.id.contentInputField);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.createBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = layout.getEditText().getText().toString();
                content = contentField.getText().toString();
                Boolean isInserted = database.insertData(title,content);
                if (isInserted){
                    showToast("Note inserted successfully");
                    finish();
                }else{
                    showToast("There was some error trying to insert your data!");
                    layout.getEditText().setText(title);
                    contentField.setText(content);
                }
            }
        });
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }
}