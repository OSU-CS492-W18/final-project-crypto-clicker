package com.example.osu.cryptoclicker;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 3/22/2018.
 */

public class Upgrade {
    private final static String TAG = "Upgrade";

    private final static String TAG_UPGRADE = "upgrade";
    private final static String TAG_NAME = "name";
    private final static String TAG_AMOUNT = "amount";
    private final static String TAG_COST = "cost";

    private String mName;
    private double mAmount;
    private double mCost;

    public Upgrade(){}

    public Upgrade(String name, double amount, double cost){
        mName = name;
        mAmount = amount;
        mCost = cost;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public double getAmount(){
        return mAmount;
    }

    public void setAmount(double amount){
        mAmount = amount;
    }

    public double getCost(){
        return mCost;
    }

    public void setCost(double cost){
        mCost = cost;
    }

    public static ArrayList<Upgrade> parseXML(Context context, int resourceID){
        ArrayList<Upgrade> upgradeList = new ArrayList<>();
        InputStream xmlStream;

        try {
            xmlStream = context.getResources().openRawResource(resourceID);

            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(xmlStream, null);
            parser.nextTag();

            Upgrade upgrade = new Upgrade();
            while(parser.next() != XmlPullParser.END_TAG){
                if(parser.getEventType() != XmlPullParser.START_TAG){
                    continue;
                }

                switch (parser.getName()){
                    case TAG_UPGRADE:
                        upgrade = new Upgrade();
                        break;

                    case TAG_NAME:
                        parser.next();
                        upgrade.setName(parser.getText());
                        parser.nextTag();
                        break;

                    case TAG_AMOUNT:
                        parser.next();
                        upgrade.setAmount(Double.valueOf(parser.getText()));
                        parser.nextTag();
                        break;

                    case TAG_COST:
                        parser.next();
                        upgrade.setCost(Double.valueOf(parser.getText()));
                        upgradeList.add(upgrade);
                        parser.nextTag();
                        parser.nextTag();
                        break;

                    default:
                        Log.d(TAG, "something else: " + parser.getName());
                }
            }
        } catch (XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return upgradeList;
    }
}
