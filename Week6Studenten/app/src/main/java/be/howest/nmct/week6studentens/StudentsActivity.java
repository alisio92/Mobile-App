package be.howest.nmct.week6studentens;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.util.List;

import be.howest.nmct.week6studentens.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.week6studentens.evaluationstudents.data.admin.Student;

public class StudentsActivity extends Activity {

    public static final int REQUEST_STUDENT = 1;
    String mCurFilter;

    StudentAdmin instance = StudentAdmin.getInstance();
    List<Student> studenten = instance.getStudenten();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        FragmentManager fragment = getFragmentManager();
        FragmentTransaction transaction = fragment.beginTransaction();
        StudentFragment frag = new StudentFragment();
        transaction.add(R.id.container, frag, "mainfrag");//
        transaction.commit();
    }
}
