package be.nmct.howest.week6horoscoop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String EXTRA_BIRTHYEAR = "be.howest.nmct.week6horoscoop.BIRTHYEAR";
    public static final String EXTRA_HOROSCOOP = "be.howest.nmct.week6horoscoop.HOROSCOOP";
    public static final int REQUEST_BIRTHDAY = 1;
    public static final int REQUEST_HOROSCOOP = 2;

    Button btnGeboortejaar, btnHoroscoop;
    TextView txtGeboortejaar;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGeboortejaar = (Button) findViewById(R.id.btngeboorte);
        btnGeboortejaar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selecteerGeboortejaar(v);
            }
        });

        btnHoroscoop = (Button) findViewById(R.id.btnHoroscoop);
        btnHoroscoop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selecteerHoroscoop(v);
            }
        });
    }

    public void selecteerGeboortejaar(View v){
        Intent intent = new Intent(MainActivity.this, SelectGeboortejaarActivity.class);
        startActivityForResult(intent, REQUEST_BIRTHDAY);
    }

    public void selecteerHoroscoop(View v)
    {
        Intent intent = new Intent(MainActivity.this, HoroscoopActivity.class);
        startActivityForResult(intent, REQUEST_HOROSCOOP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode){
            case REQUEST_BIRTHDAY:

                txtGeboortejaar = (TextView) findViewById(R.id.txtJaar);
                String geboortejaar = data.getStringExtra(EXTRA_BIRTHYEAR);
                txtGeboortejaar.setText("Geboortejaar: " + geboortejaar);
                break;
            case REQUEST_HOROSCOOP:
                //FOTO VERANDEREN
                icon = (ImageView) findViewById(R.id.imgHoroscoop);
                int icoon = data.getIntExtra(EXTRA_HOROSCOOP, 0);
                icon.setImageResource(icoon);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
