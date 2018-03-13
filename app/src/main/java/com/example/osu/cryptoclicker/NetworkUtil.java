package com.example.osu.cryptoclicker;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mohammed Algadhib on 3/12/2018.
 */

public class NetworkUtil {
    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String url) throws IOException   {
        Request request = new Request.Builder().url(url).build();

        Response response = mHTTPClient.newCall(request).execute();
        try {
            return response.body().string();
        }
        finally {
            response.close();
        }
    }
}
