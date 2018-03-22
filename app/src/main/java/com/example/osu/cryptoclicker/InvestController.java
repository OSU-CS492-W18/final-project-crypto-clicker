package com.example.osu.cryptoclicker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

//TODO: do the logic of buying and selling btc

public class InvestController extends AppCompatActivity
        implements LoaderCallbacks<ArrayList<String>>{

    private ProgressBar mProgressBar;

    private final static String SELL_URL_KEY = "sellURL";
    private final static String BUY_URL_KEY = "buyURL";
    private final static int INVEST_LOADER_ID = 0;

    private TextView mSellPriceTV;
    private TextView mBuyPriceTV;
    private TextView mLoadingError;
    private TextView mUSDBalanceTV;
    private TextView mBTCBalanceTV;
    private EditText mUSDAmountET;
    private EditText mBTCAmountET;

    private SQLiteDatabase mDB;

    private double mUSDBalance;
    private double mBTCBalance;

    private double mSellPrice;
    private double mBuyPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        mSellPriceTV = (TextView)findViewById(R.id.sell_price_tv);
        mBuyPriceTV = (TextView)findViewById(R.id.buy_price_tv);

        mUSDBalanceTV = (TextView)findViewById(R.id.usd_balance_tv);
        mBTCBalanceTV = (TextView)findViewById(R.id.btc_balance_tv);

        mLoadingError = (TextView)findViewById(R.id.tv_loading_error);
        mProgressBar = (ProgressBar)findViewById(R.id.pb_loading_bar_invest);

        mUSDAmountET = (EditText)findViewById(R.id.et_usd_amount);
        mBTCAmountET = (EditText)findViewById(R.id.et_btc_amount);

        ClickerDBHelper dbHelper = new ClickerDBHelper(this);
        mDB = dbHelper.getReadableDatabase();

        Button buyButton = (Button)findViewById(R.id.bt_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mUSDAmountET.getText().toString();
                if(!TextUtils.isEmpty(amount))   {
                    buyBTC(amount);
                }
                mUSDAmountET.setText("");
                updateBalance();
            }
        });

        Button sellButton = (Button)findViewById(R.id.bt_sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mBTCAmountET.getText().toString();
                if(!TextUtils.isEmpty(amount))   {
                    sellBTC(amount);
                }
                mBTCAmountET.setText("");
                updateBalance();
            }
        });


        getPricesData();
        updateBalance();
    }

    private void buyBTC(String amount)  {
        double USDAmount = Double.parseDouble(amount);
        if(USDAmount > mUSDBalance) {
            //cant buy
            noSufficientFundsDialog();
            return;
        }
        else    {
            Double BTCAmount = USDAmount/mBuyPrice;
            mUSDBalance -= USDAmount;
            mBTCBalance += BTCAmount;
            ClickerDBHelper.updateCurrency(mDB, ClickerContract.UserData.COLUMN_USD, mUSDBalance);
            ClickerDBHelper.updateCurrency(mDB, ClickerContract.UserData.COLUMN_BITCOIN, mBTCBalance);
        }
    }

    private void sellBTC(String amount)  {
        Double BTCAmount = Double.parseDouble(amount);
        if(BTCAmount > mBTCBalance) {
            //cant buy
            noSufficientFundsDialog();
            return;
        }
        else    {
            Double USDAmount = BTCAmount*mSellPrice;
            mUSDBalance += USDAmount;
            mBTCBalance -= BTCAmount;
            ClickerDBHelper.updateCurrency(mDB, ClickerContract.UserData.COLUMN_USD, mUSDBalance);
            ClickerDBHelper.updateCurrency(mDB, ClickerContract.UserData.COLUMN_BITCOIN, mBTCBalance);
        }
    }

    private void noSufficientFundsDialog()  {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.no_money).setTitle("Warning!");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)   {
        getMenuInflater().inflate(R.menu.invest_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        switch (item.getItemId())   {
            case R.id.refresh_price:
                getPricesData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void updateBalance() {
        Cursor cursor = mDB.query(
                ClickerContract.UserData.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            mUSDBalance = cursor.getDouble(cursor.getColumnIndex(ClickerContract.UserData.COLUMN_USD));
            mBTCBalance = cursor.getDouble(cursor.getColumnIndex(ClickerContract.UserData.COLUMN_BITCOIN));
            String usd = String.format("%.2f", mUSDBalance);
            String btc = String.format("%.7f", mBTCBalance);
            mUSDBalanceTV.setText("USD Balance: $" + usd);
            mBTCBalanceTV.setText("BTC Balance: " + btc);
        }
        cursor.close();
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
        mProgressBar.setVisibility(View.INVISIBLE);
        if(data.get(0) != null && data.get(1) != null)  {

            mSellPrice = Double.parseDouble(CoinBaseUtils.parsePriceJSON(data.get(0)));
            mBuyPrice = Double.parseDouble(CoinBaseUtils.parsePriceJSON(data.get(1)));

            String sellPrice = getString(R.string.sell_price) + CoinBaseUtils.parsePriceJSON(data.get(0));
            String buyPrice =  getString(R.string.buy_price) + CoinBaseUtils.parsePriceJSON(data.get(1));
            mSellPriceTV.setText(sellPrice);
            mBuyPriceTV.setText(buyPrice);
            mProgressBar.setVisibility(View.INVISIBLE);
            mLoadingError.setVisibility(View.INVISIBLE);
        }
        else {
            mLoadingError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<String>> loader) {
        //nothing to do
    }
}
