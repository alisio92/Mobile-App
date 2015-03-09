package be.howest.nmct.android.Week2StopAfstand;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import be.howest.nmct.cars.StopAfstandinfo;

public class StopAfstandFragment extends Fragment {

    private EditText uwSnelheidView;
    private EditText uwReactietijdView;
    private TextView uwStopafstandView;
    private RadioGroup droogWegTypeView;
    private Button buttonStopafstand;
    private Context applicationContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stop_afstand, container, false);
        initVariables(v);
        this.buttonStopafstand.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toonStopAfstand(v);
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwReactietijdView = (EditText) v.findViewById(R.id.txtReactie);
        this.uwSnelheidView = (EditText) v.findViewById(R.id.txtSnelheid);
        this.droogWegTypeView = (RadioGroup)v.findViewById(R.id.rdbNatDroog);
        this.uwStopafstandView = (TextView) v.findViewById(R.id.txtStopafstand);
        this.buttonStopafstand = (Button) v.findViewById(R.id.btnBereken);
    }

    public void toonStopAfstand(View v) {
        int snelheid = Integer.parseInt(this.uwSnelheidView.getText().toString());
        float reactie = Float.parseFloat(this.uwReactietijdView.getText().toString());
        int radioButtonId = this.droogWegTypeView.getCheckedRadioButtonId();
        View radioButton = this.droogWegTypeView.findViewById(radioButtonId);
        int wegtype = this.droogWegTypeView.indexOfChild(radioButton);
        StopAfstandinfo info = new StopAfstandinfo(snelheid, reactie, wegtype);
        double getal = Math.round(info.getStopafstand() * 100.0) / 100.0;
        this.uwStopafstandView.setText(getal + " m");
    }

    public Context getApplicationContext() {
        return this.getApplicationContext();
    }
}
