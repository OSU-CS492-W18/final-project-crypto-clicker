package com.example.osu.cryptoclicker;

/**
 * Created by Alex on 3/22/2018.
 */

public class Upgrade {

    private String mName;
    private double mAmount;
    private double mCost;

    public Upgrade(String name, double amount, double cost){
        mName = name;
        mAmount = amount;
        mCost = cost;
    }

    public String getName(){
        return mName;
    }

    public double getAmount(){
        return mAmount;
    }

    public double getCost(){
        return mCost;
    }
}
