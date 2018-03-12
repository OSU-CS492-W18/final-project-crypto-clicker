package com.example.osu.cryptoclicker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        mDB = dbHelper.getWritableDatabase();
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
