package com.example.osu.cryptoclicker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private SQLiteDatabase mDB;
    private Player mPlayer;

    private TextView mTVStatus, mTVOnClick;

    private int mClickCounter = 0;
    private Animation mClickAnimation;

    private ArrayList<Upgrade> upgradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVStatus = findViewById(R.id.tv_status);
        mTVStatus.setOnClickListener(mSceneClickListener);

        mTVOnClick = findViewById(R.id.tv_click_count);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mPlayer = new Player(mDB);

        upgradeList = Upgrade.parseXML(this, R.raw.upgrades);

        updateScene();

        initClickAnimation();
    }

    @Override
    protected void onResume()  {
        super.onResume();
        mPlayer.updateFromDB();
        updateScene();
    }

    private void updateScene(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("$");
        stringBuilder.append(String.format("%.2f", mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_USD)));
        stringBuilder.append("\n\nBitcoin: ");
        stringBuilder.append(String.format("%.15f", mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_BTC)));
        stringBuilder.append("\n\n");
        stringBuilder.append("Upgrades purchased: ");
        stringBuilder.append(mPlayer.getUpgrade());
        stringBuilder.append("\n");
        mTVStatus.setText(stringBuilder.toString());
    }

    private void initClickAnimation(){
        mClickAnimation = new AlphaAnimation(1.0f, 0.0f);
        mClickAnimation.setDuration(3000);
        mClickAnimation.setRepeatCount(0);
        mClickAnimation.setStartOffset(1000);
        mClickAnimation.setRepeatMode(Animation.REVERSE);
        mClickAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mClickCounter = 0;
                mTVOnClick.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // do nothing
            }
        });
    }

    private void updateAnimation(){
        String clickText = "+" + mPlayer.getClickAmount();

        if(mClickCounter != 0){
            clickText += " X " + (mClickCounter+1);
        }

        mTVOnClick.setText(clickText);
        mTVOnClick.setVisibility(View.VISIBLE);
        mTVOnClick.startAnimation(mClickAnimation);
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
            updateAnimation();
            mClickCounter++;

            mPlayer.click();

            updateScene();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        switch (item.getItemId())   {
            case R.id.share_upgrade_menu:
                shareUpgrade();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareUpgrade() {

        String message = String.format("Hey, check out my new balance in CryptoClicker," +
                " now I got $%.2f and %.7f BTC!!",
                mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_USD),
                mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_BTC));

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
