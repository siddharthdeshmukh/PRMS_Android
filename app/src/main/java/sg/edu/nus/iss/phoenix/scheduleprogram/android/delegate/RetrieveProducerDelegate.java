package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ReviewSelectPresenterProducerController;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_PRODUCER;

/**
 * Created by Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class RetrieveProducerDelegate extends AsyncTask<String, Void, String> {
    // Tag for logging
    private static final String TAG = RetrieveProducerDelegate.class.getName();

    private ReviewSelectPresenterProducerController reviewSelectPresenterProducerController = null;

    // constructor
    public RetrieveProducerDelegate(ReviewSelectPresenterProducerController reviewSelectPresenterProducerController) {
        this.reviewSelectPresenterProducerController = reviewSelectPresenterProducerController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_PRODUCER).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<Producer> producers = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray producerArray = reader.getJSONArray("producerList");

                for (int i = 0; i < producerArray.length(); i++) {
                    JSONObject producerJson = producerArray.getJSONObject(i);
                    String id = producerJson.getString("id");
                    String name = producerJson.getString("name");

                    producers.add(new Producer(id,name));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (reviewSelectPresenterProducerController != null)
            reviewSelectPresenterProducerController.producersRetrieved(producers);
    }
}
