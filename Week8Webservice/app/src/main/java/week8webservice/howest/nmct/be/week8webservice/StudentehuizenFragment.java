package week8webservice.howest.nmct.be.week8webservice;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import week8webservice.howest.nmct.be.week8webservice.loader.Contract;
import week8webservice.howest.nmct.be.week8webservice.loader.StudentenhuizenLoader;


public class StudentehuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentAdapter studentenAdapter;

    static final String[] mColumnNames = new String[] {
            Contract.KotColumns.COLUMN_ADRES,
            Contract.KotColumns.COLUMN_HUISNUMMER,
            Contract.KotColumns.COLUMN_GEMEENTE,
            Contract.KotColumns.COLUMN_AANTAL_KAMERS
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] to = new int[] { R.id.txtAddress, R.id.txtHomeNumber, R.id.txtCity, R.id.txtNumber };
        studentenAdapter = new StudentAdapter(getActivity(), R.layout.row_studentenhuis, null, mColumnNames, to, 0);
        this.setListAdapter(studentenAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_student, container, false);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        return v;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        studentenAdapter.swapCursor(data);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        studentenAdapter.swapCursor(null);
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
            return row;
        }
    }
}
