package be.howest.nmct.Week7StudentenViewPager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;


import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.List;

import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.Student;


public class StudentsActivity extends ActionBarActivity implements StudentFragment.OnStudentListener, DiplomagraadFragment.OnDiplomagraadListener{

    public static final int REQUEST_STUDENT = 1;
    String mCurFilter;

    StudentAdmin instance = StudentAdmin.getInstance();
    List<Student> studenten = instance.getStudenten();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDiplomagraadTitles;
    private View mFrag;
    private String diplomagraad = "";
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mFrag = findViewById(R.id.drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                /*R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mFrag);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
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

    public void showStudent(String sort){
        /*StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        getFragmentManager().beginTransaction().remove(fragment).commit();
        getFragmentManager().executePendingTransactions();
        Bundle args = new Bundle();
        args.putString(StudentFragment.DIPLOMAGRAAD, sort);
        fragment.setArguments(args);
        getFragmentManager().popBackStack();
        getFragmentManager().executePendingTransactions();*/

         /*StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        Bundle args = new Bundle();
        args.putString(StudentFragment.DIPLOMAGRAAD, sort);
        fragment.setArguments(args);
        getFragmentManager().popBackStack();*/

        /*StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(fragment);
        Bundle args = new Bundle();
        args.putString(StudentFragment.DIPLOMAGRAAD, sort);
        fragment.setArguments(args);
        getFragmentManager().popBackStack();
        ft.attach(fragment);
        ft.commit();*/

        /*StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(fragment);
        fragment.getArguments().putString(StudentFragment.DIPLOMAGRAAD, sort);
        ft.attach(fragment);
        getFragmentManager().popBackStack();*/

        StudentFragment fragment = StudentFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "student");
        Bundle args = new Bundle();
        if(sort!= "") args.putString(StudentFragment.DIPLOMAGRAAD, sort);
        fragment.setArguments(args);
        transaction.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void showStudentDetail(String email){
        /*getFragmentManager().beginTransaction()
                .add(R.id.container, StudentDetailsFragment.newInstance(email))
                .commit();*/

        /*StudentDetailsFragment fragment = StudentDetailsFragment.newInstance(email);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "detail");
        transaction.addToBackStack(null);
        transaction.commit();*/

        Intent myIntent = new Intent(StudentsActivity.this, StudentDetailActivity.class);
        Bundle b = new Bundle();
        b.putString("key", email);
        myIntent.putExtras(b);
        StudentsActivity.this.startActivity(myIntent);
    }

    public void onStudentSelected(String email) {
       showStudentDetail(email);
    }

    public void onDiplomagraadSelected(String diplomagraad) {
        this.diplomagraad = diplomagraad;
        showStudent(diplomagraad);
    }
}
