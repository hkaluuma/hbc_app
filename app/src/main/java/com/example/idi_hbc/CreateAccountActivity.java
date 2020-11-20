package com.example.idi_hbc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CreateAccountActivity extends AppCompatActivity {

        String fullname, username, district, userphone, useremail, userpassword,confirmpw;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_account);

            final EditText editTextccfullname= findViewById(R.id.editTextccfullname);
            final EditText editTextccphoneno= findViewById(R.id.editTextccphoneno);
            final EditText editTextccemail= findViewById(R.id.editTextccemail);
            final EditText editTextccusername= findViewById(R.id.editTextccusername);
            final EditText editTextccpassword= findViewById(R.id.editTextccpassword);
            final EditText editTextccconfirmpassword= findViewById(R.id.editTextccconfirmpassword);
            Button buttonccregister=findViewById(R.id.buttonccregister);

            // final Spinner spinnercategory= findViewById(R.id.spinnercategory); if global, leave it here
            displaySpinner();

            buttonccregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullname = editTextccfullname.getText().toString();
                    username = editTextccusername.getText().toString();
                    userphone = editTextccphoneno.getText().toString();
                    useremail = editTextccemail.getText().toString();
                    userpassword = editTextccpassword.getText().toString();
                    confirmpw = editTextccconfirmpassword.getText().toString();


                    // testing if there is any field which is empty

                    if (fullname.isEmpty()) {
                        editTextccfullname.setError("Full Name is Required");
                    }

                    if (username.isEmpty()) {
                        editTextccusername.setError("Username is Required");
                    }

                    if(userphone.isEmpty()){
                        editTextccphoneno.setError("Phone number is required");
                    }

                    if (useremail.isEmpty()) {
                        editTextccemail.setError("Email is Required");
                    }


                    if(userpassword.isEmpty()){
                        editTextccpassword.setError("Password number is required");
                    }

                    if(confirmpw.isEmpty()){
                        editTextccconfirmpassword.setError("Confirm Password");
                    }
                    else {

                        //check internet connection
                        if(haveNetworkConnection()){
                            // connected
                            new CreateAcc().execute();
                        }
                        else{
                            // not connected
                            Toast.makeText(CreateAccountActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();

                        }


                    }

                }
            });

        }


        // method to implement android spinner.

        public void displaySpinner() {

            // specify data source
            final String districts[] = {"Kampala", "Wakiso", "Mukono", "Jinja", "Mbarara", "Gulu", "Mbale"};
            final Spinner spinnercountry = findViewById(R.id.spinnercountry);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateAccountActivity.this, R.layout.spinnerdesign, R.id.textViewspinner, districts);
            spinnercountry.setAdapter(spinnerAdapter);

            // listener
            spinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // passing index of item selected.
                    //index= position, any variable
                    district = districts[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    district = "nothing";
                }
            });

        }



        //async task class to create new business
        class CreateAcc extends AsyncTask<String,String, String> {

            String responcefromphp;
            //create progress dialog class
            ProgressDialog pdialog;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //show dialog while registering Business
                pdialog = new ProgressDialog(CreateAccountActivity.this);

                // message can be "please wait...."
                pdialog.setMessage("please wait...");
                pdialog.setIndeterminate(false);//hold till procees is done
                pdialog.setCancelable(false);// set screen in freez
                pdialog. show();

            }

            @Override
            protected String doInBackground(String... strings) {

                //upload data to the database

                try {

                    /* seting up the connection and send data with url */
                    // create a http default client - initialize the HTTp client

                    DefaultHttpClient httpclient = new DefaultHttpClient();

                    HttpPost httppost = new HttpPost("http://agilemobiletech.com/hbc_app/register_user.php");

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                    nameValuePairs.add(new BasicNameValuePair("name", fullname));
                    nameValuePairs.add(new BasicNameValuePair("phonenumber",userphone));
                    nameValuePairs.add(new BasicNameValuePair("email",useremail));
                    nameValuePairs.add(new BasicNameValuePair("district", district));
                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("password",userpassword));


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
                    Toast.makeText(getApplicationContext(),"Try Again", Toast.LENGTH_LONG).show();
                }

                return responcefromphp;


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // dismiss dialog and perform other tasks
                pdialog.dismiss();

                if (responcefromphp.equals("1")){

                    Toast.makeText(CreateAccountActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent createintent= new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(createintent);

                }else{

                    Toast.makeText(CreateAccountActivity.this, "Account Fail Try Again", Toast.LENGTH_SHORT).show();

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
