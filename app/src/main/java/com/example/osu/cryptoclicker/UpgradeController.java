package com.example.osu.cryptoclicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class UpgradeController extends AppCompatActivity implements UpgradeRVAdapter.OnUpgradeClickListener {
    private final static String TAG = "UpgradeController";

    private UpgradeRVAdapter mUpgradeRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        initUpgradesRV();
    }

    private void initUpgradesRV(){
        RecyclerView upgradesRV = findViewById(R.id.rv_upgrades);
        mUpgradeRVAdapter = new UpgradeRVAdapter(this);
        upgradesRV.setAdapter(mUpgradeRVAdapter);
        upgradesRV.setLayoutManager(new LinearLayoutManager(this));
        upgradesRV.setHasFixedSize(true);
        mUpgradeRVAdapter.updateData(testStrings(10));
    }

    private ArrayList<String> testStrings(int num){
        ArrayList<String> ret = new ArrayList<>();

        for(int i=1; i<=num; i++){
            ret.add(String.valueOf(i));
        }

        return ret;
    }

    @Override
    public void onUpgradeClick(String upgrade) {
        Log.d(TAG, upgrade);
    }
}
