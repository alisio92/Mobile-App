package be.howest.nmct.week4launching;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button buttonStartActivity2;
    private Button buttonDialog;
    private EditText editScore;
    public static final int REQUEST_CODE_EXPLICIT = 1;
    public static final String EXTRA_INFO_BACK_LASTNAME = "be.howest.nmct.anderoid.week4launching.EXTRA_INFO_BACK_LASTNAME";
    public static final String EXTRA_INFO_BACK_AGE = "be.howest.nmct.anderoid.week4launching.EXTRA_INFO_BACK_AGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartActivity2 = (Button) findViewById(R.id.btnActivity2);
        buttonStartActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSecondActivity(v);
            }
        });

        buttonDialog = (Button) findViewById(R.id.btnDialog);
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogWindow(v);
            }
        });
    }

    public void showDialogWindow(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int score = Integer.parseInt(editScore.getText().toString());
                        Toast.makeText(MainActivity.this, "Jouw score: " + score, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
        .setView(R.layout.alert);
        builder.create();
        AlertDialog dialog = builder.show();
        editScore = (EditText) dialog.findViewById(R.id.txtScore);
    }

    public void startSecondActivity(View v){
        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
        intent.putExtra(ExplicitActivity.EXTRA_INFO, "2NMCT");
        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_EXPLICIT);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case REQUEST_CODE_EXPLICIT:
            switch (resultCode) {
                case RESULT_CANCELED:
                    Toast.makeText(MainActivity.this, "User selected Cancel", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_OK:
                    Toast.makeText(MainActivity.this, "User selected Ok", Toast.LENGTH_SHORT).show();
                    break;
                case ExplicitActivity.USER_RESULT_CODE:
                    //Toast.makeText(MainActivity.this, "User selected Result", Toast.LENGTH_SHORT).show();
                    String value = data.getStringExtra(MainActivity.EXTRA_INFO_BACK_LASTNAME);
                    int age = data.getIntExtra(MainActivity.EXTRA_INFO_BACK_AGE, 0);
                    Toast.makeText(MainActivity.this, "No Idea what's happening there, " + value + " - " + age, Toast.LENGTH_SHORT).show();
                    break;
            }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
