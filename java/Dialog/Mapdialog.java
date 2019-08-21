package com.example.gagan.dailyserices.Dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gagan.dailyserices.R;
import com.example.gagan.dailyserices.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by gagan on 24/3/17.
 */

public class Mapdialog extends AppCompatDialogFragment implements OnMapReadyCallback,GoogleMap.OnCameraMoveListener {

    GoogleMap mMap;
    TextView txtmarkerset;
    SupportMapFragment mapFragment;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.setlocationdialog,null);
        txtmarkerset=(TextView)v.findViewById(R.id.textViewsetlocation);
        txtmarkerset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), Util.latitude+" "+Util.longitude , Toast.LENGTH_SHORT).show();

            }
        });
        mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        DialogInterface.OnClickListener listner=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };


        return new AlertDialog.Builder(getActivity())
                .setTitle("Set Location")
                .setView(v)
                .create();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        Toast.makeText(getActivity(), Util.latitude+" "+Util.longitude , Toast.LENGTH_SHORT).show();
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
        mMap.setOnCameraMoveListener(this);;

    }


    @Override
    public void onPause() {
        super.onPause();

        getChildFragmentManager().beginTransaction()
                .remove(mapFragment)
                .commit();
    }



    @Override
    public void onCameraMove() {

        Util.latitude=mMap.getCameraPosition().target.latitude;

        Util.longitude=mMap.getCameraPosition().target.longitude;
        Toast.makeText(getActivity(), Util.latitude+" "+Util.longitude , Toast.LENGTH_SHORT).show();
    }
}
