package com.example.osu.cryptoclicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mohammed Algadhib on 3/20/2018.
 */

public class InvestControllerLoader extends AsyncTaskLoader<ArrayList<String>> {
    String mSellPriceJSON;
    String mSellPriceURL;
    String mBuyPriceJSON;
    String mBuyPriceURL;

    InvestControllerLoader(Context context, String sellURL, String buyURL)   {
        super(context);
        mSellPriceURL = sellURL;
        mBuyPriceURL = buyURL;
    }

    @Override
    protected void onStartLoading() {
        if(mSellPriceURL != null && mBuyPriceURL != null)  {
            if(mSellPriceJSON!=null && mBuyPriceJSON != null) {
                ArrayList<String> priceListJSON = new ArrayList<String>();
                priceListJSON.add(mSellPriceJSON);
                priceListJSON.add(mBuyPriceJSON);
                deliverResult(priceListJSON);
            }
            else    {
                forceLoad();
            }
        }
    }

    @Nullable
    @Override
    public ArrayList<String> loadInBackground() {
        if(mSellPriceURL != null  && mBuyPriceURL != null)   {
            String sellPriceResult = null;
            String buyPriceResult = null;
            try {
                sellPriceResult = NetworkUtil.doHTTPGet(mSellPriceURL);
                buyPriceResult = NetworkUtil.doHTTPGet(mBuyPriceURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> priceListJSON = new ArrayList<String>();
            priceListJSON.add(sellPriceResult);
            priceListJSON.add(buyPriceResult);
            return priceListJSON;
        }
        else {
            return null;
        }
    }

    @Override
    public void deliverResult(ArrayList<String> data)   {
        mSellPriceJSON = data.get(0);
        mBuyPriceJSON = data.get(1);
        super.deliverResult(data);
    }
}
