package com.example.rhendel03.sprinkler.webservices;

import android.os.AsyncTask;

import com.example.rhendel03.sprinkler.SprkConfig;
import com.example.rhendel03.sprinkler.util.WebServiceUtil;

import org.json.JSONObject;

/**
 * Created by rhendel03 on 4/13/2016.
 */
public class SprinStatAsyncTask extends AsyncTask<Void, Void, JSONObject> {

    private JSONObject output;

    @Override
    protected JSONObject doInBackground(Void... strings) {
        String url = SprkConfig.WEBSERVICE_URL + "/sprinklrws/services/request/latest";
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
            activity.onGetRequest(jsonObject);
        }
    }

    public SprinStatAsyncTask() {
        super();
    }

    private SSAInterface activity;

    public SprinStatAsyncTask(SSAInterface activity) {
        this.activity = activity;
    }

}

