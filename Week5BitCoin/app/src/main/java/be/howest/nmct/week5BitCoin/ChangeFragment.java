package be.howest.nmct.week5BitCoin;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeFragment extends Fragment {

    private TextView uwBitcoinView;
    private EditText uwEuroView;
    private TextView uwRateView;
    private Button buttonBitcoin;
    private Button buttonEuro;
    private Button buttonKoers;
    public float currentRateBitcoinInEuro = 1;
    public static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";
    private OnChangeListener mChangeCallback;

    public void setCurrentRateBitcoinInEuro(float currentRateBitcoinInEuro) {
        this.currentRateBitcoinInEuro = currentRateBitcoinInEuro;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            currentRateBitcoinInEuro = getArguments().getFloat(BITCOIN_RATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bit_coin, container, false);
        initVariables(v);
        this.buttonBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToBit();
            }
        });
        this.buttonEuro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeToEuro();
            }
        });
        this.buttonKoers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mChangeCallback.onChangeSelected(currentRateBitcoinInEuro);
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwBitcoinView = (TextView) v.findViewById(R.id.txtBedragBitcoin);
        this.uwBitcoinView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwBitcoinView.getText().toString().equals("0")) uwBitcoinView.setText("");
            }
        });
        this.uwEuroView = (EditText) v.findViewById(R.id.txtBedragEuro);
        this.uwEuroView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwEuroView.getText().toString().equals("0")) uwEuroView.setText("");
            }
        });
        this.uwRateView = (TextView) v.findViewById(R.id.txbRate);
        showKoers();
        this.buttonBitcoin = (Button) v.findViewById(R.id.btnONBit);
        this.buttonEuro = (Button) v.findViewById(R.id.btnONE);
        this.buttonKoers = (Button) v.findViewById(R.id.btnWijzig);
    }

    private void showKoers(){
        this.uwRateView.setText("1 bitcoin = " + currentRateBitcoinInEuro);
    }

    private void changeToEuro(){
        Float bitcoin = Float.parseFloat(uwBitcoinView.getText().toString());
        Float getal = bitcoin * currentRateBitcoinInEuro;
        this.uwEuroView.setText(getal.toString());
    }

    private void changeToBit(){
        Float euro = Float.parseFloat(uwEuroView.getText().toString());
        Float getal = euro / currentRateBitcoinInEuro;
        this.uwBitcoinView.setText(getal.toString());
    }

    public static ChangeFragment newInstance(float bitcoinrate){
        ChangeFragment fragment = new ChangeFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnChangeListener {
        public void onChangeSelected(float position);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mChangeCallback = (OnChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
