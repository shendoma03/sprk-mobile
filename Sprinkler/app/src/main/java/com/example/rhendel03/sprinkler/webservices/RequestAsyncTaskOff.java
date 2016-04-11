package com.example.rhendel03.sprinkler.webservices;

import android.os.AsyncTask;

import com.example.rhendel03.sprinkler.SprkConfig;
import com.example.rhendel03.sprinkler.util.WebServiceUtil;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by rhendel03 on 4/10/2016.
 */
public class RequestAsyncTaskOff extends AsyncTask<String, Void, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... strings) {
        String url = SprkConfig.WEBSERVICE_URL + "/sprinklrws/services/request/off/operation";
        InputStream inputStream = null;
        String result = "";

        try {
            return WebServiceUtil.postWS(url, new JSONObject());
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
        if(this.postInterface != null) {
            if (jsonObject != null && jsonObject.optString("status", "FAIL").equals("SUCCESS")) {
                this.postInterface.onSuccessfuloff();
            }
            else {
                this.postInterface.onFailedoff();
            }
        }
    }


    public RequestAsyncTaskOff(RequestAsyncOffInterface postInterface) {
        this.postInterface = postInterface;
    }

    private RequestAsyncOffInterface postInterface;


}

