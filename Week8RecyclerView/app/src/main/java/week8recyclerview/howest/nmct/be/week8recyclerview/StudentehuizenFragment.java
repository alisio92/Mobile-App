package week8recyclerview.howest.nmct.be.week8recyclerview;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import week8recyclerview.howest.nmct.be.week8recyclerview.loader.Contract;
import week8recyclerview.howest.nmct.be.week8recyclerview.loader.StudentenhuizenLoader;

public class StudentehuizenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //private StudentenHuizenAdaptar studentenAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    static final String[] mColumnNames = new String[]{
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_student, container, false);
        View v = inflater.inflate(R.layout.recycleview, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);


        return v;
    }



    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //studentenAdapter.swapCursor(data);
        mAdapter = new StudentenHuizenAdaptar(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new StudentenhuizenLoader(getActivity());
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        //MyAdapter.swapCursor(null);
    }
}

class StudentenHuizenAdaptar extends RecyclerView.Adapter<StudentenHuizenAdaptar.KotViewHolder> {
    private Cursor mDataset;

    public static class KotViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mAddress;
        public TextView mCity;
        public TextView mNumber;
        public TextView mHomeNumber;

        public KotViewHolder(View v) {
            super(v);
            mIcon = (ImageView)v.findViewById(R.id.imgIcon);
            mAddress = (TextView)v.findViewById(R.id.txtAddress);
            mCity = (TextView)v.findViewById(R.id.txtCity);
            mNumber = (TextView)v.findViewById(R.id.txtNumber);
            mHomeNumber = (TextView)v.findViewById(R.id.txtHomeNumber);
        }
    }

    public StudentenHuizenAdaptar(Cursor myDataset) {
        mDataset = myDataset;
    }

    @Override
    public StudentenHuizenAdaptar.KotViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_studentenhuis, parent, false);
        KotViewHolder vh = new KotViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(KotViewHolder holder, int position) {
        String[] names = mDataset.getColumnNames();
        if(mDataset != null){
            mDataset.moveToPosition(position);

            int colAddress = mDataset.getColumnIndex(Contract.KotColumns.COLUMN_ADRES);
            String address = "";
            if(colAddress>= 0) address = mDataset.getString(colAddress);

            int colCity= mDataset.getColumnIndex(Contract.KotColumns.COLUMN_GEMEENTE);
            String city = "";
            if(colCity >= 0) city = mDataset.getString(colCity);

            int colNumber = mDataset.getColumnIndex(Contract.KotColumns.COLUMN_AANTAL_KAMERS);
            String number = "";
            if(colNumber >= 0) number = mDataset.getString(colNumber);

            int colHomeNumber = mDataset.getColumnIndex(Contract.KotColumns.COLUMN_HUISNUMMER);
            String homeNumber = "";
            if(colHomeNumber >= 0) homeNumber = mDataset.getString(colHomeNumber);

            holder.mIcon.setImageResource(R.drawable.house);
            holder.mAddress.setText(address);
            holder.mCity.setText(city);
            holder.mNumber.setText(number);
            holder.mHomeNumber.setText(homeNumber);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.getCount();
    }
}
