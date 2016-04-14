package com.example.rhendel03.sprinkler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rhendel03.sprinkler.webservices.LoginAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.NotificationView;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncOffInterface;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncTask;
import com.example.rhendel03.sprinkler.webservices.RequestAsyncTaskOff;
import com.example.rhendel03.sprinkler.webservices.SSAInterface;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.ScanValueAsyncTask;
import com.example.rhendel03.sprinkler.webservices.SprinStatAsyncTask;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Dashboard extends Activity implements ScanValueAsyncInterface, RequestAsyncInterface, RequestAsyncOffInterface,SSAInterface {
    Button refreshButton;
    Switch toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        (new ScanValueAsyncTask(Dashboard.this)).execute();
        (new SprinStatAsyncTask(Dashboard.this)).execute();
        refresh();
        sprinkler();
        timeLoop();

    }//protected
    public void timeLoop (){
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                timeLoop2();
                (new ScanValueAsyncTask(Dashboard.this)).execute();
                (new SprinStatAsyncTask(Dashboard.this)).execute();
            }
        }.start();
    }
    public void timeLoop2 (){
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                timeLoop();
                (new ScanValueAsyncTask(Dashboard.this)).execute();
                (new SprinStatAsyncTask(Dashboard.this)).execute();
            }
        }.start();
    }


    public void refresh() {
        refreshButton = (Button) findViewById(R.id.ref);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ScanValueAsyncTask(Dashboard.this)).execute();
                (new SprinStatAsyncTask(Dashboard.this)).execute();
            }
        });
    }

    public void sprinkler() {


         toggle = (Switch) findViewById(R.id.remoteButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    (new RequestAsyncTask(Dashboard.this)).execute();
                    (new SprinStatAsyncTask(Dashboard.this)).execute();

                } else {

                    (new RequestAsyncTaskOff(Dashboard.this)).execute();
                    (new SprinStatAsyncTask(Dashboard.this)).execute();
                }
            }
        });
    }





    @Override
    public void onGetValues(JSONObject values) {
        Intent intent = new Intent(this, Dashboard.class);
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Status")
                .setContentText("Water Level: HIGH")
                .setSmallIcon(R.drawable.waterpic)
                .setContentIntent(pIntent)
                .setSound(uri)
                .setAutoCancel(true).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String soil = values.optString("smlvl");
        TextView soilTv = (TextView)findViewById(R.id.soil);
        soilTv.setText(soil);

        String water = values.optString("wlvl");
        TextView wtrLvl = (TextView)findViewById(R.id.waterLevel);
        wtrLvl.setText(water);
        if (water.equals("high")){
            notificationManager.notify(0, n);
        }

        String temperature = values.optString("temp");
        TextView temperatureView = (TextView) findViewById(R.id.temp);
        temperatureView.setText(temperature);

        long dateT = values.optLong("createDate");
        Date convertDate = new Date(dateT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        TextView date  = (TextView)findViewById(R.id.date);
        date.setText(sdf.format(convertDate));
        //Toast.makeText(this, "Page successfully refreshed", Toast.LENGTH_SHORT).show();
        //set to text

    }



    @Override
    public void onGetfail() {
        Toast.makeText(this, "Sorry failed to refresh, check internet connection", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestSuccess() {
        //Toast.makeText(this, "Sprinkler on", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestFailed() {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessfuloff() {
        //Toast.makeText(this, "Sprinkler off", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailedoff() {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetRequest(JSONObject values) {
        String status = values.optString("operation");
        String stat = "";
        if(status.equals("true")){
            stat = "on";
            toggle.setChecked(true);
        }else{
            stat = "off";
            toggle.setChecked(false);
        }
        TextView statusv = (TextView) findViewById(R.id.tvStat);
        statusv.setText(stat);
    }

    @Override
    public void onFailedGet() {

    }
}
