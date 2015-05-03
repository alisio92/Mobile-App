package be.howest.nmct.week9politiekortrijk.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import be.howest.nmct.week9politiekortrijk.util.Maanden;

/**
 * Created by alisio on 3/05/2015.
 */
public class SnelheidscontrolesLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private final String[] columnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.MetingColumns.COLUMN_ADRES,
                    Contract.MetingColumns.COLUMN_GEMEENTE,
                    Contract.MetingColumns.COLUMN_MAAND,
                    Contract.MetingColumns.COLUMN_AANTAL_CONTROLES,
                    Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN,
                    Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN,
                    Contract.MetingColumns.COLUMN_X,
                    Contract.MetingColumns.COLUMN_Y,
            };

    private static Object lock = new Object();
    private final String url = "http://data.kortrijk.be/snelheidsmetingen/pz_vlas.json";

    public SnelheidscontrolesLoader(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading(){
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null){
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground(){
        if(mCursor == null){
            loadCursor();
        }
        return mCursor;
    }

    private void loadCursor()
    {
        synchronized (lock){
            if(mCursor != null) return;

            MatrixCursor matrixCursor = new MatrixCursor(columnNames);
            InputStream inputStream = null;
            JsonReader jsonReader = null;

            try{
                inputStream = new URL(url).openStream();
                jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                int id = 1;
                jsonReader.beginArray();
                while(jsonReader.hasNext()){
                    jsonReader.beginObject();

                    String adres = "";
                    String gemeente = "";
                    String maand = "";
                    int controles = 0;
                    int voertuigen = 0;
                    int overtredingen = 0;
                    double x = 0.0;
                    double y = 0.0;

                    while(jsonReader.hasNext()){
                        String name = jsonReader.nextName();

                        if(name.equals(Contract.MetingColumns.COLUMN_ADRES)){
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.STRING))
                                adres = jsonReader.nextString();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_GEMEENTE)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.STRING))
                                gemeente = jsonReader.nextString();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_MAAND)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                maand += Maanden.Maand.getMaand(jsonReader.nextInt()).getMaand();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_AANTAL_CONTROLES)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                controles += jsonReader.nextInt();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                voertuigen += jsonReader.nextInt();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                overtredingen += jsonReader.nextInt();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_X)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                x += jsonReader.nextDouble();
                        }
                        else if(name.equals(Contract.MetingColumns.COLUMN_Y)) {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                y += jsonReader.nextDouble();
                        }
                        else jsonReader.skipValue();
                    }

                    MatrixCursor.RowBuilder row = matrixCursor.newRow();
                    row.add(id);
                    row.add(adres);
                    row.add(gemeente);
                    row.add(maand);
                    row.add(controles);
                    row.add(voertuigen);
                    row.add(overtredingen);
                    row.add(x);
                    row.add(y);
                    id++;

                    jsonReader.endObject();
                }
                jsonReader.endArray();

                mCursor = matrixCursor;
            }catch(Exception ex){
                Log.d("Exception occured: ", ex.getMessage());
            }finally{
                try {
                    jsonReader.close();
                }catch(Exception ex) {
                    Log.d("Exception occured: ", ex.getMessage());
                }
                try{
                    inputStream.close();
                } catch(Exception ex){
                    Log.d("Exception occured: ", ex.getMessage());
                }
            }
        }
    }
}
