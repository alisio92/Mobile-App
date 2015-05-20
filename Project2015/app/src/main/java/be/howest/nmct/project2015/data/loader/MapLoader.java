package be.howest.nmct.project2015.data.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import be.howest.nmct.project2015.data.Contract;
import be.howest.nmct.project2015.data.helper.Helper;
import be.howest.nmct.project2015.data.helper.Step;

/**
 * Created by alisio on 19/05/2015.
 */
public class MapLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private final String[] columnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.MapColumns.COLUMN_DISTANCE,
                    Contract.MapColumns.COLUMN_DURATION,
                    Contract.MapColumns.COLUMN_STARTADRES,
                    Contract.MapColumns.COLUMN_ENDADRES,
                    Contract.MapColumns.COLUMN_ARRAY,
            };

    private static Object lock = new Object();
    private String url = "";
    private GoogleMap map;
    private List<Step> polylines = new ArrayList<>();

    public List<Step> getPolylines() {
        return polylines;
    }

    public MapLoader(Context context, String u) {
        super(context);
        url = u;
        //map = m;
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (mCursor == null) {
            loadCursor();
        }
        return mCursor;
    }

    private void loadCursor() {
        synchronized (lock) {
            if (mCursor != null) return;

            MatrixCursor matrixCursor = new MatrixCursor(columnNames);
            InputStream inputStream = null;
            JsonReader jsonReader = null;

            try {
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                int id = 1;
                jsonReader.beginObject();

                String distance = "";
                String duration = "";
                String startAdres = "";
                String endAdres = "";
                //List<Step> polylines = new ArrayList<>();

                while (jsonReader.hasNext()) {
                    String route = getNextName(jsonReader);
                    if(route!= ""){
                        if (route.equals(Contract.MapColumns.COLUMN_ROUTES)) {
                            jsonReader.beginArray();
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String legs = getNextName(jsonReader);
                                if(legs!= ""){
                                    if (legs.equals(Contract.MapColumns.COLUMN_LEGS)) {
                                        jsonReader.beginArray();
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            String name = getNextName(jsonReader);
                                            if (name.equals(Contract.MapColumns.COLUMN_DISTANCE))
                                                distance = getText(jsonReader, Contract.MapColumns.COLUMN_TEXT);

                                            if (name.equals(Contract.MapColumns.COLUMN_DURATION))
                                                duration = getText(jsonReader, Contract.MapColumns.COLUMN_TEXT);

                                            if (name.equals(Contract.MapColumns.COLUMN_STARTADRES))
                                                startAdres = jsonReader.nextString();

                                            if (name.equals(Contract.MapColumns.COLUMN_ENDADRES))
                                                endAdres = jsonReader.nextString();

                                            if (name.equals(Contract.MapColumns.COLUMN_STEPS)) {
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    jsonReader.beginObject();
                                                    String dur = "";
                                                    String dis = "";
                                                    String point = "";
                                                    String html = "";
                                                    while (jsonReader.hasNext()) {
                                                        String steps = getNextName(jsonReader);
                                                        if (steps.equals(Contract.MapColumns.COLUMN_POLYLINE)) {
                                                            point = getText(jsonReader, Contract.MapColumns.COLUMN_POINTS);
                                                        }

                                                        if (steps.equals(Contract.MapColumns.COLUMN_HTML)) {
                                                            html = jsonReader.nextString();
                                                        }

                                                        if (steps.equals(Contract.MapColumns.COLUMN_INNER_DISTANCE)) {
                                                            dis = getText(jsonReader, Contract.MapColumns.COLUMN_TEXT);
                                                        }

                                                        if (steps.equals(Contract.MapColumns.COLUMN_INNER_DURATION)) {
                                                            dur = getText(jsonReader, Contract.MapColumns.COLUMN_TEXT);
                                                        }

                                                        if (steps == "") {
                                                            jsonReader.skipValue();
                                                        }
                                                    }
                                                    Step step = new Step();
                                                    step.setDistance(dis);
                                                    step.setDuration(dur);
                                                    step.setHtml(html);
                                                    step.setPoint(point);
                                                    if (point != "" && dur!= "" && dis!="" && html!="") polylines.add(step);
                                                    jsonReader.endObject();
                                                }
                                                jsonReader.endArray();
                                            }

                                            if(name == "") {
                                                jsonReader.skipValue();
                                            }
                                        }
                                        jsonReader.endObject();
                                        jsonReader.endArray();
                                    }
                                }else jsonReader.skipValue();
                            }
                            jsonReader.endObject();
                            jsonReader.endArray();
                        }else jsonReader.skipValue();
                    }
                }

                MatrixCursor.RowBuilder row = matrixCursor.newRow();
                row.add(id);
                row.add(distance);
                row.add(duration);
                row.add(startAdres);
                row.add(endAdres);
                row.add(polylines);
                id++;

                jsonReader.endObject();

                mCursor = matrixCursor;
                this.stopLoading();

            } catch (Exception ex) {
                Log.d("Exception occured: ", ex.getMessage());
            } finally {
                try {
                    jsonReader.close();
                } catch (Exception ex) {
                    Log.d("Exception occured: ", ex.getMessage());
                }
                try {
                    inputStream.close();
                } catch (Exception ex) {
                    Log.d("Exception occured: ", ex.getMessage());
                }
            }
        }
    }

    private String getNextName(JsonReader jsonReader){
        String next = "";
        try{
            next = jsonReader.nextName();
        }catch(Exception e){

        }
        return next;
    }

    private String getText(JsonReader jsonReader, String field){
        String txt = "";
        try{
            jsonReader.beginObject();
            while (jsonReader.hasNext()){
                String name = getNextName(jsonReader);
                if(name.equals(field)) {
                    txt = jsonReader.nextString();
                }else jsonReader.skipValue();
            }
            jsonReader.endObject();
        }catch (Exception e){

        }
        return txt;
    }
}
