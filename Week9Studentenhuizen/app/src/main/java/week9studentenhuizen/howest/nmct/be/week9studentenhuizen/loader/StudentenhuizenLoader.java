package week9studentenhuizen.howest.nmct.be.week9studentenhuizen.loader;

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

/**
 * Created by alisio on 24/04/2015.
 */
public class StudentenhuizenLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private final String[] columnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.KotColumns.COLUMN_ADRES,
                    Contract.KotColumns.COLUMN_HUISNUMMER,
                    Contract.KotColumns.COLUMN_BISNUMMER,
                    Contract.KotColumns.COLUMN_GEMEENTE,
                    Contract.KotColumns.COLUMN_AANTAL_KAMERS
            };

    private static Object lock = new Object();
    private final String url = "http://data.kortrijk.be/studentenvoorzieningen/koten.json";

    public StudentenhuizenLoader(Context context)
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
                    String huisnummer = "";
                    String bisnummer = "";
                    String gemeente = "";
                    int aantalKamers = 0;

                    while(jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equals(Contract.KotColumns.COLUMN_ADRES)) adres = jsonReader.nextString();
                        else if(name.equals(Contract.KotColumns.COLUMN_HUISNUMMER)){
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.STRING))
                                huisnummer = jsonReader.nextString();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                huisnummer += jsonReader.nextInt();
                        }
                        else if(name.equals(Contract.KotColumns.COLUMN_BISNUMMER))
                        {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.STRING))
                                bisnummer = jsonReader.nextString();
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                bisnummer += jsonReader.nextInt();
                        }
                        else if(name.equals(Contract.KotColumns.COLUMN_GEMEENTE))
                        {
                            gemeente = jsonReader.nextString();
                        }
                        else if(name.equals(Contract.KotColumns.COLUMN_AANTAL_KAMERS))
                        {
                            if(jsonReader.peek().equals(JsonToken.NULL))
                                jsonReader.skipValue();
                            else if(jsonReader.peek().equals(JsonToken.STRING))
                                aantalKamers = Integer.parseInt(jsonReader.nextString());
                            else if(jsonReader.peek().equals(JsonToken.NUMBER))
                                aantalKamers = jsonReader.nextInt();
                        }
                        else jsonReader.skipValue();
                    }

                    MatrixCursor.RowBuilder row = matrixCursor.newRow();
                    row.add(id);
                    row.add(adres);
                    row.add(huisnummer);
                    row.add(bisnummer);
                    row.add(gemeente);
                    row.add(aantalKamers);
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
