package com.enex.notemi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class ViewNote extends AppCompatActivity {
    private TextToSpeech tts;
    String title,content,date;
    TextView titleView, contentView, dateView, characterCount;
    FloatingActionButton editButton;
    ImageView backBtn;
    LinearLayout ttsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        content = getIntent().getStringExtra("content");

        titleView = findViewById(R.id.titleOfNote);
        contentView = findViewById(R.id.contentOfNote);
        dateView = findViewById(R.id.dateOfNoteCreation);
        titleView.setText(title);
        contentView.setText(content);
        dateView.setText(date);

        editButton = findViewById(R.id.editBtn);
        editButton.setOnClickListener(view -> editNote());
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> finish());

        characterCount = findViewById(R.id.characterCount);
        characterCount.setText("Character Count: "+content.length());

        ttsLayout = findViewById(R.id.ttsLayout);
        setupTTS();
        ttsLayout.setOnClickListener(v->speak(title+".."+content));
    }

    public void editNote(){
        Intent intent = new Intent(this,Editnote.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("date",date);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                assert data != null;
                title = data.getStringExtra("title");
                content = data.getStringExtra("content");
                titleView.setText(title);
                contentView.setText(content);
            }
        }
    }

    public void showToast(String msg){
        Toast.makeText(this,"The page is under development",Toast.LENGTH_SHORT).show();
    }
    public void setupTTS(){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int languageTest = tts.setLanguage(Locale.US);
                    if (languageTest == TextToSpeech.LANG_MISSING_DATA
                            || languageTest == TextToSpeech.LANG_NOT_SUPPORTED
                    ){
                        showToast("There was some problem trying to initialize TTS Language");
                    }
                }else{
                    showToast("TTS Initialization Failed");
                }
            }
        });
    }
    public void speak(String msg){
        float speed = 1f;
        float pitch = 1f;
        tts.setSpeechRate(speed);
        tts.setPitch(pitch);
        tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);
    }
    public void stopTTS(){
        if (tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTTS();
    }
}