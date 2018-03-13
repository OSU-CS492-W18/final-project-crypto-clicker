package com.example.osu.cryptoclicker;

import android.net.Uri;

/**
 * Created by aruef on 3/12/2018.
 */

public class CoinBaseUtils {

    public final static String USD = "usd";
    public final static String BITCOIN = "bitcoin";

    //url builders
    private final static String COINBASE_BASE_URL = "https://api.coinbase.com/v2/prices";
    private final static String COINBASE_BUY_PRICE_PATH = "buy";
    private final static String COINBASE_SELL_PRICE_PATH = "sell";
    private final static String COINBASE_CURRENCY_BTC = "btc";
    private final static String COINBASE_CURRENCY_ETH = "eth";
    private final static String COINBASE_CURRENCY_BCH = "bch";
    private final static String COINBASE_CURRENCY_LTC = "ltc";
    private final static String COINBASE_CURRENCY_USD = "usd";

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
}


