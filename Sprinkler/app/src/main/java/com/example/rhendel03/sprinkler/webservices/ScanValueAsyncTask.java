package com.example.rhendel03.sprinkler.webservices;

import android.os.AsyncTask;

import com.example.rhendel03.sprinkler.SprkConfig;
import com.example.rhendel03.sprinkler.util.WebServiceUtil;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by rhendel03 on 12/13/2015.
 */
public class ScanValueAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    private JSONObject output;

    @Override
    protected JSONObject doInBackground(Void... strings) {
        String url = SprkConfig.WEBSERVICE_URL + "/sprinklrws/services/scan/display";
        String result = "";

        try {

            return WebServiceUtil.getWS(url);
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

        if (jsonObject != null) {
            activity.onGetValues(jsonObject);
        }
    }

    public ScanValueAsyncTask() {
        super();
    }

    private ScanValueAsyncInterface activity;

    public ScanValueAsyncTask(ScanValueAsyncInterface activity) {
        this.activity = activity;
    }

}

