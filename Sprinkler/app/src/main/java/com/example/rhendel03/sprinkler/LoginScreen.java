package com.example.rhendel03.sprinkler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhendel03.sprinkler.webservices.LoginAsyncInterface;
import com.example.rhendel03.sprinkler.webservices.LoginAsyncTask;


public class LoginScreen extends Activity implements LoginAsyncInterface{
    //public static final String MyPREFERENCES = "MyPrefs" ;
    EditText etusername;

    EditText etpassword;

    public void init (){
        etusername=(EditText) findViewById(R.id.uname);
        etpassword=(EditText) findViewById(R.id.upass);

        Button loginBt = (Button) findViewById(R.id.loginBt);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vuser = etusername.getText().toString();
                String vpass = etpassword.getText().toString();

                (new LoginAsyncTask(LoginScreen.this)).execute(new String[]{vuser, vpass});
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_screen);
        init();

    }



    @Override
    public void onSuccessfulLogin() {
//        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//        startActivity(intent);
//        finish();

    }

    @Override
    public void onFailedLogin() {
        Toast.makeText(this, "Login failed! Please check your user name and password.", Toast.LENGTH_LONG).show();
    }
}
