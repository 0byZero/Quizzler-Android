package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import org.w3c.dom.Text;

public class MainActivity extends Activity {


    // member variables
     Button mTrueButton;
     Button mFalseButton;
     TextView mScoreTextView;
     TextView mQuestionTextView;
     ProgressBar mProgressBar;
     int mScore;
     int mIndex;
     int mQuestion;

    // question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    //constants
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

     // TrueFalse firstQuestion = mQuestionBank[mIndex];
        int mQuestion  = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);

        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    //  mQuestionTextView.setText(mQuestionBank[mIndex].getQuestionID());
    // 3 in 1

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.d("Quzzler","Button pressed!");
                Toast myToast = Toast.makeText(getApplicationContext(),"True pressed",Toast.LENGTH_SHORT);
                myToast.show();*/
                checkAnswer(true);
                updateQuestion();
            }
        };
        mTrueButton.setOnClickListener(myListener);

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Toast.makeText(getApplicationContext(),"False Pressed",Toast.LENGTH_SHORT).show();
                checkAnswer(false);
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        mIndex = ( mIndex + 1 ) % mQuestionBank.length ;

        if(mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this );
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You Scored " + mScore + " points!");
            alert.setPositiveButton("Close Applcation", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        if(userSelection == correctAnswer) {
            //Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            showToastMessage("You got it!",500);
            mScore = mScore + 1;
        } else {
            //Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
            showToastMessage("Wrong!",500);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }

    public void showToastMessage(String text, int duration){
        final Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }
}
