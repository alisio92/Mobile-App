package be.howest.nmct.week5BitCoinExtra;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Console;

public class MainActivity extends Activity implements ChangeFragment.OnChangeListener, BitcoinRateFragment.OnBitcoinRateListener {

    public float huidigeWisselkoers = 1;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        huidigeWisselkoers = prefs.getFloat("changeRate", 0.0f);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.frag_main, ChangeFragment.newInstance(huidigeWisselkoers), "change")
                    /*.addToBackStack("start_ChangeFragment")*/
                    .commit();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("changeRate", huidigeWisselkoers);
        editor.commit();
    }

    public void showFragmentBitcoinRate(float value) {
        BitcoinRateFragment newFragment = new BitcoinRateFragment();
        Bundle args = new Bundle();
        args.putFloat(BitcoinRateFragment.BITCOIN_RATE, value);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_main, newFragment);
        transaction.addToBackStack(null);//null
        transaction.commit();
    }

    public void showFragmentChange(float value) {
        huidigeWisselkoers = value;
        ChangeFragment fragment = (ChangeFragment) getFragmentManager().findFragmentByTag("change");
        fragment.setCurrentRateBitcoinInEuro(value);
        getFragmentManager().popBackStack();
    }

    public void onBitcoinRateSelected(float value) {
        showFragmentChange(value);
    }

    public void onChangeSelected(float value) {
        showFragmentBitcoinRate(value);
    }

}
