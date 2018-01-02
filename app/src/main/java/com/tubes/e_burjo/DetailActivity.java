package com.tubes.e_burjo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    double longitude=0;
    double latitude=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this)
        ;

        Intent i= this.getIntent();

        //RECEIVE DATA
        longitude = Double.parseDouble(i.getExtras().getString("LONGITUDE_WARUNG"));
        latitude =Double.parseDouble(i.getExtras().getString("LATITUDE_WARUNG"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng rumah = new LatLng (latitude,longitude);
        // googleMap.addMarker(new MarkerOptions().position(rumah).title("Rumah Kesayanganku"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(rumah));
        moveToCurrentLocation(rumah,googleMap);
        googleMap.addMarker(new MarkerOptions() .position(rumah).title("MY LOCATION") .snippet("kochi").draggable(true));
    }

    private void moveToCurrentLocation(LatLng currentLocation, GoogleMap googleMap)
    {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);


    }
}
