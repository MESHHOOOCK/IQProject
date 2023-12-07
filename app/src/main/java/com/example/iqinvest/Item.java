package com.example.iqinvest;

public class Item {

    private int price;
    private int clickIncome;
    private double incomePerSecond;

    public Item(int price, int clickIncome, double incomePerSecond) {
        this.price = price;
        this.clickIncome = clickIncome;
        this.incomePerSecond = incomePerSecond;
    }

    public int getPrice() {
        return price;
    }

    public int getClickIncome() {
        return clickIncome;
    }

    public double getIncomePerSecond() {
        return incomePerSecond;
    }
}

