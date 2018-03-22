package com.example.osu.cryptoclicker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private SQLiteDatabase mDB;
    private Player mPlayer;

    private TextView mTVStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVStatus = findViewById(R.id.tv_status);
        mTVStatus.setOnClickListener(mSceneClickListener);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mPlayer = new Player(mDB);

        updateScene();
    }

    @Override
    protected void onResume()  {
        super.onResume();
        mPlayer.updateCurrency();
    }

    private void updateScene(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USD: ");
        stringBuilder.append(mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_USD));
        stringBuilder.append("\n\n");
        stringBuilder.append("Upgrades: ");
        stringBuilder.append(mPlayer.getUpgrade());
        stringBuilder.append("\n");
        //stringBuilder.append("Current Click: ");
        mTVStatus.setText(stringBuilder.toString());
    }

    public void goUpgrades(View v){
        Intent intent = new Intent(this, UpgradeController.class);
        startActivity(intent);
    }

    public void goInvest(View v){
        Intent intent = new Intent(this, InvestController.class);
        startActivity(intent);
    }

    private View.OnClickListener mSceneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPlayer.click();

            updateScene();
        }
    };
}
