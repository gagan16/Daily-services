package com.example.gagan.dailyserices;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class RegisterTutorActivity extends AppCompatActivity {
    EditText etxtqualification,etxtcharges;
    Spinner spnoccupation,spnexperience,spncharges,spntiming1,spntiming2;
    Tutorbean tutorbean;
    Button btnregistertutor;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    android.app.AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);
     //   startActivity(new Intent(RegisterTutorActivity.this,TutorProfileActivity.class));
        initiate();

    }



    private void initiate() {
        tutorbean=new Tutorbean();
        etxtcharges=(EditText)findViewById(R.id.editcharges);
        etxtqualification=(EditText)findViewById(R.id.editqualification);
        spncharges=(Spinner)findViewById(R.id.spinnercharges);
        spnexperience=(Spinner)findViewById(R.id.spinnerexperience);
        spnoccupation=(Spinner)findViewById(R.id.spinneroccupation);
        spntiming2=(Spinner)findViewById(R.id.spinnertiming2);
        spntiming1=(Spinner)findViewById(R.id.spinnertiming1);
        btnregistertutor=(Button)findViewById(R.id.buttonregistertutor);
        spinners();
        requestQueue = Volley.newRequestQueue(this);

        btnregistertutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnfunction();
            }
        });



    }

    private void spinners() {

      //  startActivity(new Intent(RegisterTutorActivity.this,TutorProfileActivity.class));
        //timing1
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegisterTutorActivity.this,
                R.array.Timing, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spntiming1.setAdapter(adapter);

        //timing2
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(RegisterTutorActivity.this,
                R.array.Timing, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spntiming2.setAdapter(adapter1);

        //occupation
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(RegisterTutorActivity.this,
                R.array.Occupations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnoccupation.setAdapter(adapter2);

        //experience
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(RegisterTutorActivity.this,
                R.array.Experience, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnexperience.setAdapter(adapter3);

        //charges
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(RegisterTutorActivity.this,
                R.array.Charges, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spncharges.setAdapter(adapter4);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio1:
                if (checked)
                    tutorbean.setHometutor("yes");

                break;
            case R.id.radio2:
                if (checked)
                    tutorbean.setHometutor("no");
                break;
        }
    }

    private void btnfunction() {
        tutorbean.setQualification(etxtqualification.getText().toString().trim());
        tutorbean.setCharges(etxtcharges.getText().toString().trim()+" "+spncharges.getSelectedItem().toString());
        tutorbean.setOccupation(spnoccupation.getSelectedItem().toString());
        tutorbean.setExperience(spnexperience.getSelectedItem().toString());
        tutorbean.setTiming1(spntiming1.getSelectedItem().toString());
        tutorbean.setTiming2(spntiming2.getSelectedItem().toString());

        Tutorbean.Qualification=etxtqualification.getText().toString().trim();
        Tutorbean.Charges=etxtcharges.getText().toString().trim()+" "+spncharges.getSelectedItem().toString();
        Tutorbean.Occupation=spnoccupation.getSelectedItem().toString();
        Tutorbean.Timing1=spntiming1.getSelectedItem().toString()+":00";
        Tutorbean.Timing2=spntiming2.getSelectedItem().toString()+":00";
        Tutorbean.Experience=spnexperience.getSelectedItem().toString();
        Tutorbean.Hometutor=tutorbean.getHometutor();


        if (tutorbean.validatePerson2().equals("abc") ) {


                if (Util.isNetworkConnected(this)) {
                       Toast.makeText(RegisterTutorActivity.this,tutorbean.getName()+" "+tutorbean.getEmail()+""+tutorbean.getMobile()+""+tutorbean.getPassword()+""+tutorbean.getCity()+tutorbean.getOccupation()+" "+tutorbean.getCharges()+" "+tutorbean.getQualification()+" " +
                               tutorbean.getExperience()+" "+tutorbean.getTiming2()+" "+tutorbean.getTiming1(),Toast.LENGTH_LONG).show();

                    storeinfo();

                } else {
                    Toast.makeText(this, "Please check your connectivity", Toast.LENGTH_LONG).show();
                }



        }else {
            showalertdialog();

        }

    }

    private void storeinfo() {

        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es//register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            Util.registerorin="Go to Your Profile";
                            startActivity(new Intent(RegisterTutorActivity.this,TutorProfileActivity.class));finish();
                            Toast.makeText(RegisterTutorActivity.this,message,Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(RegisterTutorActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(RegisterTutorActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
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
                map.put("name",Tutorbean.Name);
                map.put("city",Tutorbean.City);
                map.put("mobile",Tutorbean.Mobile);
                map.put("password",Tutorbean.Password);
                map.put("occupation",Tutorbean.Occupation);
                map.put("qualification",Tutorbean.Qualification);
                map.put("charges",Tutorbean.Charges);
                map.put("experience",Tutorbean.Experience);
                map.put("timing1",Tutorbean.Timing1);
                map.put("timing2",Tutorbean.Timing2);
                map.put("hometutor",Tutorbean.Hometutor);
                  return map;
            }
        };
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }
    public void showalertdialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterTutorActivity.this);
        builder.setMessage(tutorbean.validatePerson2())
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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
}
