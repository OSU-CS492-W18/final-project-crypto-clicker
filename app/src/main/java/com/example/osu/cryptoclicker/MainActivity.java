package com.example.osu.cryptoclicker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        Player player = new Player(mDB);
        player.setCurrency(ClickerContract.UserData.COLUMN_BITCOIN, 75.0);
        Log.d(TAG, String.valueOf(player.getCurrency(CoinBaseUtils.USD)));
        Log.d(TAG, String.valueOf(player.getCurrency(CoinBaseUtils.BITCOIN)));
    }

    public void goUpgrades(View v){
        Intent intent = new Intent(this, UpgradeController.class);
        startActivity(intent);
    }

    public void goInvest(View v){
        Intent intent = new Intent(this, InvestController.class);
        startActivity(intent);
    }
}
