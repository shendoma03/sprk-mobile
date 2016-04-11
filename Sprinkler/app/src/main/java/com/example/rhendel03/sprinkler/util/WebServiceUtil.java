package com.example.rhendel03.sprinkler.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rhendel03 on 12/13/2015.
 */
public class WebServiceUtil {

    public static JSONObject getWS(String wsUrl) throws Exception {
        URL url;
        HttpURLConnection urlConn;

        try {
            url = new URL(wsUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed: HTTP error code: " + urlConn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String buf;
            while ((buf = br.readLine()) != null) {
                sb.append(buf);
            }
            br.close();

            JSONObject result = null;
            try {
                result = new JSONObject(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed: Response did not return JSON string: " + e.getMessage());
            }

            return result;
        }
        catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed: IOException: " + e.getMessage());
        }
    }

    public static JSONObject postWS(String wsUrl, JSONObject inputData) {
        URL url;
        HttpURLConnection urlConn;

        try {
            url = new URL(wsUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.connect();

            OutputStream os = new DataOutputStream(urlConn.getOutputStream());
            os.write(inputData.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            if (!(urlConn.getResponseCode() == HttpURLConnection.HTTP_OK ||urlConn.getResponseCode() == HttpURLConnection.HTTP_CREATED  )) {
                throw new RuntimeException("Failed: HTTP error code: " + urlConn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String buf;
            while ((buf = br.readLine()) != null) {
                sb.append(buf);
            }
            br.close();

            JSONObject result = null;
            try {
                System.out.println("Message response: "+sb.toString());
                result = new JSONObject(sb.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed: Response did not return JSON string: " + e.getMessage());
            }

            return result;
        }
        catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed: IOException: " + e.getMessage());
        }
    }
}
