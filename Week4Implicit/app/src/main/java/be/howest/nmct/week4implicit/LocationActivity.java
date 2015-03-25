package be.howest.nmct.week4implicit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class LocationActivity extends Activity {

    private Button uwLocatie;
    private TextView uwLatitudeView;
    private TextView uwLongtitudeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initVariables();
        this.uwLocatie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*GoogleMap googleMap;
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.container)).getMap();*/
                String latitude = uwLatitudeView.getText().toString();
                String longtitude = uwLongtitudeView.getText().toString();
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+ latitude + "&daddr=" + longtitude)));
            }
        });
    }

    public void initVariables(){
        this.uwLocatie = (Button) findViewById(R.id.btnLocatie);
        this.uwLatitudeView = (TextView) findViewById(R.id.txtLatitude);
        this.uwLatitudeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwLatitudeView.getText().toString().equals("Latitude")) uwLatitudeView.setText("");
                if(!hasFocus && uwLatitudeView.getText().toString().equals("")) uwLatitudeView.setText("Latitude");
            }
        });
        this.uwLongtitudeView = (TextView) findViewById(R.id.txtLongtitude);
        this.uwLongtitudeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwLongtitudeView.getText().toString().equals("Longtitude")) uwLongtitudeView.setText("");
                if(!hasFocus && uwLongtitudeView.getText().toString().equals("")) uwLongtitudeView.setText("Longtitude");
            }
        });
    }
}
