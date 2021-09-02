package com.example.googlemaps;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemaps.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void showAddressMap(View view) {
        EditText editText = (EditText) findViewById(R.id.search);
        String data = editText.getText().toString();
        List<Address> address = null;

        if (data != null || !data.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                address = geocoder.getFromLocationName(data, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1 = address.get(0);
            LatLng latLng = new LatLng(address1.getLatitude(), address1.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in your place..."));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));
        }


    }

    public void mapType(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void onZoom(View view) {
        if (view.getId() == R.id.zoomIn) {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view.getId() == R.id.zoomOut) {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(18.502375885181202, 73.86368915672831);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}