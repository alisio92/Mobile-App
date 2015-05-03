package be.howest.nmct.week9politiekortrijk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import be.howest.nmct.week9politiekortrijk.loader.Contract;
import be.howest.nmct.week9politiekortrijk.loader.SnelheidscontrolesLoader;
import be.howest.nmct.week9politiekortrijk.util.Test;

public class PolitieControleFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private PolitieControleAdapter politieControleAdapter;
    private Cursor mCursor;
    private String maanden;
    private SearchView searchView;

    static final String[] mColumnNames = new String[] {
            Contract.MetingColumns.COLUMN_ADRES,
            Contract.MetingColumns.COLUMN_GEMEENTE,
            Contract.MetingColumns.COLUMN_MAAND,
            Contract.MetingColumns.COLUMN_AANTAL_CONTROLES,
            Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN,
            Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN,
            Contract.MetingColumns.COLUMN_X,
            Contract.MetingColumns.COLUMN_Y,
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

        int[] to = new int[] { R.id.txtAdres, R.id.txtGemeente, R.id.txtMaand, R.id.txtControles };
        politieControleAdapter = new PolitieControleAdapter(getActivity(), R.layout.row_controle, null, mColumnNames, to, 0);
        this.setListAdapter(politieControleAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.activity_main, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Cursor cursor = this.politieControleAdapter.getCursor();
        int col1 = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_ADRES);
        int col2 = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_GEMEENTE);
        int col3 = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_MAAND);
        int col4 = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_X);
        int col5 = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_Y);

        cursor.moveToPosition(position);
        String adres = cursor.getString(col1);
        String gemeente = cursor.getString(col2);
        String maand = cursor.getString(col3);
        double x = cursor.getDouble(col4);
        double y = cursor.getDouble(col5);
        LatLng lat = Test.lambert72toWGS84(x, y);

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=" + adres + "+" + gemeente + "+" + maand, lat.latitude, lat.longitude, adres, gemeente, maand);
        Uri geo = Uri.parse(uri);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geo);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null)
        {
            startActivity(intent);
        }
        /*String labelLocation = "LOOOOOOL";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + lat.latitude  + ">,<" + lat.longitude + ">?q=<" + lat.latitude  + ">,<" + lat.longitude + ">(" + labelLocation + ")"));
        startActivity(intent);*/

        /*String label = "Cinnamon & Toast";
        String uriBegin = "geo:" + lat.latitude + "," + lat.longitude;
        String query =  adres + "+" + gemeente + "+" + maand + "+" + lat.latitude + "," + lat.longitude + "(" + label + ")";
        String encodedQuery = Uri.encode( query  );
        String uriString = uriBegin + "?q=" + encodedQuery;
        Uri uri = Uri.parse( uriString );
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
        startActivity( intent );*/
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        politieControleAdapter.swapCursor(cursor);
        this.mCursor = cursor;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new SnelheidscontrolesLoader(getActivity());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        politieControleAdapter.swapCursor(null);
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
                if(currentString.isEmpty()) politieControleAdapter.swapCursor(mCursor);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String currentString = s.toString();
                if(currentString.isEmpty()) politieControleAdapter.swapCursor(mCursor);
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
                maanden = s;
                politieControleAdapter.swapCursor(filterCursorMaand());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                maanden = s;
                politieControleAdapter.swapCursor(filterCursorMaand());
                return false;
            }
        });
    }

    private Cursor filterCursorMaand()
    {
        String[] tempColumnNames = new String[]
                {
                        BaseColumns._ID,
                        Contract.MetingColumns.COLUMN_ADRES,
                        Contract.MetingColumns.COLUMN_GEMEENTE,
                        Contract.MetingColumns.COLUMN_MAAND,
                        Contract.MetingColumns.COLUMN_AANTAL_CONTROLES,
                        Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN,
                        Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN,
                        Contract.MetingColumns.COLUMN_X,
                        Contract.MetingColumns.COLUMN_Y,
                };

        MatrixCursor newCursor = new MatrixCursor(tempColumnNames);
        int colnr1 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_ADRES);
        int colnr2 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_GEMEENTE);
        int colnr3 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_MAAND);
        int colnr4 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_AANTAL_CONTROLES);
        int colnr5 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN);
        int colnr6 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN);
        int colnr7 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_X);
        int colnr8 = mCursor.getColumnIndex(Contract.MetingColumns.COLUMN_Y);

        int id = 1;
        if(mCursor.moveToFirst())
        {
            do
            {
                if(mCursor.getString(colnr3).toLowerCase().contains(maanden.toLowerCase().trim()))
                {
                    MatrixCursor.RowBuilder row = newCursor.newRow();
                    row.add(id++);
                    row.add(mCursor.getString(colnr1));
                    row.add(mCursor.getString(colnr2));
                    row.add(mCursor.getString(colnr3));
                    row.add(mCursor.getString(colnr4));
                    row.add(mCursor.getString(colnr5));
                    row.add(mCursor.getString(colnr6));
                    row.add(mCursor.getString(colnr7));
                    row.add(mCursor.getString(colnr8));
                }
            } while(mCursor.moveToNext());
        }

        return newCursor;
    }

    class PolitieControleAdapter extends SimpleCursorAdapter {

        private int layout;

        public PolitieControleAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags){
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            final LayoutInflater inflater = LayoutInflater.from(context);

            View row = inflater.inflate(layout, parent, false);
            RelativeLayout layout = (RelativeLayout)row.findViewById(R.id.row);

            int indexVoertuigen = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_AANTAL_VOERTUIGEN);
            int indexOvertredingen = cursor.getColumnIndex(Contract.MetingColumns.COLUMN_AANTAL_OVERTREDINGEN);
            int aantalVoertuigen = cursor.getInt(indexVoertuigen);
            int aantalOvertredingen = cursor.getInt(indexOvertredingen);

            double graad = ((double)aantalOvertredingen / (double)aantalVoertuigen);

            if(graad > 0.3){
                layout.setBackgroundColor(getResources().getColor(R.color.rood));
            }else if(graad > 0.2 && graad < 0.3){
                layout.setBackgroundColor(getResources().getColor(R.color.oranje));
            }else{
                layout.setBackgroundColor(getResources().getColor(R.color.groen));
            }
            return row;
        }
    }
}
