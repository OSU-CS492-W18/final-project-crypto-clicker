package com.example.osu.cryptoclicker;

import android.net.ParseException;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aruef on 3/12/2018.
 */

public class CoinBaseUtils {

    //url builders
    private final static String COINBASE_BASE_URL = "https://api.coinbase.com/v2/prices";
    private final static String COINBASE_BUY_PRICE_PATH = "buy";
    private final static String COINBASE_SELL_PRICE_PATH = "sell";
    public final static String COINBASE_CURRENCY_BTC = "btc";
    public final static String COINBASE_CURRENCY_ETH = "eth";
    public final static String COINBASE_CURRENCY_BCH = "bch";
    public final static String COINBASE_CURRENCY_LTC = "ltc";
    public final static String COINBASE_CURRENCY_USD = "usd";

    //this function returns the buying price of a given crypto currency
    public static String buildBuyUrl()    {
        //https://api.coinbase.com/v2/prices/:currency_pair/buy
        String currencyPair = COINBASE_CURRENCY_BTC + '-' + COINBASE_CURRENCY_USD;

        return Uri.parse(COINBASE_BASE_URL).buildUpon()
                .appendPath(currencyPair)
                .appendPath(COINBASE_BUY_PRICE_PATH)
                .toString();
    }

    public static String buildSellUrl()    {
        //https://api.coinbase.com/v2/prices/:currency_pair/sell
        String currencyPair = COINBASE_CURRENCY_BTC + '-' + COINBASE_CURRENCY_USD;

        return Uri.parse(COINBASE_BASE_URL).buildUpon()
                .appendPath(currencyPair)
                .appendPath(COINBASE_SELL_PRICE_PATH)
                .toString();
    }

    public static String parsePriceJSON(String priceJSON)   {
        try {
            JSONObject priceObj = new JSONObject(priceJSON);
            String price = priceObj.getJSONObject("data").getString("amount");
            return price;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e)  {
            e.printStackTrace();
            return null;
        }
    }
}


