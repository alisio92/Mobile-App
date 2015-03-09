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

public class StopAfstandDesign2Fragment extends Fragment {

    private SeekBar uwSnelheidView;
    private SeekBar uwReactietijdView;
    private TextView uwStopafstandView;
    private TextView uwSnelheidTextView;
    private TextView uwReactieTextView;
    private RadioGroup droogWegTypeView;
    private Button buttonStopafstand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_stop_afstand_design2, container, false);
        initVariables(v);
        uwSnelheidTextView.setText(uwSnelheidView.getProgress() + " km/u");
        this.uwSnelheidView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                uwSnelheidTextView.setText(uwSnelheidView.getProgress() + " km/u");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        float getal = this.uwReactietijdView.getProgress();
        this.uwReactieTextView.setText(getal / 10 + " sec");
        this.uwReactietijdView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                float getal = uwReactietijdView.getProgress();
                uwReactieTextView.setText(getal / 10 + " sec");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.buttonStopafstand.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toonStopAfstand(v);
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwReactietijdView = (SeekBar) v.findViewById(R.id.txtReactie);
        this.uwSnelheidView = (SeekBar) v.findViewById(R.id.txtSnelheid);
        this.droogWegTypeView = (RadioGroup)v.findViewById(R.id.rdbNatDroog);
        this.uwStopafstandView = (TextView) v.findViewById(R.id.txtStopafstand);
        this.uwSnelheidTextView = (TextView) v.findViewById(R.id.txbSnelheid);
        this.uwReactieTextView = (TextView) v.findViewById(R.id.txbReactie);
        this.buttonStopafstand = (Button) v.findViewById(R.id.btnBereken);
    }

    public void toonStopAfstand(View v) {
        int snelheid = this.uwSnelheidView.getProgress();
        float reactie = this.uwReactietijdView.getProgress();
        int radioButtonId = this.droogWegTypeView.getCheckedRadioButtonId();
        View radioButton = this.droogWegTypeView.findViewById(radioButtonId);
        int wegtype = this.droogWegTypeView.indexOfChild(radioButton);
        StopAfstandinfo info = new StopAfstandinfo(snelheid, reactie / 10, wegtype);
        double getal = Math.round(info.getStopafstand() * 100.0) / 100.0;
        this.uwStopafstandView.setText(getal + " m");
    }
}
