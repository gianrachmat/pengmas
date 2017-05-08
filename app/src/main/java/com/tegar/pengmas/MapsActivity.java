package com.tegar.pengmas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker marker;
    // deklarasi variabel
    double lat, lng; // latitude dan longitude
    GoogleApiClient googleApiClient; // membuat klien dari Google API
    LocationRequest locationRequest; //
    protected Location location; //
    private TextView lati, lngi;

    // interval pembaruan pada detik
    private static int UPDATE_INTERVAL = 5000; // 5 sec
    private static int FASTEST_INTERVAL = 2500; // 2.5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lati = (TextView) findViewById(R.id.lat);
        lngi = (TextView) findViewById(R.id.lng);

        lati.setText("");
        lngi.setText("");

        if (checkPlayService()){
            buildGoogleApiClient();
            createLocationRequest();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions options = new MarkerOptions();
        LatLng latLng = new LatLng(lat, lng);
        options.position(latLng).draggable(true);
        marker = mMap.addMarker(options);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.remove();
                marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                );
                String str = String.valueOf(marker.getPosition().latitude);
                lati.setText(str);
                str = String.valueOf(marker.getPosition().longitude);
                lngi.setText(str);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("klik", "klik Marker");
                backTo(marker);
//                marker.showInfoWindow();
                return true;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                backTo(marker);
            }
        });
    }

    private void backTo(Marker marker){
        String lat = ""+marker.getPosition().latitude;
        String lng = ""+marker.getPosition().longitude;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("lat", ""+lat);
        intent.putExtra("lng", ""+lng);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        displayLocation();
        updateMarker();
    }

    private void displayLocation(){
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();

//            lati.setText(""+lat);
//            lngi.setText(""+lng);
        } else {
//            lati.setText("Mencari");
//            lngi.setText("Mencari");
        }
    }

    private void updateMarker(){
        LatLng sydney = new LatLng(lat, lng);
        Log.d("lat lat lng",""+lat+", "+lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19));
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean checkPlayService() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int resultCode = googleApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApi.isUserResolvableError(resultCode)) {
                googleApi.getErrorDialog(this, resultCode, 9000).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  }, 1234);

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    protected void stopLocationupdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationupdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationupdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayService();
        if(googleApiClient.isConnected()) startLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null){
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Error", "Koneksi gagal, kode = "+connectionResult.getErrorCode());
    }
}
