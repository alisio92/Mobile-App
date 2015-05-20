package be.howest.nmct.project2015;


import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import be.howest.nmct.project2015.data.Contract;
import be.howest.nmct.project2015.data.json.DirectionsJSONParser;
import be.howest.nmct.project2015.data.loader.DetailLoader;

public class DetailFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView distanceView;
    private TextView timeView;
    private TextView fromView;
    private TextView toView;
    DetailAdapter detailAdapter;

    static final String[] mColumnNames = new String[]{
            Contract.MapColumns.COLUMN_INNER_DISTANCE,
            Contract.MapColumns.COLUMN_INNER_DURATION,
            Contract.MapColumns.COLUMN_HTML,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        initVariables(v);
        setValues();
        return v;
    }

    public static DetailFragment newInstance(){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void initVariables(View v) {
        this.distanceView = (TextView) v.findViewById(R.id.txtDistance);
        this.timeView = (TextView) v.findViewById(R.id.txtTime);
        this.fromView = (TextView) v.findViewById(R.id.txtDetailFrom);
        this.toView = (TextView) v.findViewById(R.id.txtDetailTo);
    }

    public void setValues() {
        distanceView.setText(DirectionsJSONParser.getDistance());
        timeView.setText(DirectionsJSONParser.getDuration());
        fromView.setText(DirectionsJSONParser.getFrom());
        toView.setText(DirectionsJSONParser.getTo());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] to = new int[]{R.id.txtRowDistance, R.id.txtRowDuration, R.id.txtHTML};
        detailAdapter = new DetailAdapter(getActivity(), R.layout.row_detail, null, mColumnNames, to, 0);
        this.setListAdapter(detailAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        detailAdapter.swapCursor(data);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DetailLoader loader = new DetailLoader(getActivity());
        // loader.loadInBackground();
        return loader;
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        detailAdapter.swapCursor(null);
    }

    class DetailAdapter extends SimpleCursorAdapter {

        private int layout;

        public DetailAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent, false);
            return row;
        }
    }
}
