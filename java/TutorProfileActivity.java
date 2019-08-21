
package com.example.gagan.dailyserices;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;
import android.provider.MediaStore;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Adapters.DownloadImageTask;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.Dialog.Mapdialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class TutorProfileActivity extends AppCompatActivity  implements OnMapReadyCallback {

    TextView txtname,txtemail,txtmobile,txtoccupation,dialogtxtsetlocation,txtqualification,txthometutor,txtexperience,txttiming,txtcharges,txtaddress,txtcity;
    Button btnchangepass;;
    android.app.AlertDialog alert;
    String dialogtext="skip>";
    ImageView imgmobile,imgoccupation,imgqualification,imghometutor,imgtiming,imgcharges,imgaddress,imgcity,imgexperience;
    private static int RESULT_LOAD_IMG = 1;
     ImageView imgView;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ImageView imgprofile;
    Bitmap bitmap;
    MapView mMapView;
    GoogleMap mMap;
    String s;
    String[] split ;  SupportMapFragment mapFragment;
    String name;
    LatLng center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
        s=Tutorbean.Email;
        split = s.split("@");
        requestQueue = Volley.newRequestQueue(this);
        name = split[0];
       // imgView=(ImageView)findViewById(R.id.imageView11);

        if(Util.tutorornot==0){
            showalertdialogs();
            showalertdialog();
        }
        mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        initiate();
        getImage();

    }

    private void getImage() {


        try{ new DownloadImageTask(imgprofile).execute("http://tablepay.esy.es/photos/"+name+".png");}
        catch (Exception e){
            imgprofile.setImageResource(R.drawable.defaultprofilepic);
        }

    }

    public void showalertdialog() {
      android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TutorProfileActivity.this);
      LayoutInflater inflater = this.getLayoutInflater();
      builder.setTitle("Profile Picture")
              .setCancelable(false)
              .setView(inflater.inflate(R.layout.uploadprofielpicdialog, null))
              .setPositiveButton(dialogtext, new DialogInterface.OnClickListener() {
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

    public void showalertdialogs() {

        final Dialog dialog = new Dialog(TutorProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.setlocationdialog);
        dialog.show();

        MapsInitializer.initialize(TutorProfileActivity.this);

        mMapView = (MapView) dialog.findViewById(R.id.map);
        dialogtxtsetlocation=(TextView)dialog.findViewById(R.id.textViewsetlocation);
        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();// needed to get the map to display immediately
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng locations = new LatLng(Util.latitude,Util.longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 12));
                dialogtxtsetlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        center = googleMap.getCameraPosition().target;
                        Tutorbean.Latitude=Double.toString(center.latitude);
                        Tutorbean.Longitide=Double.toString(center.longitude);
                        insertlnglat();
                        dialog.cancel();
                    }
                });

              //  Toast.makeText(MainActivity.this,center.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void insertlnglat()   {
        stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/lnglatregister.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            Util.tutorornot=1;
                            Intent mIntent = getIntent();
                            finish();
                            startActivity(mIntent);
                         //   Util.registerorin="Go to Your Profile";
                          ////  startActivity(new Intent(RegisterTutorActivity.this,TutorProfileActivity.class));//finish();
                          //  Toast.makeText(RegisterTutorActivity.this,message,Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(TutorProfileActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                        }finally {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(TutorProfileActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                     //   Log.i("Volley", volleyError.toString());
                        //Log.i("Volley",volleyError.getMessage());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",Tutorbean.Email);
                map.put("latitude",Tutorbean.Latitude);
                map.put("longitude",Tutorbean.Longitide);

                return map;
            }
        };
        // Now the request shall be executed
        requestQueue.add(stringRequest);

    }


    private void initiate() {

        imgprofile=(ImageView)findViewById(R.id.imageView11);

        //textview
        txtname=(TextView)findViewById(R.id.textViewname);
        txtname.setText(Tutorbean.Name);

        txtemail=(TextView)findViewById(R.id.textViewemail);
        txtemail.setText(Tutorbean.Email);

        txtmobile=(TextView)findViewById(R.id.textViewmobile);
        txtmobile.setText(Tutorbean.Mobile);

        txthometutor=(TextView)findViewById(R.id.textViewhometutor);
        txthometutor.setText(Tutorbean.Hometutor);

        txtcity=(TextView)findViewById(R.id.textViewcity);
        txtcity.setText(Tutorbean.City);

        txtaddress=(TextView)findViewById(R.id.textViewaddress);
        txtaddress.setText(Tutorbean.Address);

        txttiming=(TextView)findViewById(R.id.textViewtiming);
        txttiming.setText("Available from "+Tutorbean.Timing1+" to "+Tutorbean.Timing2);

        txtexperience=(TextView)findViewById(R.id.textViewexperience);
        txtexperience.setText(Tutorbean.Experience);

        txtcharges=(TextView)findViewById(R.id.textViewCharges);
        txtcharges.setText(Tutorbean.Charges);

        txtqualification=(TextView)findViewById(R.id.textViewqualification);
        txtqualification.setText(Tutorbean.Qualification);

        txtoccupation=(TextView)findViewById(R.id.textViewoccupation);
        txtoccupation.setText(Tutorbean.Occupation);

        //Button
        btnchangepass=(Button)findViewById(R.id.buttonchangepass);
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(TutorProfileActivity.this,ChangePassActivity.class));


            }
        });
        //imageview
        imgcity=(ImageView)findViewById(R.id.imageViewcity);
        imgcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="City";
                dialogbox();
            }
        });
        imgmobile=(ImageView)findViewById(R.id.imageViewmobile);
        imgmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Mobile";
                dialogbox();
            }
        });
        imghometutor=(ImageView)findViewById(R.id.imageViewhometutor);
        imghometutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Hometutor";
                dialogbox();
            }
        });
        imgtiming=(ImageView)findViewById(R.id.imageViewtiming);
        imgtiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Timing";
                dialogbox();
            }
        });
        imgcharges=(ImageView)findViewById(R.id.imageViewcharges);
        imgcharges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Charges";
                dialogbox();
            }
        });
        imgexperience=(ImageView)findViewById(R.id.imageViewexperience);
        imgexperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Experience";
                dialogbox();
            }
        });
        imgqualification=(ImageView)findViewById(R.id.imageViewqualification);
        imgqualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Qualification";
                dialogbox();
            }
        });
        imgoccupation=(ImageView)findViewById(R.id.imageViewoccupation);
        imgoccupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Occupation";
                dialogbox();
            }
        });
        imgaddress=(ImageView)findViewById(R.id.imageViewaddress);
        imgaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.DialogName="Address";
                dialogbox();
            }
        });

    }

    private void dialogbox() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TutorProfileActivity.this);
        builder.setTitle(Util.DialogName)
                .setCancelable(true);
        final EditText input = new EditText(this);
            input.setText("Enter new "+Util.DialogName);
