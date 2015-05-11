package be.howest.nmct.project2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

import be.howest.nmct.project2015.data.Helper;
import be.howest.nmct.project2015.data.DownloadTask;
import be.howest.nmct.project2015.data.MapOptie;

public class GoogleMapFragment extends Fragment {//implements OnMapReadyCallback {

    private GoogleMap map;
    ArrayList<LatLng> markerPoints;
    public static final String FROM = "be.howest.nmct.NEW_FROM";
    public static final String TO = "be.howest.nmct.NEW_TO";
    public static final String TRANSITMODE = "be.howest.nmct.NEW_TRANSITMODE";;
    public static final String AVOID = "be.howest.nmct.NEW_AVOID";;
    public static final String MODE = "be.howest.nmct.NEW_MODE";;
    private LatLng locationFrom = null;
    private LatLng locationTo = null;
    private String trannsitMode = "";
    private String avoid = "";
    private String mode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String from = getArguments().getString(FROM);
            String to = getArguments().getString(TO);
            mode = getArguments().getString(MODE);
            trannsitMode = getArguments().getString(TRANSITMODE);
            avoid = getArguments().getString(avoid);
            locationFrom = Helper.getLocationFromAddress(from, getActivity());
            locationTo = Helper.getLocationFromAddress(to, getActivity());
        }
    }

    public void initMap(){
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        markerPoints = new ArrayList<LatLng>();

        LatLng loc = MainActivity.LOCATION_Default;
        if(locationFrom!= null) loc = locationFrom;
        setLocation(loc, 15);

        if(map!=null) {
            mapSettings();
            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

                @Override
                public boolean onMyLocationButtonClick() {
                    setLocation(MainActivity.LOCATION_Default, 15);
                    return true;
                }
            });

            setMarker(locationFrom);
            setMarker(locationTo);

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {
                    setMarker(point);
                }
            });
        }
    }

    public void setLocation(LatLng location, Integer z){
        CameraUpdate center = CameraUpdateFactory.newLatLng(location);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(z);
        map.moveCamera(center);
        map.animateCamera(zoom);
    }

    public void mapSettings(){
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setIndoorLevelPickerEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
    }

    public void setMarker(LatLng point){
        if (markerPoints.size() > 1) {
            markerPoints.clear();
            map.clear();
        }
        markerPoints.add(point);
        MarkerOptions options = new MarkerOptions();
        options.position(point);
        if (markerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (markerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        map.addMarker(options);
        if (markerPoints.size() >= 2) {
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);
            //driving walking bicycling transit
            //avoid=tolls avoid=highways avoid=ferries
            String url = Helper.getDirectionsUrl(origin, dest, trannsitMode, avoid);
            DownloadTask downloadTask = new DownloadTask(map);
            map = downloadTask.getMap();
            downloadTask.execute(url);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_google_map, container, false);
        initMap();
        if(mode == MapOptie.Optie.NORMAL.getName()) showNormal();
        if(mode == MapOptie.Optie.SATELLITE.getName()) showSatelite();
        if(mode == MapOptie.Optie.TERRAIN.getName()) showTerrain();
        return v;
    }

    public void showSatelite() {
        LatLng latLng = map.getCameraPosition().target;
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom);
        map.animateCamera(update);
    }

    public void showTerrain() {
        LatLng latLng = map.getCameraPosition().target;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom);
        map.animateCamera(update);

    }

    public void showNormal() {
        LatLng latLng = map.getCameraPosition().target;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom);
        map.animateCamera(update);
    }
}
