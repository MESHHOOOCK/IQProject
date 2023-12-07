package com.example.iqinvest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        private static final int ACTIVITY_SHOP_ID = R.id.activity_shop;

        private static int balance = 0;
        private float incomePerSecond = 0f;
        private SharedPreferences sharedPreferences;

        private TextView balanceTextView;
        private TextView incomeTextView;
        private Button earnMoneyButton;

        private CountDownTimer incomeTimer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            balanceTextView = findViewById(R.id.balanceTextView);
            incomeTextView = findViewById(R.id.incomeTextView);
            earnMoneyButton = findViewById(R.id.earnMoneyButton);

            sharedPreferences = getSharedPreferences("gameState", MODE_PRIVATE);

            loadGameState();
            updateUI();

            earnMoneyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    earnMoney();
                }
            });

            startIncomeTimer();
        }

        private void earnMoney() {
            increaseBalance(1);
            updateUI();
        }

        private void startIncomeTimer() {
            incomeTimer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    increaseBalance((int) incomePerSecond);
                    updateUI();
                    startIncomeTimer();
                }
            }.start();
        }

        private void loadGameState() {
            balance = sharedPreferences.getInt("balance", 0);
            incomePerSecond = sharedPreferences.getFloat("incomePerSecond", 0f);
        }

        private void saveGameState() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("balance", balance);
            editor.putFloat("incomePerSecond", incomePerSecond);
            editor.apply();
        }

        private void updateUI() {
            balanceTextView.setText("Balance: " + balance + "₽");
            incomeTextView.setText("Income per second: " + incomePerSecond + "₽");

            saveGameState();
        }

        @Override
        protected void onPause() {
            super.onPause();
            incomeTimer.cancel();
            incomeTimer = null;
        }

        @Override
        protected void onResume() {
            super.onResume();
            startIncomeTimer();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ACTIVITY_SHOP_ID) {
            // код для активности магазина
            return true;
        } else if (item.getItemId() == R.id.activity_bonus) {
            // открытие активности бонуса
            return true;
        } else if (item.getItemId() == R.id.menu_main_menu) {
            // открытие основного меню
            return true;
        } else if (item.getItemId() == R.id.menu_save_and_exit) {
            // сохранение состояния игры и выход из игры
            saveGameState();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

        public static void increaseBalance(int amount) {
            balance += amount;
        }
    }