package com.example.gagan.dailyserices;

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

public class ChangePassActivity extends AppCompatActivity {

    EditText etxtoldpass,etxtnewpass;
    Button btnchangepass;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        requestQueue = Volley.newRequestQueue(this);
        etxtnewpass=(EditText)findViewById(R.id.editTextNewPassword);
        etxtoldpass=(EditText)findViewById(R.id.editTextoldpassword);
        btnchangepass=(Button)findViewById(R.id.buttonchangepass);
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Tutorbean.Password.equals(etxtoldpass.getText().toString().trim())){
                    changepass();
                    finish();
                }
            }
        });


    }

    private void changepass() {

        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/UpdateProfile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            //checktutor();
                            Toast.makeText(ChangePassActivity.this,"Password changed",Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                           // Toast.makeText(TutorProfileActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                      //  Toast.makeText(TutorProfileActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                        Log.i("Volley", volleyError.toString());
                        Log.i("Volley",volleyError.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("dialogname", "Password");
                map.put("dialognameinput",etxtnewpass.getText().toString().trim());
                map.put("email",Tutorbean.Email);

                return map;
            }

        }; requestQueue.add(stringRequest);

    }
}
