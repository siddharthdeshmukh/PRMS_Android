package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

/**
 * Created by Mathura on 01-10-2017.
 */
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.scheduleprogram.util.Util;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

public class DeleteScheduleDelegate extends AsyncTask<ProgramSlot,Void, Boolean> {
    //tag for logging//
    private static final String TAG = DeleteScheduleDelegate.class.getName();

    private final ScheduleController scheduleController;

    public DeleteScheduleDelegate(ScheduleController scheduleController) {

        this.scheduleController = scheduleController;
    }

    protected Boolean doInBackground(ProgramSlot... params) {
        // Encode the name of radio program in case of the presence of special characters.
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, "delete").buildUpon().build();
        String name = null;
        try {
            name = URLEncoder.encode(params[0].getRadioProgram().getRadioProgramName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        builtUri = Uri.withAppendedPath(builtUri, name);
        builtUri = Uri.withAppendedPath(builtUri, Util.convertProgramDateToJSONString(params[0].getScheduleDate()));
        builtUri = Uri.withAppendedPath(builtUri, Util.convertProgramTimeToJSONString(params[0].getScheduleStartTime()));
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        boolean success = false;
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setUseCaches(false);
            System.out.println(httpURLConnection.getResponseCode());
            Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        scheduleController.scheduleDeleted(result.booleanValue());
    }

}

