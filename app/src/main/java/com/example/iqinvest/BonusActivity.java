package com.example.iqinvest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BonusActivity extends AppCompatActivity {

    private final long BONUS_DURATION = 24 * 60 * 60 * 1000;
    private final int INITIAL_BONUS_AMOUNT = 1000;

    private long bonusTimerInMillis = BONUS_DURATION;
    private int bonusAmount = INITIAL_BONUS_AMOUNT;

    private TextView timerTextView;
    private TextView bonusAmountTextView;
    private Button getBonusButton;

    private CountDownTimer bonusTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);

        timerTextView = findViewById(R.id.timerTextView);
        bonusAmountTextView = findViewById(R.id.bonusAmountTextView);
        getBonusButton = findViewById(R.id.getBonusButton);

        bonusTimer = new CountDownTimer(bonusTimerInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                bonusTimerInMillis = millisUntilFinished;
                updateUI();
            }

            @Override
            public void onFinish() {
                bonusAmount += 0.2 * bonusAmount;
                updateUI();
            }
        }.start();

        updateUI();

        getBonusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBonus();
            }
        });
    }

    private void getBonus() {
        MainActivity.increaseBalance(bonusAmount);
        updateUI();
    }

    private void updateUI() {
        timerTextView.setText("Timer: " + millisToTimeString(bonusTimerInMillis));
        bonusAmountTextView.setText("Bonus Amount: " + bonusAmount + "₽");
    }

    private String millisToTimeString(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}