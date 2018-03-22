package com.example.osu.cryptoclicker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aruef on 3/12/2018.
 */

public class ClickerDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "clicker.db";
    private static int DB_VERSION = 1;

    public ClickerDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE =
                "CREATE TABLE " + ClickerContract.UserData.TABLE_NAME + "(" +
                        ClickerContract.UserData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ClickerContract.UserData.COLUMN_UPGRADE + " INTEGER, " +
                        ClickerContract.UserData.COLUMN_USD + " FLOAT, " +
                        ClickerContract.UserData.COLUMN_BITCOIN + " FLOAT " +
                        ");";
        db.execSQL(SQL_CREATE_TABLE);

        //insert initial user values on create
        final String SQL_USER_INSERT =
                "INSERT INTO " + ClickerContract.UserData.TABLE_NAME + "(" +
                        ClickerContract.UserData.COLUMN_UPGRADE + ", " +
                        ClickerContract.UserData.COLUMN_USD + ", " +
                        ClickerContract.UserData.COLUMN_BITCOIN + ") VALUES (" +
                        "0, 0.00, 0.00);";
        db.execSQL(SQL_USER_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClickerContract.UserData.TABLE_NAME + ";");
        onCreate(db);
    }

    //static method for easy updating of DB
    public static void updateCurrency(SQLiteDatabase db, String column, double value){
        final String SQL_UPDATE =
                "UPDATE " + ClickerContract.UserData.TABLE_NAME + " SET " +
                        column + " = " + String.valueOf(value) + " WHERE " +
                        ClickerContract.UserData._ID + " = 1;";
        db.execSQL(SQL_UPDATE);
    }

    //static method to easily update upgrades in the db
    public static void updateUpgrade(SQLiteDatabase db, int value){
        final String SQL_UPDATE =
                "UPDATE " + ClickerContract.UserData.TABLE_NAME + " SET " +
                        ClickerContract.UserData.COLUMN_UPGRADE + " = " + value + " WHERE " +
                        ClickerContract.UserData._ID + " = 1;";
        db.execSQL(SQL_UPDATE);
    }
}
