package com.example.rhendel03.sprinkler.webservices;

import android.os.AsyncTask;


import com.example.rhendel03.sprinkler.SprkConfig;
import com.example.rhendel03.sprinkler.util.WebServiceUtil;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by rhendel03 on 12/13/2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... strings) {
        String url = SprkConfig.WEBSERVICE_URL + "/sprinklrws/services/access/validate/user";
        InputStream inputStream = null;
        String result = "";

        try {
            JSONObject input = new JSONObject();
            input.accumulate("userName", strings[0]);
            input.accumulate("password", strings[1]);
            return WebServiceUtil.postWS(url, input);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        // if we called the AsyncTask with the activity object as the constructor parameter
        // call the appropriate methods based on the status
        if(this.activity != null) {
            if (jsonObject != null && jsonObject.optString("status", "FAIL").equals("SUCCESS")) {
                this.activity.onSuccessfulLogin();
            }
            else {
                this.activity.onFailedLogin();
    }
}
    }

    public LoginAsyncTask() {
        super();
    }

    private LoginAsyncInterface activity;

    public LoginAsyncTask(LoginAsyncInterface activity) {
        this.activity = activity;
    }
}

