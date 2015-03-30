package be.howest.nmct.week6studentens;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import be.howest.nmct.week6studentens.evaluationstudents.data.loader.Contract;
import be.howest.nmct.week6studentens.evaluationstudents.data.loader.StudentLoader;

public class StudentFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    StudentAdapter studentAdapter;
    String mCurFilter;

    static final String[] mColumnNames = new String[] {
            Contract.StudentColumns.COLUMN_STUDENT_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] to = new int[] { R.id.txtNaamStudent, R.id.txtEmailStudent };
        studentAdapter = new StudentAdapter(getActivity(), R.layout.row_student, null, mColumnNames, to, 0);
        this.setListAdapter(studentAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_student, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
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

            if(cursor.getDouble(colnr) < 8){
                icon.setImageResource(R.drawable.student_red);
            }else if(cursor.getDouble(colnr) < 10){
                icon.setImageResource(R.drawable.student_orange);
            }else{
                icon.setImageResource(R.drawable.student_green);
            }

            return row;
        }
    }
}
