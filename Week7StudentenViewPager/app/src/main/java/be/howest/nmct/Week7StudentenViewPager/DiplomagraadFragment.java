package be.howest.nmct.Week7StudentenViewPager;


import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.Student;

public class DiplomagraadFragment extends ListFragment {

    private OnDiplomagraadListener mDiplomagraadCallback;
    public static final String ARG_DIPLOMAGRAAD_NUMBER = "diploma_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diplomagraad, container, false);
        setListAdapter(new DiplomagraadAdapter(getActivity()));

        //int i = getArguments().getInt(ARG_DIPLOMAGRAAD_NUMBER);
        /*String[] diplomagraden = getResources().getStringArray(R.array.diplomagraad_array);
        String diplomagraad = diplomagraden[diplomagraden.length -1];*/
        //int imageId = getResources().getIdentifier(diplomagraad.toLowerCase(Locale.getDefault()), "drawable", getActivity().getPackageName());
        //((ImageView) v.findViewById(R.id.image)).setImageResource(imageId);
        //getActivity().setTitle(diplomagraad);

        return v;
    }

    public interface OnDiplomagraadListener {
        public void onDiplomagraadSelected(String diplomagraad);
    }

    public static DiplomagraadFragment newInstance(int idx){
        DiplomagraadFragment fragment = new DiplomagraadFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_DIPLOMAGRAAD_NUMBER, idx);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mDiplomagraadCallback = (OnDiplomagraadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    class ViewHolder{
        public TextView textViewDiplomagraad = null;

        public ViewHolder(View row)
        {
            this.textViewDiplomagraad = (TextView) row.findViewById(R.id.txtDiplomagraad);
        }
    }

    class DiplomagraadAdapter extends ArrayAdapter<Student.DiplomaGraad> {

        public DiplomagraadAdapter(Context context){
            super(context, R.layout.row_diplomagraad,R.id.txtDiplomagraad ,Student.DiplomaGraad.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = super.getView(position, convertView, parent);

            final Student.DiplomaGraad graad = Student.DiplomaGraad.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if(holder == null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewDiplomagraad = holder.textViewDiplomagraad;
            textViewDiplomagraad.setText(graad.getName());

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    onListItemClick(v, graad);
                }
            });
            return row;
        }

        protected void onListItemClick(View v, Student.DiplomaGraad diplomagraad)
        {
            String name = diplomagraad.getName();
            mDiplomagraadCallback.onDiplomagraadSelected(name);
        }
    }
}
