package be.howest.nmct.project2015;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.location.LocationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;

import be.howest.nmct.project2015.data.helper.MapOptie;
import be.howest.nmct.project2015.data.loader.MapLoader;

public class MainActivity extends ActionBarActivity implements SettingsFragment.OnSettingsListener, MapOptionsFragment.OnMapOptionsListener, LocationListener, GoogleMapFragment.OnGoogleMapListener {

    private LocationManager locationManager;
    public static LatLng LOCATION_Default = new LatLng(50.8246827, 3.251409599999988);

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private View mFrag;
    private String mapOptie = "";

    private String distance;
    private String duration;
    private String from;
    private String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.drawer, MapOptionsFragment.newInstance())
                    .add(R.id.container, SettingsFragment.newInstance(), "settings")
                    .commit();
        }
        setDrawer();
    }

    public void onSettingsSelected(String from, String to, String modes, String avoid) {
        if(from!= null) showMap(from, to, modes, avoid);
        else showDetail();
    }

    public void onGoogleMapSelected(String distance, String duration, String from, String to) {
        this.distance = distance;
        this.duration = duration;
        this.from = from;
        this.to = to;

        SettingsFragment frag = (SettingsFragment) getFragmentManager().findFragmentByTag("settings");
        frag.enableDisableControls();
        showSettings();
    }

    public void onMapOptionsSelected(String optie) {
        GoogleMapFragment fragment = (GoogleMapFragment) getFragmentManager().findFragmentByTag("googlemap");
        mapOptie = optie;
        if (fragment != null) {
            if (optie == MapOptie.Optie.NORMAL.getName()) fragment.showNormal();
            if (optie == MapOptie.Optie.SATELLITE.getName()) fragment.showSatelite();
            if (optie == MapOptie.Optie.TERRAIN.getName()) fragment.showTerrain();
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void showSettings() {
        SettingsFragment fragment = (SettingsFragment) getFragmentManager().findFragmentByTag("settings");
        getFragmentManager().popBackStack();
    }

    public void showMap(String from, String to, String modes, String avoid) {
        GoogleMapFragment newFragment = GoogleMapFragment.newInstance(from, to, modes, avoid, mapOptie);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment, "googlemap");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showDetail(){
        DetailFragment newFragment = DetailFragment.newInstance(distance, duration, from, to);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment, "detail");
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
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    public void setDrawer() {
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mFrag = findViewById(R.id.drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                /*R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mFrag);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        GoogleMapFragment frag = (GoogleMapFragment) getFragmentManager().findFragmentByTag("googlemap");
        if(frag!= null) frag.callCalback();
        else showSettings();
    }
}