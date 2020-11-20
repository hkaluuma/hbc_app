package com.example.idi_hbc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {

    //variables
    String pass, username;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    public static final String name = "username";
    public static final String MYPREFERENCES_LOGIN = "MyPreferences_002";
    SharedPreferences.Editor editor;
    EditText editTextusername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextusername = findViewById(R.id.editTextusername);
        final EditText editTextpassword = findViewById(R.id.editTextpassword);
        final TextView textViewcreateacc = findViewById(R.id.textViewcreateacc);
        final TextView textViewforgotpassword = findViewById(R.id.textViewforgotpassword);


        textViewcreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textViewcreateaccintent= new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(textViewcreateaccintent);
            }
        });

        textViewforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent textViewforgotpasswordintent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(textViewforgotpasswordintent);
            }
        });


        Button buttonlogin = findViewById(R.id.buttonlogin);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass = editTextpassword.getText().toString();
                username = editTextusername.getText().toString();

                if (username.isEmpty()) {
                    editTextusername.setError("User Name is Required");
                }

                if (pass.isEmpty()) {
                    editTextpassword.setError("Password is required");

                } else {

                    if (haveNetworkConnection()) {
                        // connected
                        new CreateLogin().execute();
                    } else {
                        // not connected
                        Toast.makeText(LoginActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });

        
    } //end of onCreate(Bundle savedInstanceState)

    //begin of createlogin method
    class CreateLogin extends AsyncTask<String, String, String> {

        String responcefromphp;
        String myresult;
        //create progress dialog class
        ProgressDialog pdialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //show dialog while registering Business
            pdialog = new ProgressDialog(LoginActivity.this);


            pdialog.setMessage("Please wait...");
            pdialog.setIndeterminate(false);//hold till procees is done
            pdialog.setCancelable(false);// set screen in freez
            pdialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            //upload data to the database

            try {

                /* seting up the connection and send data with url */
                // create a http default client - initialize the HTTp client

                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("https://www.agilemobiletech.com/hbc_app/authentic.php");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("password", pass));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                InputStream inputStream = response.getEntity().getContent();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                responcefromphp = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
            }

            return responcefromphp;


        }

        @Override
        protected void onPostExecute(String result) {

            // TODO Auto-generated method stub
            super.onPostExecute(result);

            //equalsIgnoreCase("add")
            if (responcefromphp.equals("0")) {
                Toasty.warning(getApplicationContext(), "Invalid Login Credentials!", Toast.LENGTH_LONG, true).show();
                //after showing error on login then reload the same page
                Intent loginactivityintent= new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(loginactivityintent);
                //Toast.makeText(getApplicationContext(), "Invalid Login Credentials!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(LoginActivity.this, "Invalid Login Credentials!", Toast.LENGTH_SHORT).show();

                //these two lines below cause the app to crash
                //editTextusername.setEnabled(true);
                //editTextPassword.setEnabled(true);


            } else {
                String[] usercredentials = responcefromphp.split("#");
                Log.e("responcefromphp", responcefromphp);

                SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFERENCES_LOGIN, Activity.MODE_PRIVATE);
                editor = mySharedPreferences.edit();
                editor.putString("username", usercredentials[0]);
                editor.putString("phonenumber", usercredentials[1]);
                editor.putString("email", usercredentials[2]);
                editor.putString("location", usercredentials[3]);
                editor.putString("id", usercredentials[4]);
                editor.putString("password", usercredentials[5]);
                editor.putString("registrationdate", usercredentials[6]);
                editor.commit();


                Toasty.success(getApplicationContext(), "Login successfull!", Toast.LENGTH_LONG, true).show();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        }

    }


    //method to check internet availability(WiFi and MobileData)
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}