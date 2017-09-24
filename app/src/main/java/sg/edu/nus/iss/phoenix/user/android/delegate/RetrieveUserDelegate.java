package sg.edu.nus.iss.phoenix.user.android.delegate;

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

import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by siddharth on 9/21/2017.
 */

public class RetrieveUserDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrieveUserDelegate.class.getName();
    private UserController userController;

    public RetrieveUserDelegate(UserController userController) {
        this.userController = userController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_USER).buildUpon().build();
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
        List<User> users = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray userArray = reader.getJSONArray("userList");

                for (int i = 0; i < userArray.length(); i++) {
                    List<Role> roles = new ArrayList<>();
                    JSONObject userJson = userArray.getJSONObject(i);
                    String id = userJson.getString("id");
                    String name = userJson.getString("name");
                    String password = userJson.getString("password");
                    JSONArray rolesArray = userJson.getJSONArray("roles");
                    for (int j=0; j<rolesArray.length();j++){
                        JSONObject roleJson = rolesArray.getJSONObject(j);
                        String role = roleJson.getString("role");
                        if(!role.isEmpty()) {
                            Role roleObject = new Role(role);
                            roles.add(roleObject);
                        }
                    }
                    Log.v(TAG, "User is "+ name);

                    users.add(new User(id, name, password, (ArrayList<Role>) roles));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (userController != null)
            userController.userRetrieved(users);

    }
}
