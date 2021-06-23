package com.example.guessthecelebrity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView infoContentTextView;
    private Button startButton;
    private DownloadTask task;
    private List<Pair<String, String>> celebsInfo;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startQuiz(View view) {
        if (!celebsInfo.isEmpty()) {
            Log.i("Info", "celebsInfo is being passed on to next activity");
            startButton.setTooltipText("");

            //Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            //startActivity(intent);
        }
    }

    public List<Pair<String, String>> giveAllMatchesOfGroup(String regexPattern, String text,
                                                            Integer groupNumber1, Integer groupNumber2) {
        List<Pair<String, String>> ans = new ArrayList<>();

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            ans.add(new Pair<> (matcher.group(groupNumber1), matcher.group(groupNumber2)));
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
                celebrityWebsiteHtml = task.execute("https://www.imdb.com/list/ls052283250/").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String regexPattern = "<img alt=\"(.*?)\"(.*?)src=\"(.*?)\"(.*?)>";
        celebsInfo = giveAllMatchesOfGroup(regexPattern, celebrityWebsiteHtml, 1, 3);
    }
}