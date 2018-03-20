package com.example.osu.cryptoclicker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aruef on 3/12/2018.
 */

public class Player {

    private Map<String, Double> currency;
    private int upgrade;
    private SQLiteDatabase mDB;

    public Player(SQLiteDatabase db){
        currency = new HashMap<String, Double>();
        mDB = db;

        //User will have ID of 1
        Cursor cursor = db.query(ClickerContract.UserData.TABLE_NAME,
                new String[] {ClickerContract.UserData.COLUMN_UPGRADE,
                        ClickerContract.UserData.COLUMN_USD,
                        ClickerContract.UserData.COLUMN_BITCOIN},
                ClickerContract.UserData._ID + "=?",
                new String[] {"1"},
                null, null, null);

        cursor.moveToFirst();
        upgrade = cursor.getInt(0);
        currency.put(CoinBaseUtils.COINBASE_CURRENCY_USD, cursor.getDouble(1));
        currency.put(CoinBaseUtils.COINBASE_CURRENCY_BTC, cursor.getDouble(2));
    }

    public double getCurrency(String reqCurrency){
        return currency.containsKey(reqCurrency) ? currency.get(reqCurrency) : 0.;
    }

    public void setCurrency(String reqCurrency, double value){
        currency.put(reqCurrency, value);

        ClickerDBHelper.updateCurrency(mDB, reqCurrency, value);
    }
}
