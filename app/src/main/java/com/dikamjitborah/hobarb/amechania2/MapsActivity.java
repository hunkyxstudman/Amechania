package com.dikamjitborah.hobarb.amechaniaapp;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import static com.dikamjitborah.hobarb.amechaniaapp.AddContacts.s2;
import static com.dikamjitborah.hobarb.amechaniaapp.AddContacts.s4;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Button b1, b2, b3;
    double x, y;
    TextView textView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");

            }
        }
    }

    public void centerMapOnLocation(Location location, String title) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        x = location.getLatitude();
        y = location.getLongitude();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
        mMap.addMarker(new MarkerOptions().position(userLocation).title(title));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "Metro.otf");

        SharedPreferences sp2 = getSharedPreferences("key", 0);
        String tValue2 = sp2.getString("textvalue2","");
        s2 = tValue2;

        SharedPreferences sp4 = getSharedPreferences("key", 0);
        String tValue4 = sp4.getString("textvalue4","");
        s4 = tValue4;

        textView = findViewById(R.id.hobarb);
        textView.setTypeface(typeface1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sochna);
        b1 = findViewById(R.id.buttonhelpme);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(s2.isEmpty() || s4.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Add contacts first", Toast.LENGTH_SHORT);
                }
                else
                {

                    String messageToSend = "HELP ME! I AM HERE -> https://maps.google.com/?ll=" + x + ","+ y;

                    SmsManager.getDefault().sendTextMessage(s2, null, messageToSend , null,null);
                    SmsManager.getDefault().sendTextMessage(s4, null, messageToSend , null,null);

                }



                Toast.makeText(getApplicationContext(), "Messages have been sent" , Toast.LENGTH_LONG).show();
            }
        });
        b2 = findViewById(R.id.buttoncontacts);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, AddContacts.class);
                startActivity(intent);
            }
        });

        b3 = findViewById(R.id.buttonabout);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                centerMapOnLocation(location, "Your Location");

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                centerMapOnLocation(lastKnownLocation, "Your Location");
            } else {
                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                centerMapOnLocation(loc, "Your Location");
            }

        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null) {
                    centerMapOnLocation(lastKnownLocation, "Your Location");
                } else {
                    Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    centerMapOnLocation(loc, "Your Location");
                }

            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }
        }


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS service allowed", Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},1);
            Toast.makeText(getApplicationContext(), "SMS service allowed", Toast.LENGTH_SHORT).show();

        }
    }
}
