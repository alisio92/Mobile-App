package be.howest.nmct.Week7StudentenNavigationDrawer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {//implements StudentFragment.OnStudentListener, StudentDetailsFragment.OnStudentDetailListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        /*getFragmentManager().beginTransaction()
                .add(R.id.container, new StudentFragment())
                .commit();*/
        /*Intent intent = new Intent(MainActivity.this, StudentsActivity.class);
        startActivity(intent);*/

        if (savedInstanceState == null) {
            /*getFragmentManager().beginTransaction()
                    .add(R.id.container, StudentFragment.newInstance(), "student")
                    .commit();*/
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showStudent(){
        StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        //fragment.setCurrentRateBitcoinInEuro(value);
        getFragmentManager().popBackStack();
    }

    public void showStudentDetail(String email){
        getFragmentManager().beginTransaction()
                .add(R.id.container, StudentDetailsFragment.newInstance(email))
                .commit();
    }

    public void onStudentSelected(String email) {
        showStudentDetail(email);
    }

    public void onStudentDetailSelected() {
        showStudent();
    }*/
}
