package com.example.osu.cryptoclicker;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class InvestController extends AppCompatActivity
        implements LoaderCallbacks<ArrayList<String>>{

    private ProgressBar mProgressBar;

    private final static String SELL_URL_KEY = "sellURL";
    private final static String BUY_URL_KEY = "buyURL";
    private final static int INVEST_LOADER_ID = 0;

    private TextView mSellPriceTV;
    private TextView mBuyPriceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        mSellPriceTV = (TextView)findViewById(R.id.sell_price_tv);
        mBuyPriceTV = (TextView)findViewById(R.id.buy_price_tv);

        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_bar_invest);
        getPricesData();
    }

    private void getPricesData() {
        String sellPriceURL = CoinBaseUtils.buildSellUrl();
        String buyPriceURL = CoinBaseUtils.buildBuyUrl();
        Bundle args = new Bundle();
        args.putString(SELL_URL_KEY, sellPriceURL);
        args.putString(BUY_URL_KEY, buyPriceURL);
        mProgressBar.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(INVEST_LOADER_ID, args, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, @Nullable Bundle args) {
        String sellPriceURL = null;
        String buyPriceURL = null;
        if(args != null)    {
            sellPriceURL = args.getString(SELL_URL_KEY);
            buyPriceURL = args.getString(BUY_URL_KEY);
        }
        return new InvestControllerLoader(this, sellPriceURL, buyPriceURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<String>> loader, ArrayList<String> data) {
        mProgressBar.setVisibility(View.VISIBLE);
        if(data!=null)  {
            String sellPrice = "Sell price: " + data.get(0);
            String buyPrice =  "Buy price: " + data.get(1);
            mSellPriceTV.setText(sellPrice);
            mBuyPriceTV.setText(buyPrice);
        }
        else {
            //do error message
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<String>> loader) {
        //nothing to do
    }
}