//Set an EditText view to the Alert Dialog to get user inputs.
//input is the variable form where you will get the input value from user.
        builder.setView(input);

              builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        // alert.dismiss();
                        Util.DialogNameinput=input.getEditableText().toString();
                        if (alert != null && alert.isShowing()) {


                            stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/UpdateProfile.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {

                                            try {
                                                //    Toast.makeText(getActivity(), Util.placeid+"     "+date.getText().toString().trim()+"      "+table_for.getSelectedItem().toString()+"  "+time.getText().toString().trim(),Toast.LENGTH_LONG).show();
                                                JSONObject jsonObject = new JSONObject(s);
                                                int success = jsonObject.getInt("success");
                                                String message = jsonObject.getString("message");
                                                if(success==1){
                                                    if( Util.DialogName.equals("City")){
                                                        txtcity.setText(Util.DialogNameinput);
                                                    }

                                                    if( Util.DialogName.equals("Hometutor")){
                                                        txthometutor.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Timing")){
                                                        txttiming.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Occupation")){
                                                        txtoccupation.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Qualification")){
                                                        txtqualification.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Charges")){
                                                        txtcharges.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Mobile")){
                                                        txtmobile.setText(Util.DialogNameinput);

                                                    }
                                                    if( Util.DialogName.equals("Address")){
                                                        txtaddress.setText(Util.DialogNameinput);

                                                    }

                                                }
                                                //checktutor();
                                                Toast.makeText(TutorProfileActivity.this,message,Toast.LENGTH_LONG).show();

                                            }catch (Exception e){
                                                Toast.makeText(TutorProfileActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                                            }finally {

                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            Toast.makeText(TutorProfileActivity.this,"VolleyError "+volleyError, Toast.LENGTH_LONG).show();
                                            Log.i("Volley", volleyError.toString());
                                            Log.i("Volley",volleyError.getMessage());
                                        }
                                    }
                            )

                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();

                                    map.put("dialogname", Util.DialogName);
                                    map.put("dialognameinput",Util.DialogNameinput);
                                    map.put("email",Tutorbean.Email);

                                    return map;
                                }

                            }; requestQueue.add(stringRequest);


                            alert.dismiss();
                        }
                    }
                })
                      .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
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

    public void onprofilepicdialogs(View view) {
// Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri filePath = data.getData();
                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    //Setting the Bitmap to ImageView
                     imgView = (ImageView) findViewById(R.id.imageView11);
                    imgView.setImageBitmap(bitmap);
                    uploadImage();
                } catch (IOException e) {
                    imgView.setImageResource(R.drawable.defaultprofilepic);
                    e.printStackTrace();
                }

                alert.dismiss();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
       // final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tablepay.esy.es/upload2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                       // loading.dismiss();
                        //Showing toast message of the response
                       // Toast.makeText(TutorProfileActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                      //  loading.dismiss();

                        //Showing toast
                        Toast.makeText(TutorProfileActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
             //   String name = editTextName.getText().toString().trim();

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("image", image);
                params.put("name", name);

                //returning parametersname
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        if (ActivityCompat.checkSelfPermission(TutorProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LatLng locations ;
        try {
            locations = new LatLng(Double.parseDouble(Tutorbean.Latitude),Double.parseDouble(Tutorbean.Longitide));
        } catch (NumberFormatException e) {
            locations = new LatLng(30.9010,75.8573);
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 13));
        mMap.addMarker(new MarkerOptions()
                    .position(locations)
                    .title(Tutorbean.Name));

    }
}


