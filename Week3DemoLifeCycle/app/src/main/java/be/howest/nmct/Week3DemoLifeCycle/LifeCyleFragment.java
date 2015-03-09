package be.howest.nmct.Week3DemoLifeCycle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LifeCyleFragment extends Fragment {

    private Button buttonClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("log: " + getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_life_cyle, container, false);
        initVariables(v);
        this.buttonClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getActivity().finish();
            }
        });
        return v;
    }


    @Override
    public void onDestroy() {
        Log.d("log: " + getClass().getSimpleName(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.d("log: " + getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("log: " + getClass().getSimpleName(), "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.d("log: " + getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d("log: " + getClass().getSimpleName(), "onStart");
        super.onStart();
    }

   // @Override
   // public void onRestart() {
   //     Log.d("log: " + getClass().getSimpleName(), "onRestart");
   //     super.onRestart();
   // }

    public void initVariables(View v){
        this.buttonClose = (Button) v.findViewById(R.id.btnClose);
    }
}
