package be.howest.nmct.Week7StudentenGridView.evaluationstudents.data.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.Objects;

import be.howest.nmct.Week7StudentenGridView.evaluationstudents.data.StudentAdmin;
import be.howest.nmct.Week7StudentenGridView.evaluationstudents.data.admin.Student;

/**
 * Created by alisio on 30/03/2015.
 */
public class StudentLoader extends AsyncTaskLoader<Cursor>{
    private Cursor mCursor;

    private final String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.StudentColumns.COLUMN_STUDENT_NAAM,
            Contract.StudentColumns.COLUMN_STUDENT_VOORNAAM,
            Contract.StudentColumns.COLUMN_STUDENT_EMAIL,
            Contract.StudentColumns.COLUMN_STUDENT_TOTAAL};

    private static Object lock = new Object();

    public StudentLoader(Context context){
        super(context);

    }

    @Override
    protected  void onStartLoading(){
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null){
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground(){
        if(mCursor == null){
            loadCursor();
        }
        return mCursor;
    }

    private void loadCursor(){
        synchronized (lock){
            if(mCursor != null) return;

            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;
            for(Student student : StudentAdmin.getInstance().getStudenten()){
                MatrixCursor.RowBuilder row = cursor.newRow();
                row.add(id);
                row.add(student.getNaamStudent());
                row.add(student.getVoornaamStudent());
                row.add(student.getEmailStudent());
                row.add(Math.round(student.getTotaleScoreStudent()));
                id++;
            }
            mCursor = cursor;
        }
    }
}
