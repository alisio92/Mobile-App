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
import be.howest.nmct.project2015.data.helper.Helper;
import be.howest.nmct.project2015.data.json.DirectionsJSONParser;
import be.howest.nmct.project2015.data.loader.DetailLoader;

public class DetailFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView distanceView;
    private TextView timeView;
    private TextView fromView;
    private TextView toView;
    DetailAdapter detailAdapter;

    private String distance;
    private String duration;
    private String from;
    private String destination;

    public static final String DISTANCE = "be.howest.nmct.NEW_DISTANCE";;
    public static final String DURATION = "be.howest.nmct.NEW_DURATION";;
    public static final String FROM = "be.howest.nmct.NEW_FROM";;
    public static final String TO = "be.howest.nmct.NEW_TO";;

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

        distance = getArguments().getString(DISTANCE);
        duration = getArguments().getString(DURATION);
        from = getArguments().getString(FROM);
        destination = getArguments().getString(TO);
        setValues();

        return v;
    }

    public static DetailFragment newInstance(String distance, String duration, String from, String to){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(DetailFragment.DISTANCE, distance);
        args.putString(DetailFragment.DURATION, duration);
        args.putString(DetailFragment.FROM, from);
        args.putString(DetailFragment.TO, to);
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
        distanceView.setText(distance);
        timeView.setText(duration);
        fromView.setText(from);
        toView.setText(destination);
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
