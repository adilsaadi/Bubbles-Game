package com.adil.bullegame;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;



public class MainActivity extends AppCompatActivity {

    private int bubblesNumber;
    static int clickedBubbles = 0;
    TextView countDown;
    private static String in;
    private static final String FORMAT = "%02d:%02d:%02d";
    private  Random random;

    int seconds, minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDown = (TextView) findViewById(R.id.counter);

        CountDownTimer start = new CountDownTimer(30000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                countDown.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                showSimplePopUp();
                clickedBubbles = 0;
            }
        }.start();


        generateButton(60);
    }

    private void generateButton(final int numberOfButtons) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createButton();
                if((numberOfButtons-1)>0){
                    generateButton(numberOfButtons-1);
                }
            }
        }, 400);
    }


    private void createButton() {

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main);

        final Button myButton = new Button(this);
        myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_round));
        layout.addView(myButton);
        ;

        RelativeLayout.LayoutParams absParams =
                (RelativeLayout.LayoutParams) myButton.getLayoutParams();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;


        Random r = new Random();


        absParams.leftMargin = r.nextInt(width - 200);
        absParams.topMargin = r.nextInt(height - 300);
        myButton.setLayoutParams(absParams);


        myButton.postDelayed(new Runnable() {
            public void run() {
                myButton.setVisibility(View.GONE);
            }
        }, 3000);


        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                clickedBubbles++;
                v.setVisibility(View.GONE);

            }
        });


    }

    private void showSimplePopUp() {

        AlertDialog.Builder gameOver = new AlertDialog.Builder(this);
        gameOver.setTitle("GameOver");
        gameOver.setMessage("You clicked " + clickedBubbles + " bubbles");
        gameOver.setPositiveButton("restart",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);

                    }

                });


        AlertDialog dialog = gameOver.create();
        dialog.show();

    }

}
