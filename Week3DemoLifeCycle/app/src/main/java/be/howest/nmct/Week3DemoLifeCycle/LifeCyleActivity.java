package be.howest.nmct.Week3DemoLifeCycle;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class LifeCyleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("log: " + getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cyle);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LifeCyleFragment())
                    .commit();
        }
    }
    @Override
    protected void onPause() {
        Log.d("log: " + getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("log: " + getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d("log: " + getClass().getSimpleName(), "onDestroy");
        super.onDestroy();  // Always call the superclass
        android.os.Debug.stopMethodTracing();
    }

    @Override
    protected void onStop() {
        Log.d("log: " + getClass().getSimpleName(), "onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("log: " + getClass().getSimpleName(), "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d("log: " + getClass().getSimpleName(), "onRestart");
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_life_cyle, menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("log: " + getClass().getSimpleName(), "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_life_cyle, container, false);
            return rootView;
        }
    }
}
