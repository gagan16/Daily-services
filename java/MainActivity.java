package com.example.gagan.dailyserices;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Beans.ResultBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rl1,rl2;

    TextView txtRegister;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    //initializationa and setonclick listner
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.tutorornot=0;
        Util.cityon=0;
      Util.spinneron=0;
        txtRegister=(TextView)findViewById(R.id.textViewRegistertutor);
      //  Toast.makeText(MainActivity.this,"tutor or not "+ Util.tutorornot+" text "+txtRegister.getText(),Toast.LENGTH_LONG).show();
        rl1=(RelativeLayout)findViewById(R.id.relativelayout1);
        rl2=(RelativeLayout)findViewById(R.id.relativelayout2);

        requestQueue = Volley.newRequestQueue(this);

        retrieve();


    }



    private void retrieve() {
        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://tablepay.esy.es/retrieveinfo.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            Util.tutorornot = 1;
                          //  Toast.makeText(MainActivity.this, "tutor or not " + Util.tutorornot + " text " + txtRegister.getText(), Toast.LENGTH_LONG).show();

                            Tutorbean.Hometutor = jsonObject.getString("Hometutor");
                            Tutorbean.Charges = jsonObject.getString("Charges");
                            Tutorbean.Qualification = jsonObject.getString("Qualification");
                            Tutorbean.Occupation = jsonObject.getString("Occupation");
                            Tutorbean.Experience = jsonObject.getString("Experience");
                            Tutorbean.Timing1 = jsonObject.getString("Timing1");
                            Tutorbean.Timing2 = jsonObject.getString("Timing2");
                       //     Toast.makeText(MainActivity.this, "tutor or not " + Util.tutorornot + " text " + txtRegister.getText(), Toast.LENGTH_LONG).show();
                            next();
                            try {
                                Tutorbean.Address = jsonObject.getString("Address");
                                Tutorbean.Latitude = jsonObject.getString("Latitude");
                                Tutorbean.Longitide = jsonObject.getString("Longitude");
                            } catch (Exception e) {

                            }
                       //    Toast.makeText(MainActivity.this, Tutorbean.Timing1 + "" + Tutorbean.Address + "" + Tutorbean.Timing2
                          //          + "" + Tutorbean.Experience + "" + Tutorbean.Hometutor + " " + Tutorbean.City + " " + Tutorbean.Mobile + "       " + Util.nodialog, Toast.LENGTH_LONG).show();


                            //    Toast.makeText(MainActivity.this, Tutorbean.Email+""+Tutorbean.Name+" "+Tutorbean.City+" "+ Tutorbean.Mobile, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            next();
                           // Toast.makeText(MainActivity.this, "Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("email", Tutorbean.Email);

                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void next() {
        if(Util.tutorornot==1){
            txtRegister.setText("Go To Profile");
        //    Toast.makeText(MainActivity.this, "tutor or not " + Util.tutorornot + " text " + txtRegister.getText(), Toast.LENGTH_LONG).show();

           // Toast.makeText(MainActivity.this, "Timing -"+Tutorbean.Timing1+" "+Tutorbean.Timing2+" Address-"+Tutorbean.Address+" Experience -"+Tutorbean.Experience+
           //         " Hometutor -"+Tutorbean.Hometutor, Toast.LENGTH_LONG).show();

        }
        else {
            Tutorbean.Latitude="30.8929";Tutorbean.Longitide="75.8219";
            //Toast.makeText(MainActivity.this,"Name -"+Tutorbean.Name+" Email -"+Tutorbean.Email+" Mobile -"+Tutorbean.Mobile+" City ="+Tutorbean.City+" Password -"+Tutorbean.Password,Toast.LENGTH_LONG).show();

        }

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListofTutorActivity.class));

            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.tutorornot==1){
                    startActivity(new Intent(MainActivity.this,TutorProfileActivity.class));

                }
                else {
                    startActivity(new Intent(MainActivity.this, RegisterTutorActivity.class));
                }
            }
        });

    }




}
