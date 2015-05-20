package be.howest.nmct.project2015.data.json;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private GoogleMap map;
    private ParserTask parserTask;

    public GoogleMap getMap() {
        return map;
    }

    public DownloadTask(GoogleMap map){
        this.map = map;
    }

    public ParserTask getParserTask() {
        return parserTask;
    }

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try{
            data = downloadUrl(url[0]);
        }catch(Exception e){
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        parserTask = new ParserTask(map);
        map = parserTask.getMap();
        parserTask.execute(result);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception downloading", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}