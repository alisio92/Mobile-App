package be.howest.nmct.Week7StudentenNavigationDrawer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.HashMap;

import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.admin.Student;
import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.loader.Contract;
import be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.loader.StudentLoader;

public class StudentFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    StudentAdapter studentAdapter;
    String mCurFilter;
    private OnStudentListener mStudentCallback;
    public static final String DIPLOMAGRAAD = "be.howest.nmct.NEW_DIPLOMAGRAAD";

    static final String[] mColumnNames = new String[] {
            Contract.StudentColumns.COLUMN_STUDENT_VOLLEDIGE_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_TOTAAL,
    };

    public StudentFragment() {
        super();
        // Just to be an empty Bundle. You can use this later with getArguments().set...
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student, container, false);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] to = new int[] { R.id.txtNaamStudent, R.id.txtEmailStudent, R.id.txtScore };
        studentAdapter = new StudentAdapter(getActivity(), R.layout.row_student, null, mColumnNames, to, 0);
        this.setListAdapter(studentAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        studentAdapter.swapCursor(data);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StudentLoader loader = new StudentLoader(getActivity());
       // loader.loadInBackground();
        return loader;
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        studentAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        /*Fragment fragment = null;
        FragmentManager frgManager;
        FragmentTransaction ft;

        fragment = new StudentDetailsFragment();
        frgManager = fragment.getFragmentManager();
        ft = frgManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();*/

        /*StudentDetailsFragment newFragment = new StudentDetailsFragment();
        Bundle args = new Bundle();
        //args.putFloat(BitcoinRateFragment.BITCOIN_RATE, value);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
        //Student student = (Student)l.getChildAt(position);
        /*getFragmentManager().beginTransaction()
                  .add(R.id.container, StudentDetailsFragment.newInstance(), "change")
                  .commit();*/

        MatrixCursor obj = (MatrixCursor)l.getAdapter().getItem(position);
        String[] colums = obj.getColumnNames();
        Integer indexEmail = -1;
        for(int i = 0; i< colums.length;i++){
            if(colums[i].equals(Contract.StudentColumns.COLUMN_STUDENT_EMAIL)) indexEmail = i;
        }
        String email = obj.getString(indexEmail);
        mStudentCallback.onStudentSelected(email);
    }

    public interface OnStudentListener {
        public void onStudentSelected(String email);
    }

    public static StudentFragment newInstance(){
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        //args.putString(DIPLOMAGRAAD, graad);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mStudentCallback = (OnStudentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    class StudentAdapter extends SimpleCursorAdapter {

        private int layout;

        public StudentAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags){
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent, false);
            ImageView icon = (ImageView)row.findViewById(R.id.imgIcon);

            int colnr = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_TOTAAL);
            int colEmail = cursor.getColumnIndex(Contract.StudentColumns.COLUMN_STUDENT_EMAIL);
            String email = cursor.getString(colEmail);
            Student student = StudentAdmin.getInstance().getStudent(email);
            String graadStudent = String.valueOf(Student.DiplomaGraad.getDiplomaGraad(student.getTotaleScoreStudent()));

            if(cursor.getDouble(colnr) < 8.0){
                icon.setImageResource(R.drawable.student_red);
            }else if(cursor.getDouble(colnr) < 10.0){
                icon.setImageResource(R.drawable.student_orange);
            }else{
                icon.setImageResource(R.drawable.student_green);
            }
            String diplomagraad = "";
            if(getArguments().getString(DIPLOMAGRAAD)!= null) diplomagraad = getArguments().getString(DIPLOMAGRAAD);
            if(diplomagraad.equals("") || diplomagraad.equals(graadStudent)) return row;
            else return new View(context);
        }
    }
}
