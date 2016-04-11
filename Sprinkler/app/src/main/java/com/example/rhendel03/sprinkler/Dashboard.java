package com.example.rhendel03.sprinkler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rhendel03.sprinkler.webservices.LoginAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncOffInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncTask;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncTaskOff;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncTask;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Dashboard extends Activity implements ScanValueAsyncInterface, RequestAsyncInterface, RequestAsyncOffInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        (new ScanValueAsyncTask(Dashboard.this)).execute();
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
//        ImageView refreshButton = (ImageView) findViewById(R.id.sprinklerImg);
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                (new RequestAsyncTask(Dashboard.this)).execute();
//            }
//        });

        ToggleButton toggle = (ToggleButton) findViewById(R.id.remoteButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    (new RequestAsyncTaskOff(Dashboard.this)).execute();

                } else {
                    (new RequestAsyncTask(Dashboard.this)).execute();
                }
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
        Toast.makeText(this, "Page successfully refreshed", Toast.LENGTH_LONG).show();
        //set to text
    }

    @Override
    public void onGetfail(JSONObject values) {
        Toast.makeText(this, "Sorry failed to refresh, check internet connection", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestSuccess() {
        Toast.makeText(this, "Sprinkler on", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestFailed() {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessfuloff() {
        Toast.makeText(this, "Sprinkler off", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedoff() {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }
}
