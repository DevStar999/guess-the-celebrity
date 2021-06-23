package com.example.guessthecelebrity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.Getter;

@Getter
public class QuizInfo {
    private Integer totalQuestionsPlayed;    // Total number of question played in the quiz
    private Integer correctGuesses;          // Number of correct answers given by the player
    private String correctCelebName;         // The correct answer i.e. correct Celeb Name
    private String celebImageSrc;            // The correct celebImageSrc String value
    private List<String> options;            // List to hold the 4 options for celeb Names
    private QuizInfoHelper quizInfoHelper;   // Helper class object

    public QuizInfo() {
        totalQuestionsPlayed = 0;
        correctGuesses = 0;
        correctCelebName = "";
        celebImageSrc = "";
        options = new ArrayList<>();
        quizInfoHelper = new QuizInfoHelper();
    }

    public void resetAndGenerateData(List<CelebInfo> celebsInfo) {
        correctCelebName = "";
        celebImageSrc = "";
        options.clear();

        // Choosing a celebrity for question at random
        Random random = new Random();
        Integer questionCelebrityInfoIndex = random.nextInt(celebsInfo.size());
        correctCelebName = celebsInfo.get(questionCelebrityInfoIndex).getCelebName();
        celebImageSrc = celebsInfo.get(questionCelebrityInfoIndex).getCelebImageSrc();
        options.add(correctCelebName); // Adding the correct option at first position for now

        // Randomly adding other options and shuffling the options
        for (int optionNumber=1; optionNumber<=3; optionNumber++) {
            String tempOption = celebsInfo.get(random.nextInt(celebsInfo.size())).getCelebName();
            while (!quizInfoHelper.isUnique(options, tempOption)) {
                tempOption = celebsInfo.get(random.nextInt(celebsInfo.size())).getCelebName();
            }
            options.add(tempOption);
        }
        Collections.shuffle(options); // Shuffling the options
    }

    public boolean checkAnswer(String stringToBeCheckedWith) {
        totalQuestionsPlayed++;
        if (stringToBeCheckedWith.equals(correctCelebName)) {
            correctGuesses++;
            return true;
        }
        return false;
    }
}
