package be.howest.nmct.week4intentfilters;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ConcurrentModificationException;
import java.util.List;


public class MainActivity extends Activity {

    private Button imply;
    private Button onderzoek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imply = (Button) findViewById(R.id.btnImply);
        imply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWithAction(v);
            }
        });

        onderzoek = (Button) findViewById(R.id.btnOnderzoek);
        onderzoek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onderzoek4(v);
            }
        });
    }

    public void onderzoek4(View v){
        Intent intent = new Intent(Constants.ACTION_IMPLY, Uri.parse("xtp:///somedata"));
        startActivity(intent);

        /*Intent intent = new Intent(Constants.ACTION_IMPLY);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(intent);*/
    }

    public void launchWithAction(View v){
        Intent intent = new Intent("android.intent.action.VIEW");//Constants.ACTION_IMPLY
        intent.addCategory(Intent.CATEGORY_CAR_DOCK);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Intent bestaat niet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
