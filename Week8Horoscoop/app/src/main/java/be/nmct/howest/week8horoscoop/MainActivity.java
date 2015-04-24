package be.nmct.howest.week8horoscoop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import be.nmct.howest.week8horoscoop.data.Data;

public class MainActivity extends ActionBarActivity {
    public static final String EXTRA_BIRTHYEAR = "be.howest.nmct.week6horoscoop.BIRTHYEAR";
    public static final String EXTRA_HOROSCOOP = "be.howest.nmct.week6horoscoop.HOROSCOOP";
    public static final int REQUEST_BIRTHDAY = 1;
    public static final int REQUEST_HOROSCOOP = 2;

    Button btnGeboortejaar, btnHoroscoop;
    EditText vnaamView;
    EditText naamView;
    TextView jaarView;
    ImageView icon;
    private ShareActionProvider mShareActionProvider;

    private String geboortejaar;
    private int horoscopeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();

        btnGeboortejaar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selecteerGeboortejaar(v);
            }
        });

        btnHoroscoop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selecteerHoroscoop(v);
            }
        });
    }

    public void initVariables(){
        btnGeboortejaar = (Button) findViewById(R.id.btngeboorte);
        btnHoroscoop = (Button) findViewById(R.id.btnHoroscoop);

        this.vnaamView = (EditText) findViewById(R.id.txtVName);
        this.naamView = (EditText) findViewById(R.id.txtName);
        this.jaarView = (TextView) findViewById(R.id.txtJaar);
        this.icon = (ImageView) findViewById(R.id.imgIcon);
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
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_BIRTHDAY:
                geboortejaar = data.getStringExtra(EXTRA_BIRTHYEAR);
                jaarView.setText("Geboortejaar: " + geboortejaar);
                if(mShareActionProvider!= null) mShareActionProvider.setShareIntent(getShareIntent());
                break;
            case REQUEST_HOROSCOOP:
                icon = (ImageView) findViewById(R.id.imgHoroscoop);
                horoscopeImage = data.getIntExtra(EXTRA_HOROSCOOP, 0);
                icon.setImageResource(horoscopeImage);
                if(mShareActionProvider!= null) mShareActionProvider.setShareIntent(getShareIntent());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        //mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(mShareActionProvider!= null) mShareActionProvider.setShareIntent(getShareIntent());
        else Toast.makeText(MainActivity.this, "provider = null", Toast.LENGTH_SHORT).show();
        return true;
    }

    private Intent getShareIntent()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getTextInfo());
        return intent;
    }

    private String getTextInfo()
    {
        Data.Horoscoop horoscope = Data.Horoscoop.getHoroscopeByDrawable(this.horoscopeImage);
        if(geboortejaar != null && horoscope != null)
        {
            String text = "Geboortejaar: " + geboortejaar;
            text += "\n Horoscoop: " + horoscope.getNaamHoroscoop();
            return text;
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
