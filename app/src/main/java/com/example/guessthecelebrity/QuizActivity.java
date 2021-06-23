package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    // Views
    private TextView timerTextView;
    private TextView scoreTextView;
    private ImageView celebImageView;
    private TextView guessVerdictTextView;
    private LinearLayout optionLinearLayout;

    // Variables
    private long defaultQuizDuration;
    private QuizInfo quizInfo;
    private List<CelebInfo> celebsInfo;
    private Intent intent;
    private Boolean canClick;
    private Long quizTimerDuration;

    private void initialise() {
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        celebImageView = findViewById(R.id.celebImageView);
        guessVerdictTextView = findViewById(R.id.guessVerdictTextView);
        optionLinearLayout = findViewById(R.id.optionLinearLayout);

        defaultQuizDuration = 30*1000;
        quizInfo = new QuizInfo();
        celebsInfo = (ArrayList<CelebInfo>) getIntent().getExtras().getSerializable("celebsInfo");
        intent = new Intent(QuizActivity.this, PostQuizActivity.class);
        canClick = true;
        quizTimerDuration = defaultQuizDuration;
    }

    private void setGameText() {
        // Refresh QuizInfo
        quizInfo.resetAndGenerateData(celebsInfo);

        // Setting Score Text
        scoreTextView.setText(quizInfo.getCorrectGuesses().toString() + "/" +
                quizInfo.getTotalQuestionsPlayed().toString());

        // Setting Image for ImageView
        try {
            Bitmap celebImageBitmap = new ImageDownloader().execute(quizInfo.getCelebImageSrc()).get();
            celebImageView.setImageBitmap(celebImageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            setGameText();
        }

        // Setting optionButtons' text
        for (Integer optionNumber=0; optionNumber<4; optionNumber++) {
            Integer optionResourceId = this.getResources().getIdentifier("optionButton" +
                    optionNumber.toString(), "id", this.getPackageName());
            Button optionButton = findViewById(optionResourceId);
            optionButton.setText(quizInfo.getOptions().get(optionNumber));
        }
    }

    private void playGame() {
        // Set the game text for the first time
        setGameText();

        // Setting up the post quiz timer as follows
        final CountDownTimer postQuizTimer = new CountDownTimer(1500, 1500) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                // Moving onto PostQuizActivity
                startActivity(intent);
            }
        };

        // Start quiz timer
        final CountDownTimer quizTimer = new CountDownTimer(defaultQuizDuration + 500,
                1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(quizInfo.getQuizInfoHelper()
                        .convertMilliToTimerDisplay(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                quizTimerDuration = 0L;
                guessVerdictTextView.setText("Game Over");
                celebImageView.setImageResource(R.drawable.time_over);
                optionLinearLayout.setVisibility(View.GONE);

                // Setting up Intent to move to PostQuizActivity
                intent.putExtra("celebsInfo", (Serializable) celebsInfo);
                intent.putExtra("totalQuestionsPlayed", quizInfo.getTotalQuestionsPlayed());
                intent.putExtra("correctGuesses", quizInfo.getCorrectGuesses());

                // Trigger the postQuizTimer as follows -
                postQuizTimer.start();
            }
        };
        quizTimer.start();
    }

    public void clickOption(View view) {
        if (canClick && quizTimerDuration != 0L) {
            // Setting Verdict on TextView
            canClick = false;
            String guessVerdictTextViewText = guessVerdictTextView.getText().toString();
            if (quizInfo.checkAnswer(((TextView) view).getText().toString())) {
                guessVerdictTextViewText += "<font color=#4CBB17>Correct :)</font>";
            } else {
                guessVerdictTextViewText += "<font color=#EE4B2B>Wrong :( </font>";
                guessVerdictTextViewText += "<font color=#000000>"
                        + "It is " + quizInfo.getCorrectCelebName() + "</font>";
            }
            guessVerdictTextView.setText(Html.fromHtml(guessVerdictTextViewText));

            // Setting the timer to show verdict for a limited time i.e. 0.75 seconds and move on to
            // the next question
            new CountDownTimer(750,  750) {
                @Override
                public void onTick(long millisUntilFinished) { }
                @Override
                public void onFinish() {
                    guessVerdictTextView.setText("Guess Verdict : ");
                    guessVerdictTextView.setTextColor(Color.BLACK);
                    setGameText();
                    canClick = true;
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initialise();

        playGame();
    }
}