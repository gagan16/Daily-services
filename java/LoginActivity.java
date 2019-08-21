package com.example.gagan.dailyserices;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Beans.Tutorbean;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText etxtemail,etxtpass;
    Button btnlogin,btntoregister,btnresetpass;
    String text="There is an error";
    android.app.AlertDialog alert;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    int i;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        initiate();

    }


// all the initialisation and setonclick listners
    private void initiate() {

        //start
        etxtemail=(EditText)findViewById(R.id.editloginemail);
        etxtpass=(EditText)findViewById(R.id.editloginpassword);
        btnlogin=(Button)findViewById(R.id.buttonlogin);
        btntoregister=(Button)findViewById(R.id.buttontoregister);
      //  btnresetpass=(Button)findViewById(R.id.btn_reset_password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onlogin();
            }
        });
        btntoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterUserActivity.class));
                finish();
            }
        });


    }


// to check pass and email
    private void onlogin() {
        Tutorbean.Password=etxtpass.getText().toString().trim();
        Tutorbean.Email=etxtemail.getText().toString().trim();

        if ((Tutorbean.Email.isEmpty())&&(i==0)) {
            text = "Please enter emal id";
            i=1;
            showalertdialog();
        }
        if ((Tutorbean.Password.isEmpty())&&(i==0)) {
            text = "Please enter password";
            i=1;
            showalertdialog();
        }
        if ((!isEmailValid(Tutorbean.Email))&&(i==0)) {i=1;
            //Toast.makeText(RegisterActivity.this,"12345",Toast.LENGTH_LONG).show();
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please check email id")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if ((Tutorbean.Password.length() < 6)&&(i==0)) {
            text = "password should be more than 6 letter";
            i=1;
            showalertdialog();
        }
        if(i==0){

            allow();

        }
    }

    //check if the user is registered
    private void allow() {

        stringRequest = new StringRequest(Request.Method.POST,"http://tablepay.esy.es/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            if(success == 1) {
                                getInfo();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            }
                            editor.putString(Util.KEY_EMAIL,Tutorbean.Email);


                            editor.commit();
                           // Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                         //   editor.putString(Util.KEY_EMAIL,personbean.getEmail());
                        //    editor.commit();
                            //   Toast.makeText(SignUpActivity.this,message,Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            Toast.makeText(LoginActivity.this,"Some deep Error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LoginActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",Tutorbean.Email);

                map.put("password",Tutorbean.Password);

                return map;
            }
        }
        ;

        // Now the request shall be executed
        requestQueue.add(stringRequest);



    }

    private void getInfo() {
        //   Toast.makeText(MainActivity.this,Tutorbean.Email,Toast.LENGTH_LONG).show();
        stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://tablepay.esy.es/retrieve.php",
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            Tutorbean.Name=jsonObject.getString("NAME");
                            Tutorbean.Mobile=jsonObject.getString("MOBILE");
                            Tutorbean.City=jsonObject.getString("CITY");
                            Toast.makeText(LoginActivity.this,"Name -"+Tutorbean.Name+" Email -"+Tutorbean.Email+" Mobile -"+Tutorbean.Mobile+" City ="+Tutorbean.City+" Password -"+Tutorbean.Password,Toast.LENGTH_LONG).show();

                          //  Toast.makeText(LoginActivity.this, Tutorbean.Email+""+Tutorbean.Name+" "+Tutorbean.City+" "+ Tutorbean.Mobile, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                            Toast.makeText(LoginActivity.this, " 2Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LoginActivity.this, "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",Tutorbean.Email);

                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    //email is valid or not
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    //if there is a problem show it
    public void showalertdialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        // alert.dismiss();
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                });

        alert = builder.create();
        alert.show();

    }




    @Override
    protected void onDestroy() {
        try {
            if (alert != null && alert.isShowing()) {
                alert.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
