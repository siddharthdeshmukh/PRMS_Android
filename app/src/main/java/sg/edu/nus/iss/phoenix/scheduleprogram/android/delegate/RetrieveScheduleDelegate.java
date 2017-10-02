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

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ReviewSelectScheduleController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.scheduleprogram.util.Util;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * Created by thushara on 9/26/2017.
 */

public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String>{

    private static final String TAG = RetrieveScheduleDelegate.class.getName();
    private ScheduleController scheduleController = null;
    private ReviewSelectScheduleController reviewSelectScheduleController = null;

    public RetrieveScheduleDelegate(ScheduleController scheduleController) {
        this.reviewSelectScheduleController = null;
        this.scheduleController = scheduleController;
    }

    public RetrieveScheduleDelegate(ReviewSelectScheduleController reviewSelectScheduleController) {
        this.scheduleController = null;
        this.reviewSelectScheduleController = reviewSelectScheduleController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
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
        Log.i(TAG,jsonResp);
        return jsonResp;
}

    @Override
    protected void onPostExecute(String result) {
        List<ProgramSlot> programSlots = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray spArray = reader.getJSONArray("spList");

                for (int i = 0; i < spArray.length(); i++) {
                    JSONObject spJson = spArray.getJSONObject(i);
                    JSONObject radioProgramObject = spJson.getJSONObject("radioProgram");
                    String programName = radioProgramObject.getString("name");
                    RadioProgram radioProgram = new RadioProgram();
                    radioProgram.setRadioProgramName(programName);
                    String dateOfProgram = spJson.getString("dateOfProgram");
                    Integer duration = spJson.getInt("duration");
                    String startTime = spJson.getString("startTime");
                    String presenter="";
                    if (spJson.has("presenter")){
                        presenter  = spJson.getString("presenter");
                    }
                    String producer="";
                    if (spJson.has("producer")){
                        presenter  = spJson.getString("producer");
                    }
                    programSlots.add(new ProgramSlot(radioProgram, Util.convertProgramStringToDate(dateOfProgram), duration, Util.convertStringToDate(startTime), presenter, producer));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (scheduleController != null){
            Log.v("splist size ", String.valueOf(programSlots.size()));
            scheduleController.scheduleRetrieved(programSlots);
        }
        else if (reviewSelectScheduleController != null)
            reviewSelectScheduleController.programsRetrieved(programSlots);
    }
}
