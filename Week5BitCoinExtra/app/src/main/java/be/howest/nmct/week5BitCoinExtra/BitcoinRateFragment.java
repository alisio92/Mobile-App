package be.howest.nmct.week5BitCoinExtra;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.Console;

public class BitcoinRateFragment extends Fragment{

    private EditText uwEuroView;
    private Button buttonWijzig;
    public float rate1BitcoinInEuros = 1;
    public static final String BITCOIN_RATE = "be.howest.nmct.NEW_BITCOIN_RATE";
    private OnBitcoinRateListener mBitcoinRateCallback;
    public static final String STATE_EURO = "be.howest.nmct.Week3ColorPicker.EURO";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            rate1BitcoinInEuros = getArguments().getFloat(BITCOIN_RATE);
            if(savedInstanceState!=null) {
                Float rate = savedInstanceState.getFloat(STATE_EURO);
                uwEuroView.setText(rate.toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);
        initVariables(v);
        this.buttonWijzig.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                rate1BitcoinInEuros = Float.parseFloat(uwEuroView.getText().toString());
                mBitcoinRateCallback.onBitcoinRateSelected(rate1BitcoinInEuros);
            }
        });
        this.uwEuroView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwEuroView.getText().toString().equals("0")) uwEuroView.setText("");
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwEuroView = (EditText) v.findViewById(R.id.txtEuro);
        this.buttonWijzig = (Button) v.findViewById(R.id.btnWijzig);
    }

    /*public static BitcoinRateFragment newInstance(float bitcoinrate){
        BitcoinRateFragment fragment = new BitcoinRateFragment();
        Bundle args = new Bundle();
        args.putFloat(BITCOIN_RATE, bitcoinrate);
        fragment.setArguments(args);
        return fragment;
    }*/

    public interface OnBitcoinRateListener {
        public void onBitcoinRateSelected(float value);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mBitcoinRateCallback = (OnBitcoinRateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Float euro = Float.parseFloat(uwEuroView.getText().toString());
        savedInstanceState.putFloat(STATE_EURO, euro);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Float euro = savedInstanceState.getFloat(STATE_EURO);
    }
}
