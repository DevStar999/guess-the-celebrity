package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private List<CelebInfo> celebsInfo;

    private void initialise() {
        celebsInfo = (ArrayList<CelebInfo>) getIntent().getExtras().getSerializable("celebsInfo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initialise();

        Log.i("Info", "The contents for celebsInfo are as follows -");
        for (CelebInfo currentCelebInfo: celebsInfo) {
            Log.i("Info", "currentCelebInfo: Name = " + currentCelebInfo.getCelebName() +
                    ", ImageSrc = " + currentCelebInfo.getCelebImageSrc());
        }
    }
}