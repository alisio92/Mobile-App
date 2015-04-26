package be.howest.nmct.project2015;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

    private OnSettingsListener mSettingsCallback;
    private EditText uwFromView;
    private EditText uwToView;
    private Button buttonRoutebeschrijving;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        initVariables(v);
        this.buttonRoutebeschrijving.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String from = uwFromView.getText().toString();
                String to = uwToView.getText().toString();
                mSettingsCallback.onSettingsSelected(from, to);
            }
        });
        return v;
    }

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void initVariables(View v) {
        this.uwFromView = (EditText) v.findViewById(R.id.txtFrom);
        this.uwToView = (EditText) v.findViewById(R.id.txtTo);
        this.buttonRoutebeschrijving = (Button) v.findViewById(R.id.btnRoutebeschrijving);
    }

    public interface OnSettingsListener {
        public void onSettingsSelected(String from, String to);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mSettingsCallback = (OnSettingsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
