package be.howest.nmct.project2015;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;

import be.howest.nmct.project2015.data.helper.Helper;
import be.howest.nmct.project2015.data.json.DirectionsJSONParser;

public class SettingsFragment extends Fragment {

    private OnSettingsListener mSettingsCallback;
    private EditText uwFromView;
    private EditText uwToView;
    private ImageView uwIconFromView;
    private ImageView uwIconToView;
    private RadioGroup uwMode;
    private CheckBox uwToll;
    private CheckBox uwSnelwegen;
    private CheckBox uwBoten;
    private Button buttonRoutebeschrijving;
    private Button buttonDetail;
    private LatLng latLngFrom;
    private LatLng latLngTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            if(savedInstanceState!=null) {
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        initVariables(v);
        listenerVariables(v);
        enableDisableControls();
        uwIconFromView.setImageResource(R.drawable.blue);
        uwIconToView.setImageResource(R.drawable.blue);
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
        this.uwIconFromView = (ImageView) v.findViewById(R.id.imgIconFrom);
        this.uwIconToView = (ImageView) v.findViewById(R.id.imgIconTo);
        this.uwMode = (RadioGroup) v.findViewById(R.id.rdbGroup);
        this.uwToll = (CheckBox) v.findViewById(R.id.chbToll);
        this.uwSnelwegen = (CheckBox) v.findViewById(R.id.chbSnelwegen);
        this.uwBoten = (CheckBox) v.findViewById(R.id.chbBoten);
        this.buttonRoutebeschrijving = (Button) v.findViewById(R.id.btnRoutebeschrijving);
        this.buttonDetail = (Button) v.findViewById(R.id.btnDetail);
    }

    public void listenerVariables(View v){
        this.buttonRoutebeschrijving.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //driving walking bicycling transit
                //avoid=tolls avoid=highways avoid=ferries
                String from = uwFromView.getText().toString();
                String to = uwToView.getText().toString();

                int buttonId = uwMode.getCheckedRadioButtonId();
                View radioButton = uwMode.findViewById(buttonId);
                int id = uwMode.indexOfChild(radioButton);
                String mode = "";
                if(id == 0) mode = "driving";
                if(id == 1) mode = "transit";
                if(id == 2) mode = "bicycling";
                if(id == 3) mode = "walking";

                String avoid = "";
                if(uwToll.isChecked()) avoid += "&avoid=tolls";
                if(uwSnelwegen.isChecked()) avoid += "&avoid=highways";
                if(uwBoten.isChecked()) avoid += "&avoid=ferries";

                mSettingsCallback.onSettingsSelected(from, to, mode, avoid);
            }
        });
        this.buttonDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mSettingsCallback.onSettingsSelected(null, null, null, null);
            }
        });
        this.uwMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableDisableControls();
            }
        });
        this.uwFromView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                String from = uwFromView.getText().toString();
                if(from.length() > 2) {
                    latLngFrom = Helper.getLocationFromAddress(from, getActivity());
                    if(latLngFrom == null) uwIconFromView.setImageResource(R.drawable.blue);
                    else uwIconFromView.setImageResource(R.drawable.green);
                }else{
                    uwIconFromView.setImageResource(R.drawable.blue);
                }
                enableDisableControls();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        this.uwToView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                String to = uwToView.getText().toString();
                if (to.length() > 2) {
                    latLngTo = Helper.getLocationFromAddress(to, getActivity());
                    if (latLngTo == null) uwIconToView.setImageResource(R.drawable.blue);
                    else uwIconToView.setImageResource(R.drawable.green);
                } else {
                    uwIconToView.setImageResource(R.drawable.blue);
                }
                enableDisableControls();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void enableDisableControls(){
        if(latLngTo != null && latLngFrom != null){
            int buttonId = uwMode.getCheckedRadioButtonId();
            View radioButton = uwMode.findViewById(buttonId);
            int id = uwMode.indexOfChild(radioButton);
            if(latLngTo.toString().length() > 2 && latLngFrom.toString().length() > 2 && id >= 0) buttonRoutebeschrijving.setEnabled(true);
            else buttonRoutebeschrijving.setEnabled(false);
        }else{
            buttonRoutebeschrijving.setEnabled(false);
        }

        if(DirectionsJSONParser.getPolylines()!= null) buttonDetail.setEnabled(true);
        else buttonDetail.setEnabled(false);
    }

    public interface OnSettingsListener {
        public void onSettingsSelected(String from, String to, String mode, String avoid);
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
