package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PostQuizActivity extends AppCompatActivity {
    // Views
    private TextView postQuizInfoContentTextView;

    // Variables
    private List<CelebInfo> celebsInfo;
    private Integer totalQuestionsPlayed;
    private Integer correctGuesses;

    private void initialise() {
        postQuizInfoContentTextView = findViewById(R.id.postQuizInfoContentTextView);

        celebsInfo = (ArrayList<CelebInfo>) getIntent().getExtras().getSerializable("celebsInfo");
        totalQuestionsPlayed = getIntent().getExtras().getInt("totalQuestionsPlayed");
        correctGuesses = getIntent().getExtras().getInt("correctGuesses");
    }

    public void restartQuiz(View view) {
        Intent intent = new Intent(PostQuizActivity.this, QuizActivity.class);
        intent.putExtra("celebsInfo", (Serializable) celebsInfo);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quiz);

        initialise();

        Double percentageScore =
                BigDecimal.valueOf(100.0 *(correctGuesses.doubleValue()/totalQuestionsPlayed.doubleValue()))
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        String textForPostQuizInfoContentTextView =
                "Total count of Celebrities guessed : " + totalQuestionsPlayed.toString() + "\n\n"
                + "Count of correctly guesses Celebrities : " + correctGuesses.toString() + "\n\n"
                + "Quiz's percentage score : " + percentageScore.toString() + " %\n\n";
        postQuizInfoContentTextView.setText(textForPostQuizInfoContentTextView);
    }
}