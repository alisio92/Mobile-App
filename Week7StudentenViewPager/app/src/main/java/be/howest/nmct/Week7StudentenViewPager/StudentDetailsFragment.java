package be.howest.nmct.Week7StudentenViewPager;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Collection;

import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.Student;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.loader.ModulePuntAdapter;

public class StudentDetailsFragment extends Fragment {

    private OnStudentDetailListener mStudentDetailCallback;
    Student student;
    GridView grdPunten;
    TextView uwNameView;
    TextView uwPuntenView;
    TextView uwGraadView;
    Button buttonVorig;

    public static final String EMAIL = "be.howest.nmct.Email";
    public static final String SORT = "be.howest.nmct.Sort";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_details, container, false);
        initVariables(v);
        this.buttonVorig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String sort = getArguments().getString(SORT);
                mStudentDetailCallback.onStudentDetailSelected();
            }
        });

        showDetail(v);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            String email = getArguments().getString(EMAIL);
            student = StudentAdmin.getInstance().getStudent(email);
        }
    }

    public static StudentDetailsFragment newInstance(String email){
        StudentDetailsFragment fragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        args.putString(StudentDetailsFragment.EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mStudentDetailCallback = (OnStudentDetailListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public interface OnStudentDetailListener {
        public void onStudentDetailSelected();
    }

    public void initVariables(View v){
        grdPunten = (GridView) v.findViewById(R.id.grdPunten);
        uwNameView = (TextView) v.findViewById(R.id.txbName);
        uwPuntenView = (TextView) v.findViewById(R.id.txbScore);
        uwGraadView = (TextView) v.findViewById(R.id.txbDiplomagraad);
        this.buttonVorig = (Button) v.findViewById(R.id.btnDetailVorige);
    }

    public void showDetail(View v){
        grdPunten.setAdapter(new ModulePuntAdapter(getActivity(), student));
        uwNameView.setText(student.getVoornaamStudent() + " " + student.getNaamStudent());
        uwPuntenView.setText(String.valueOf(student.getTotaleScoreStudent()));
        uwGraadView.setText(String.valueOf(Student.DiplomaGraad.getDiplomaGraad(student.getTotaleScoreStudent())));
    }
}
