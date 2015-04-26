package be.howest.nmct.project2015;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.location.LocationListener;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends ActionBarActivity implements SettingsFragment.OnSettingsListener, LocationListener{

    private LocationManager locationManager;
    public static LatLng LOCATION_Default = new LatLng(50.8246827, 3.251409599999988);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance(), "settings")
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void onSettingsSelected(String from, String to) {
        showMap(from, to);
    }

    public void showMap(String from, String to){
        GoogleMapFragment newFragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putString(GoogleMapFragment.FROM, from);
        args.putString(GoogleMapFragment.TO, to);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        LOCATION_Default = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
