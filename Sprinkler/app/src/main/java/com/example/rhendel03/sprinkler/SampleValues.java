package com.example.rhendel03.sprinkler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rhendel03.sprinkler.util.WebServiceUtil;
import com.example.rhendel03.sprinkler.webservices.LoginAsyncTask;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncTask;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;


public class SampleValues extends Activity implements ScanValueAsyncInterface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_values);
        ref();
    }
    public void ref(){
        Button loginBt = (Button) findViewById(R.id.ref);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ScanValueAsyncTask(SampleValues.this)).execute();
            }
        });
    }


    @Override
    public void onGetValues(JSONObject values) {
        String soil = values.optString("soil");
        TextView soilTv = (TextView)findViewById(R.id.soil);
        soilTv.setText(soil);

        String water = values.optString("water");
        TextView waterLevel = (TextView)findViewById(R.id.waterlevel);
        waterLevel.setText(water);

        String temperature = values.optString("temperature");
        TextView temperatureView = (TextView) findViewById(R.id.temp);
        temperatureView.setText(temperature);
        //set to text
    }
}
