package be.howest.nmct.week4launching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExplicitActivity extends Activity {

    public static final String EXTRA_INFO = "be.howest.nmct.anderoid.week4launching.EXTRA_INFO";
    private TextView text;
    private Button ok;
    private Button cancel;
    private Button result;
    public static final int USER_RESULT_CODE = RESULT_FIRST_USER+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);
        String value = getIntent().getStringExtra(ExplicitActivity.EXTRA_INFO);
        this.text = (TextView) findViewById(R.id.txtExtra);
        this.text.setText(value);

        ok = (Button) findViewById(R.id.btnOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        result = (Button) findViewById(R.id.btnResult);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(USER_RESULT_CODE);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_INFO_BACK_LASTNAME, "last");
                intent.putExtra(MainActivity.EXTRA_INFO_BACK_AGE, 40);
                setResult(ExplicitActivity.USER_RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_explicit, menu);
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
