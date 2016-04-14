package com.example.rhendel03.sprinkler.webservices;

import org.json.JSONObject;

/**
 * Created by rhendel03 on 4/13/2016.
 */
public interface SSAInterface {
    public void onGetRequest(JSONObject values);
    public void onFailedGet();
}
