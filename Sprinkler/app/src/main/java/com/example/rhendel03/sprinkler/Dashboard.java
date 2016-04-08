package com.example.rhendel03.sprinkler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhendel03.sprinkler.webservices.LoginAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncTask;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncTask;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Dashboard extends Activity implements ScanValueAsyncInterface, LoginAsyncInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        refresh();
        sprinkler();
    }

    public void refresh() {
        Button refreshButton = (Button) findViewById(R.id.ref);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ScanValueAsyncTask(Dashboard.this)).execute();
            }
        });
    }

    public void sprinkler() {
        ImageView refreshButton = (ImageView) findViewById(R.id.sprinklerImg);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new RequestAsyncTask(Dashboard.this)).execute();
            }
        });
    }



    @Override
    public void onGetValues(JSONObject values) {
        String soil = values.optString("smlvl");

        TextView soilTv = (TextView)findViewById(R.id.soil);
        soilTv.setText(soil);

        String water = values.optString("wlvl");
        TextView wtrLvl = (TextView)findViewById(R.id.waterLevel);
        wtrLvl.setText(water);

        String temperature = values.optString("temp");
        TextView temperatureView = (TextView) findViewById(R.id.temp);
        temperatureView.setText(temperature);

        long dateT = values.optLong("createDate");
        Date convertDate = new Date(dateT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        TextView date  = (TextView)findViewById(R.id.date);
        date.setText(sdf.format(convertDate));
        //set to text
    }

    @Override
    public void onSuccessfulLogin() {
        Toast.makeText(this, "Sprinkler on", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedLogin() {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }
}
