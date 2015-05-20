package be.howest.nmct.project2015;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import be.howest.nmct.project2015.data.helper.MapOptie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapOptionsFragment extends ListFragment {

    private OnMapOptionsListener mMapOptionsdCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map_options, container, false);
        setListAdapter(new MapOptionsAdapter(getActivity()));
        return v;
    }

    public static MapOptionsFragment newInstance(){
        MapOptionsFragment fragment = new MapOptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnMapOptionsListener {
        public void onMapOptionsSelected(String optie);
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mMapOptionsdCallback = (OnMapOptionsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    class ViewHolder{
        public TextView textViewMapOptie = null;

        public ViewHolder(View row)
        {
            this.textViewMapOptie = (TextView) row.findViewById(R.id.txtmapoptie);
        }
    }

    class MapOptionsAdapter extends ArrayAdapter<MapOptie.Optie> {

        public MapOptionsAdapter(Context context){
            super(context, R.layout.row_mapoptie,R.id.txtmapoptie ,MapOptie.Optie.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = super.getView(position, convertView, parent);

            final MapOptie.Optie optie = MapOptie.Optie.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if(holder == null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewMapOptie = holder.textViewMapOptie;
            textViewMapOptie.setText(optie.getName());

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    onListItemClick(v, optie);
                }
            });
            return row;
        }

        protected void onListItemClick(View v, MapOptie.Optie optie)
        {
            String name = optie.getName();
            mMapOptionsdCallback.onMapOptionsSelected(name);
        }
    }
}
