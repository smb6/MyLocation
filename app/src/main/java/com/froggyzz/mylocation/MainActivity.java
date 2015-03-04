package com.froggyzz.mylocation;

// Parse ID SjVlzlRWBFBku8kzhOxdVGnw9Gkb291aqmqYfE0X
// Parse Client Key WxDukMgWtuWywRXgg7wlKtbQiKg21OU0nJzGZ4sZ
import android.annotation.TargetApi;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "DEBUG-->";
    private final static String APP_ID = "SjVlzlRWBFBku8kzhOxdVGnw9Gkb291aqmqYfE0X";
    private final static String CLIENT_ID = "WxDukMgWtuWywRXgg7wlKtbQiKg21OU0nJzGZ4sZ";

    MyApplication app;

    private ParseObject parseLocationObject;

    private Button showLocationGPS;
    private Button showLocationNet;
    private Button btnCloseApp;

    LocationManager locationManager;
    Location gpsLocation;
    Location netLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TAG_LOCAL = TAG + "onCreate";
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

//        app = (MyApplication)getApplication();

        showLocationGPS = (Button) findViewById(R.id.btn_showLocationGPS);
        showLocationNet = (Button) findViewById(R.id.btn_showLocationNet);
        btnCloseApp = (Button) findViewById(R.id.btn_closeApp);

        showLocationGPS.setOnClickListener(this);
        showLocationNet.setOnClickListener(this);
        btnCloseApp.setOnClickListener(this);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, APP_ID, CLIENT_ID);
//        Parse.initialize(this, "SjVlzlRWBFBku8kzhOxdVGnw9Gkb291aqmqYfE0X", "WxDukMgWtuWywRXgg7wlKtbQiKg21OU0nJzGZ4sZ");





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    public void onClick(View v) {
//        final String TAG_LOCAL = TAG + ".onClick";
//        Log.d(TAG_LOCAL, "");
        String tempText;
        boolean gpsOn;
        boolean nwOn;
        GpsStatus gpsStatus;
        final long MIN_DISTANCE_FOR_UPDATE = 10;
        final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
        double latitude = 0;
        double longitude = 0;
        boolean locationSet = false;
        String locationMethod = "";

        parseLocationObject = new ParseObject("LocationTable");

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        gpsStatus = locationManager.getGpsStatus(null);
        gpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        nwOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//        Log.d(TAG, "GPS status is: " + gpsOn);
        Toast.makeText(getApplicationContext(), "NW status is " + nwOn, Toast.LENGTH_SHORT).show();

        // Define a listener that responds to location updates
//        LocationListener locationListener = new MyLocationListener();
//        locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
//                makeUseOfNewLocation(location);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };



        if (v == showLocationGPS) {
            tempText = "showLocationGPS pressed";

            if (gpsOn) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, locationListener);
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);

                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (gpsLocation != null) {
                    latitude = gpsLocation.getLatitude();
                    longitude = gpsLocation.getLongitude();
                    Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (GPS): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();
                    locationMethod = "GPS";
                    locationSet = true;

                } else {
//                showSettingsAlert("GPS");
                    Toast.makeText(getApplicationContext(), "Can't get GPS location", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "GPS is DISABLED", Toast.LENGTH_SHORT).show();
                // TODO
                // Dialog that asks whether to turn on GPS

            }

        } else if (v == showLocationNet) {
            if (nwOn) {
                tempText = "showLocationNet pressed";
//                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, locationListener);
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
                netLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (netLocation != null) {
                    latitude = netLocation.getLatitude();
                    longitude = netLocation.getLongitude();
                    Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (NW): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();
                    locationMethod = "NW";
                    locationSet = true;
                } else {
//                showSettingsAlert("NETWORK");
                    Toast.makeText(getApplicationContext(), "Can't get network location", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "NETWORK is DISABLED", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnCloseApp) {
            finish();

        } else {
            tempText = "Button NOT RECOGNIZED";

        }

        if (locationSet) {
            parseLocationObject.put("method", locationMethod);
            parseLocationObject.put("latitude", latitude);
            parseLocationObject.put("longitude", longitude);
            parseLocationObject.saveInBackground();
        }
//        Toast.makeText(this, tempText, Toast.LENGTH_SHORT).show();
    }
}
