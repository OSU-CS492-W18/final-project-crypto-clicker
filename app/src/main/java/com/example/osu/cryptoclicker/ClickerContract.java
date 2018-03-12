package com.example.osu.cryptoclicker;

import android.provider.BaseColumns;

/**
 * Created by aruef on 3/12/2018.
 */

public class ClickerContract {
    private ClickerContract(){}

    public static class UserData implements BaseColumns{
        public static final String TABLE_NAME = "userData";
        public static final String COLUMN_UPGRADE = "upgrade";
        public static final String COLUMN_USD = "usd";
        public static final String COLUMN_BITCOIN = "bitcoin";
        //more currency columns
    }
}
