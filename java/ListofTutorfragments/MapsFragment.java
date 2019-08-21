package com.example.gagan.dailyserices.ListofTutorfragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gagan.dailyserices.Adapters.Tutoradapter;
import com.example.gagan.dailyserices.Beans.ResultBean;
import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.R;
import com.example.gagan.dailyserices.Util;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {
    LocationManager locationManager;
    Place place;

    String url="http://tablepay.esy.es/retrievetutor.php";
    private ArrayList<RetreivetutorBean> tutorlist;
    RetreivetutorBean tutorbean;
    StringRequest stringRequest;
    RequestQueue requestQueue;
  //  ListView listview;
    Util util;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    Tutoradapter adapter;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        tutorbean = new RetreivetutorBean();
       // listview = (ListView)view.findViewById(R.id.mylistview);
        util=new Util();
        requestQueue = Volley.newRequestQueue(getActivity());
        mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapfragment));
        mapFragment.getMapAsync(this);


        return view;
    }
    private void getTutor() {


        stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            //Gson gson = new Gson();
                            JSONArray jsonArray = jsonObject.getJSONArray("serviceprovider");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                             //   Toast.makeText(getActivity(), c.getString("Latitude"),Toast.LENGTH_LONG).show();
                                if(Util.spinneron==1) {
                                    if (ResultBean.Occupation.equals(c.getString("Occupation"))) {
                                        LatLng locations = new LatLng(Double.parseDouble(c.getString("Latitude")), Double.parseDouble(c.getString("Longitude")));
                                        mMap.addMarker(new MarkerOptions()
                                                .title(c.getString("Name"))
                                                .snippet(c.getString("Occupation"))
                                                .position(locations));
                                    }
                                }
                                else{
                                    LatLng locations = new LatLng(Double.parseDouble( c.getString("Latitude")),Double.parseDouble(c.getString("Longitude")));
                                    mMap.addMarker(new MarkerOptions()
                                            .title(c.getString("Name"))
                                            .snippet(c.getString("Occupation"))
                                            .position(locations));}
                                }


                        }
                            //    Toast.makeText(MainActivity.this, " 1 ", Toast.LENGTH_LONG).show();
                         catch (Exception e) {

                           // Toast.makeText(getActivity(), "Some JSON Parsing Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                // failure
                new com.android.volley.Response.ErrorListener() {
                    //  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      //  Toast.makeText(getActivity(), "Some Volley Error", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(stringRequest);

    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapFragment != null) {
            mapFragment = null;
        }
    }
*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Toast.makeText(getActivity(), Util.reslatitude + "   " + Util.reslogitide+"  "+Util.resname, Toast.LENGTH_LONG).show();

        LatLng locations = new LatLng(Util.latitude,Util.longitude);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 13));
        getTutor();

    }

/*
    @Override
    public void onPause() {
        super.onPause();

        getChildFragmentManager().beginTransaction()
                .remove(mapFragment)
                .commit();
    }*/


}
