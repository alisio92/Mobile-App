package be.howest.nmct.project2015.data.json;

import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.howest.nmct.project2015.data.Contract;
import be.howest.nmct.project2015.data.helper.Step;

/**
 * Created by alisio on 26/04/2015.
 */
public class DirectionsJSONParser {

    public static List<Step> polylines;
    public static String distance;
    public static String duration;
    public static String from;
    public static String to;

    public static String getDistance() {
        return distance;
    }

    public static String getDuration() {
        return duration;
    }

    public static String getFrom() {
        return from;
    }

    public static String getTo() {
        return to;
    }

    public static List<Step> getPolylines() {
        return polylines;
    }

    public List<List<HashMap<String,String>>> parse(JSONObject jObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        polylines = new ArrayList<>();
        distance = "";
        duration = "";
        from = "";
        to = "";

        try {
            jRoutes = jObject.getJSONArray(Contract.MapColumns.COLUMN_ROUTES);

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray(Contract.MapColumns.COLUMN_LEGS);
                List path = new ArrayList<HashMap<String, String>>();
                distance = (String)((JSONObject)((JSONObject)jLegs.get(i)).get(Contract.MapColumns.COLUMN_DISTANCE)).get(Contract.MapColumns.COLUMN_TEXT);
                duration = (String)((JSONObject)((JSONObject)jLegs.get(i)).get(Contract.MapColumns.COLUMN_DURATION)).get(Contract.MapColumns.COLUMN_TEXT);
                from = (String)((JSONObject)jLegs.get(i)).get(Contract.MapColumns.COLUMN_STARTADRES);
                to = (String)((JSONObject)jLegs.get(i)).get(Contract.MapColumns.COLUMN_ENDADRES);

                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray(Contract.MapColumns.COLUMN_STEPS);
                    for(int k=0;k<jSteps.length();k++){
                        Step step = new Step();
                        String polyline = "";
                        String distance = "";
                        String duration = "";
                        String html = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get(Contract.MapColumns.COLUMN_POLYLINE)).get(Contract.MapColumns.COLUMN_POINTS);
                        distance = (String)((JSONObject)((JSONObject)jSteps.get(k)).get(Contract.MapColumns.COLUMN_DISTANCE)).get(Contract.MapColumns.COLUMN_TEXT);
                        duration = (String)((JSONObject)((JSONObject)jSteps.get(k)).get(Contract.MapColumns.COLUMN_DURATION)).get(Contract.MapColumns.COLUMN_TEXT);
                        String temp = (String)((JSONObject)jSteps.get(k)).get(Contract.MapColumns.COLUMN_HTML);
                        html = temp.replaceAll("<.*?>", " ");
                        step.setDistance(distance);
                        step.setDuration(duration);
                        step.setHtml(html);
                        step.setPoint(polyline);
                        polylines.add(step);
                        List<LatLng> list = decodePoly(polyline);

                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }

        return routes;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}