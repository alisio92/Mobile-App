package be.howest.nmct.Week7StudentenGridView;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.howest.nmct.Week7StudentenGridView.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenGridView.evaluationstudents.data.admin.Student;

public class StudentsActivity extends Activity implements StudentFragment.OnStudentListener, StudentDetailsFragment.OnStudentDetailListener{

    public static final int REQUEST_STUDENT = 1;
    String mCurFilter;

    StudentAdmin instance = StudentAdmin.getInstance();
    List<Student> studenten = instance.getStudenten();

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
            .add(R.id.container, StudentFragment.newInstance(), "student")
            .commit();
    }

    public void showStudent(){
        StudentFragment fragment = (StudentFragment) getFragmentManager().findFragmentByTag("student");
        //fragment.setCurrentRateBitcoinInEuro(value);
        getFragmentManager().popBackStack();
    }

    public void showStudentDetail(String email){
        /*getFragmentManager().beginTransaction()
                .add(R.id.container, StudentDetailsFragment.newInstance(email))
                .commit();*/
        StudentDetailsFragment fragment = StudentDetailsFragment.newInstance(email);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onStudentSelected(String email) {
        showStudentDetail(email);
    }

    public void onStudentDetailSelected() {
        showStudent();
    }
}
