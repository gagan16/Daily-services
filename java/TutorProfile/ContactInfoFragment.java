package com.example.gagan.dailyserices.TutorProfile;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gagan.dailyserices.Beans.RetreiveresultBean;
import com.example.gagan.dailyserices.Beans.RetreivetutorBean;
import com.example.gagan.dailyserices.Beans.Tutorbean;
import com.example.gagan.dailyserices.R;
import com.example.gagan.dailyserices.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInfoFragment extends Fragment implements OnMapReadyCallback {

    TextView txtemail,txtmobile,txtaddress,txtcity;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    public ContactInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_info, container, false);
        txtemail=(TextView)view.findViewById(R.id.textViewemailid);
        txtemail.setText(RetreiveresultBean.Email);
        txtmobile=(TextView)view.findViewById(R.id.textViewmobile);
        txtmobile.setText(RetreiveresultBean.Mobile);
        txtaddress=(TextView)view.findViewById(R.id.textView24);
try{        txtaddress.setText(RetreiveresultBean.Address);}
catch (Exception e){

}
        txtcity=(TextView)view.findViewById(R.id.textViewcity);
        txtcity.setText(RetreiveresultBean.City);
        mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapFragment != null) {
            mapFragment = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Toast.makeText(getActivity(), Util.reslatitude + "   " + Util.reslogitide+"  "+Util.resname, Toast.LENGTH_LONG).show();


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
try{        LatLng locations = new LatLng(Double.parseDouble(RetreiveresultBean.Latitude),Double.parseDouble(RetreiveresultBean.Longitude));

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations, 13));
        mMap.addMarker(new MarkerOptions()
                .title(RetreiveresultBean.Name)

                .position(locations));}
catch (Exception e){

}

    }


    @Override
    public void onPause() {
        super.onPause();

        getChildFragmentManager().beginTransaction()
                .remove(mapFragment)
                .commit();
    }

}
