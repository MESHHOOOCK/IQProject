package com.example.iqinvest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    private List<Item> itemList;
    private ItemAdapter itemAdapter;
    private SharedPreferences sharedPreferences;

    private TextView balanceTextView;
    private TextView incomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        balanceTextView = findViewById(R.id.balanceTextView);
        incomeTextView = findViewById(R.id.incomeTextView);

        sharedPreferences = getSharedPreferences("gameState", MODE_PRIVATE);

        itemList = new ArrayList<>();
        itemList.add(new Item(10, 1, 0.5f));
        itemList.add(new Item(100, 10, 5f));
        itemList.add(new Item(1000, 100, 50f));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        updateUI();
    }

    private void purchaseItem(Item item) {
        if (getBalance() >= item.getPrice()) {
            increaseIncomePerSecond((float) item.getIncomePerSecond());
            decreaseBalance(item.getPrice());
            updateUI();
        }
    }

    private int getBalance() {
        return sharedPreferences.getInt("balance", 0);
    }

    private void increaseIncomePerSecond(float amount) {
        float currentIncomePerSecond = sharedPreferences.getFloat("incomePerSecond", 0f);
        float newIncomePerSecond = currentIncomePerSecond + amount;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("incomePerSecond", newIncomePerSecond);
        editor.apply();
    }

    private void decreaseBalance(int amount) {
        int currentBalance = getBalance();
        int newBalance = currentBalance - amount;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("balance", newBalance);
        editor.apply();
    }

    private void updateUI() {
        int balance = getBalance();
        float incomePerSecond = sharedPreferences.getFloat("incomePerSecond", 0f);
        balanceTextView.setText("Balance: " + balance + "₽");
        incomeTextView.setText("Income per second: " + incomePerSecond + "₽");
    }

    @Override
    public void onItemClick(Item item) {
        purchaseItem(item);
    }
}