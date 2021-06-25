package com.example.guessthecelebrity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView infoContentTextView;
    private Button startButton;
    private DownloadTask task;
    private List<CelebInfo> celebsInfo;

    private void initialise() {
        infoContentTextView = findViewById(R.id.infoContentTextView);
        startButton = findViewById(R.id.startButton);

        String textOfContentTextView =  "Guess as many celebrities as you\n" +
                "can by choosing the answer for\n"+
                "the celebrity image among it's four\n" +
                "options in a time of 30 seconds,\n" +
                "for all the images, to score\n" +
                "as high as possible. All the Best!";
        infoContentTextView.setText(textOfContentTextView);
    }

    // 'O' stands for Oreo i.e. SDK version 26
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startQuiz(View view) {
        if (!celebsInfo.isEmpty()) {
            Log.i("Info", "celebsInfo is being passed on to next activity");
            startButton.setTooltipText(""); // This method call requires API level 26

            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("celebsInfo", (Serializable) celebsInfo); // Passing 'celebsInfo' to QuizActivity
            startActivity(intent); // Moving to QuizActivity
        }
    }

    public List<CelebInfo> giveAllMatchesOfGroup(String regexPattern, String text,
                                                            Integer groupNumber1, Integer groupNumber2) {
        List<CelebInfo> ans = new ArrayList<>();

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            ans.add(new CelebInfo(matcher.group(groupNumber1), matcher.group(groupNumber2)));
        }

        return ans;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();

        String celebrityWebsiteHtml = "";
        while (celebrityWebsiteHtml.isEmpty()) {
            try {
                task = new DownloadTask();
                celebrityWebsiteHtml = task.execute("https://www.imdb.com/list/ls020280202/").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String regexPattern = "<img alt=\"(.*?)\"(.*?)src=\"(.*?)\"(.*?)>";
        celebsInfo = giveAllMatchesOfGroup(regexPattern, celebrityWebsiteHtml, 1, 3);
    }
}