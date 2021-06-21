package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView infoContentTextView;

    private void initialise() {
        infoContentTextView = findViewById(R.id.infoContentTextView);

        String textOfContentTextView =  "Guess as many celebrities as you\n" +
                "can by choosing the answer for\n"+
                "the celebrity image among it's four\n" +
                "options in a time of 30 seconds,\n" +
                "for all the images, to score\n" +
                "as high as possible. All the Best!";
        infoContentTextView.setText(textOfContentTextView);
    }

    public void startQuiz(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }
}