package be.howest.nmct.Week7StudentenNavigationDrawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;


import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.admin.Student;


public class StudentsActivity extends Activity implements StudentFragment.OnStudentListener, StudentDetailsFragment.OnStudentDetailListener{

    public static final int REQUEST_STUDENT = 1;
    String mCurFilter;

    StudentAdmin instance = StudentAdmin.getInstance();
    List<Student> studenten = instance.getStudenten();

    private DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDiplomagraadTitles;
    private View mFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        /*FragmentManager fragment = getFragmentManager();
        FragmentTransaction transaction = fragment.beginTransaction();
        StudentFragment frag = new StudentFragment();
        transaction.add(R.id.container, frag, "student");//
        transaction.commit();*/


        getFragmentManager().beginTransaction()
                .add(R.id.drawer,DiplomagraadFragment.newInstance(1))
                .add(R.id.container, StudentFragment.newInstance(), "student")
                .commit();

        mTitle = mDrawerTitle = getTitle();
        mDiplomagraadTitles = getResources().getStringArray(R.array.diplomagraad_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mFrag = findViewById(R.id.drawer);
        /*
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.row_diplomagraad, mDiplomagraadTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        */

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                /*R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

       /* if (savedInstanceState == null) {
            selectItem(0);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mFrag);
       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /* The click listner for ListView in the navigation drawer */
   /* private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }*/

   /* private void selectItem(int position) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, DiplomagraadFragment.newInstance(position)).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDiplomagraadTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }*/

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void showStudent(){
        /*
        StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        //fragment.setCurrentRateBitcoinInEuro(value);
        getFragmentManager().popBackStack();
        */
    }

    public void showStudentDetail(String email){
        /*getFragmentManager().beginTransaction()
                .add(R.id.container, StudentDetailsFragment.newInstance(email))
                .commit();*/
        /*
        StudentDetailsFragment fragment = StudentDetailsFragment.newInstance(email);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        */
    }

    public void onStudentSelected(String email) {
       // showStudentDetail(email);
    }

    public void onStudentDetailSelected() {
      //  showStudent();
    }
}
