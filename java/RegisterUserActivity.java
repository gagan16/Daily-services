package com.example.gagan.dailyserices;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Adapters.Tutoradapter;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    EditText etxtname,etxtemail,etxtmobile,etxtpass;
    Spinner spncity;
    Button btnregisteruser,btntologin;
      Tutorbean tutorbean;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    android.app.AlertDialog alert;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();
        initiate();
        Toast.makeText(RegisterUserActivity.this,"Name -"+Tutorbean.Name+" Email -"+Tutorbean.Email+" Mobile -"+Tutorbean.Mobile+" City ="+Tutorbean.City+" Password -"+Tutorbean.Password,Toast.LENGTH_LONG).show();


    }

    private void initiate() {
        etxtemail=(EditText)findViewById(R.id.editemail);
        //spncity=(Spinner)findViewById(R.id.spinnercity);
        etxtpass=(EditText)findViewById(R.id.editPassword);
        etxtmobile=(EditText)findViewById(R.id.editmobile);
        etxtname=(EditText)findViewById(R.id.editname);
        btntologin=(Button)findViewById(R.id.buttonlogin);
        btntologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterUserActivity.this,LoginActivity.class));
                finish();
            }
        });
        btnregisteruser=(Button)findViewById(R.id.buttonRegister);
        requestQueue = Volley.newRequestQueue(this);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        autocompleteFragment.setFilter(typeFilter);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                tutorbean.setCity(place.getName().toString().trim());
                Tutorbean.City=place.getName().toString().trim();
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //    Log.i(TAG, "An error occurred: " + status);
            }
        });
        btnregisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnfunction();
            }
        });
        tutorbean=new Tutorbean();


    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
    private void btnfunction() {
        tutorbean.setEmail(etxtemail.getText().toString().trim());
        Tutorbean.Email= etxtemail.getText().toString().trim();
        Tutorbean.Name=etxtname.getText().toString().trim();
        tutorbean.setName(etxtname.getText().toString().trim());
       // tutorbean.setCity(spncity.getSelectedItem().toString());
        tutorbean.setMobile(etxtmobile.getText().toString().trim());
        Tutorbean.Mobile=etxtmobile.getText().toString().trim();
        tutorbean.setPassword(etxtpass.getText().toString().trim());
        Tutorbean.Password=etxtpass.getText().toString().trim();


        if (tutorbean.validatePerson().equals("abc") ) {
            if (isEmailValid(tutorbean.getEmail())) {

                if (Util.isNetworkConnected(this)) {
                    //   Toast.makeText(RegisterActivity.this," 1 ",Toast.LENGTH_LONG).show();

                    storeinfo();

                } else {
                    Toast.makeText(this, "Please check your connectivity", Toast.LENGTH_LONG).show();
                }


            } else if (!isEmailValid(tutorbean.getEmail())) {
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
        }else {
            showalertdialog();

        }

    }

    private void storeinfo() {


    stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/registeruser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            editor.putString(Util.KEY_NAME,Tutorbean.Name);
                            editor.putString(Util.KEY_EMAIL,Tutorbean.Email);
                            editor.putString(Util.KEY_PHONE, Tutorbean.Mobile);

                            editor.commit();
                                                        if(success==1) {
                                startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));finish();
                            }

                        }catch (Exception e){
                            Toast.makeText(RegisterUserActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(RegisterUserActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley",volleyError.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",etxtemail.getText().toString().trim());
                map.put("name",etxtname.getText().toString().trim());
                map.put("city",tutorbean.getCity());
                map.put("mobile",etxtmobile.getText().toString().trim());
                map.put("password",etxtpass.getText().toString().trim());
                return map;
            }
        };
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }

    private void checktutor() {


        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/checktutor.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            if(success==1){
                                Util.registerorin="Go to your profile";
                                Util.nodialog=1;
                            }
                            //checktutor();
                            Toast.makeText(RegisterUserActivity.this,message,Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            Toast.makeText(RegisterUserActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(RegisterUserActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley",volleyError.getMessage());
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
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                tutorbean.setCity(place.getName().toString().trim());
                //   Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //  Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    public void showalertdialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterUserActivity.this);
        builder.setMessage(tutorbean.validatePerson())
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
}
