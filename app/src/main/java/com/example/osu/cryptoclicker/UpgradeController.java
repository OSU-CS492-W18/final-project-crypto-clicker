package com.example.osu.cryptoclicker;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpgradeController extends AppCompatActivity implements UpgradeRVAdapter.OnUpgradeClickListener {
    private final static String TAG = "UpgradeController";

    private UpgradeRVAdapter mUpgradeRVAdapter;
    private Player mPlayer;

    private TextView mTVUSD;
    private final static String TV_USD_PREPEND = "USD: $";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        mPlayer = new Player(db);

        mTVUSD = findViewById(R.id.tv_upgrade_usd);
        mTVUSD.setText(TV_USD_PREPEND + String.format("%.2f", mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_USD)));

        initUpgradesRV();
    }

    private void initUpgradesRV(){
        RecyclerView upgradesRV = findViewById(R.id.rv_upgrades);
        mUpgradeRVAdapter = new UpgradeRVAdapter(this);
        upgradesRV.setAdapter(mUpgradeRVAdapter);
        upgradesRV.setLayoutManager(new LinearLayoutManager(this));
        upgradesRV.setHasFixedSize(true);
        mUpgradeRVAdapter.updateData(Upgrade.parseXML(this, R.raw.upgrades));
    }

    @Override
    public void onUpgradeClick(Upgrade upgrade) {
        Log.d(TAG, upgrade.getName());

        if(mPlayer.purchaseUpgrade(upgrade)) {
            Toast.makeText(this, "You got " + upgrade.getName() + " upgrade successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "You can't get the " + upgrade.getName() + " upgrade yet!", Toast.LENGTH_LONG).show();
        }

        mTVUSD.setText(TV_USD_PREPEND + String.format("%.2f", mPlayer.getCurrency(CoinBaseUtils.COINBASE_CURRENCY_USD)));

        Log.d(TAG, String.valueOf(mPlayer.getUpgrade()));
    }
}
