package be.howest.nmct.Week7StudentenViewPager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.Student;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.loader.Contract;

public class StudentDetailActivity extends ActionBarActivity implements StudentDetailsFragment.OnStudentDetailListener{

    StudentDetailFragmentAdaptar mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        mDemoCollectionPagerAdapter =
                new StudentDetailFragmentAdaptar(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        Bundle b = getIntent().getExtras();
        String email = b.getString("key");
        List<Student> students = StudentAdmin.getInstance().getStudenten();
        for(int i = 0; i < StudentAdmin.getInstance().getStudenten().size();i++){
            if(students.get(i).getEmailStudent().equals(email)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_detail, menu);
        return true;
    }

    public void onStudentDetailSelected() {
        finish();
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

    class StudentDetailFragmentAdaptar extends FragmentStatePagerAdapter {
        public StudentDetailFragmentAdaptar(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle b = getIntent().getExtras();
            //String email = b.getString("key");
            String email = StudentAdmin.getInstance().getStudenten().get(i).getEmailStudent();
            Fragment fragment = StudentDetailsFragment.newInstance(email);
            return fragment;
        }

        @Override
        public int getCount() {
            return StudentAdmin.getInstance().getStudenten().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
}
