package week9studentenhuizen.howest.nmct.be.week9studentenhuizen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import week9studentenhuizen.howest.nmct.be.week9studentenhuizen.loader.Contract;
import week9studentenhuizen.howest.nmct.be.week9studentenhuizen.loader.StudentenhuizenLoader;

public class StudentehuizenFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentAdapter studentenAdapter;
    private Cursor mCursor;
    private String street;
    private SearchView searchView;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        return v;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        studentenAdapter.swapCursor(cursor);
        this.mCursor = cursor;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        studentenAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Cursor cursor = this.studentenAdapter.getCursor();
        int col1 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_ADRES);
        int col2 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_HUISNUMMER);
        int col3 = cursor.getColumnIndex(Contract.KotColumns.COLUMN_GEMEENTE);

        cursor.moveToPosition(position);
        String adres = cursor.getString(col1);
        int huisnummer = cursor.getInt(col2);
        String gemeente = cursor.getString(col3);
        String tempGeo = "geo:0,0?q=" + adres + "+" + huisnummer + "+" + gemeente;
        Uri geo = Uri.parse(tempGeo);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geo);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        MenuItem searchItem = menu.findItem(R.id.my_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        final int resource_edit_text = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        ((EditText) searchView.findViewById(resource_edit_text)).addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String currentString = s.toString();
                if(currentString.isEmpty()) studentenAdapter.swapCursor(mCursor);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String currentString = s.toString();
                if(currentString.isEmpty()) studentenAdapter.swapCursor(mCursor);
            }
        });
    }

    private void setupSearchView(MenuItem searchItem)
    {
        searchView.setIconifiedByDefault(false);
        //searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                street = s;
                studentenAdapter.swapCursor(filterCursorOnStreet());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                street = s;
                studentenAdapter.swapCursor(filterCursorOnStreet());
                return false;
            }
        });
    }

    private Cursor filterCursorOnStreet()
    {
        String[] tempColumnNames = new String[]
                {
                        BaseColumns._ID,
                        Contract.KotColumns.COLUMN_ADRES,
                        Contract.KotColumns.COLUMN_HUISNUMMER,
                        Contract.KotColumns.COLUMN_GEMEENTE,
                        Contract.KotColumns.COLUMN_AANTAL_KAMERS,
                };

        MatrixCursor newCursor = new MatrixCursor(tempColumnNames);
        int colnr1 = mCursor.getColumnIndex(Contract.KotColumns.COLUMN_ADRES);
        int colnr2 = mCursor.getColumnIndex(Contract.KotColumns.COLUMN_HUISNUMMER);
        int colnr3 = mCursor.getColumnIndex(Contract.KotColumns.COLUMN_GEMEENTE);
        int colnr4 = mCursor.getColumnIndex(Contract.KotColumns.COLUMN_AANTAL_KAMERS);

        int id = 1;
        if(mCursor.moveToFirst())
        {
            do
            {
                if(mCursor.getString(colnr1).toLowerCase().contains(street.toLowerCase().trim()))
                {
                    MatrixCursor.RowBuilder row = newCursor.newRow();
                    row.add(id++);
                    row.add(mCursor.getString(colnr1));
                    row.add(mCursor.getString(colnr2));
                    row.add(mCursor.getString(colnr3));
                    row.add(mCursor.getString(colnr4));
                }
            } while(mCursor.moveToNext());
        }

        return newCursor;
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
