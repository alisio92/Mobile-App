package be.howest.nmct.Week2BMI;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import be.howest.nmct.BMI.BMIInfo;
import be.howest.nmct.week4implicit.R;

public class BMIFragment extends Fragment {

    private EditText uwHeightView;
    private EditText uwMassView;
    private TextView uwIndexView;
    private TextView uwCategoryView;
    private Button buttonUpdate;
    private ImageView imageView;
    private float index = 0;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);
        initVariables(v);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        this.uwHeightView.setText(Float.toString(settings.getFloat("Height", 0.0f)));
        this.uwMassView.setText(Integer.toString(settings.getInt("Mass", 0)));
        this.imageView.setImageResource(getResourceId(null));
        this.buttonUpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //index = berekenBMI();
                toonBMI(index);
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwHeightView = (EditText) v.findViewById(R.id.txtHeight);
        this.uwHeightView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && uwHeightView.getText().toString().equals("0.0")) uwHeightView.setText("");
            }
        });
        this.uwMassView = (EditText) v.findViewById(R.id.txtMass);
        this.uwMassView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus && uwMassView.getText().toString().equals("0"))uwMassView.setText("");
            }
        });
        this.uwIndexView = (TextView) v.findViewById(R.id.txbIndex);
        this.uwCategoryView = (TextView) v.findViewById(R.id.txbCategory);
        this.buttonUpdate = (Button) v.findViewById(R.id.btnUpdate);
        this.imageView = (ImageView) v.findViewById(R.id.imgBMI);
    }

    private int getResourceId(BMIInfo.Category category) {
        if(category!= null){
            if(category.name().equals("ZEER_GROOT_OVERGEWICHT")) return R.drawable.silhouette_8;
            if(category.name().equals("ERNSTIG_OVERGEWICHT")) return R.drawable.silhouette_7;
            if(category.name().equals("MATIG_OVERGEWICHT")) return R.drawable.silhouette_6;
            if(category.name().equals("OVERGEWICHT")) return R.drawable.silhouette_5;
            if(category.name().equals("NORMAAL")) return R.drawable.silhouette_4;
            if(category.name().equals("ONDERGEWICHT")) return R.drawable.silhouette_3;
            if(category.name().equals("ERNSTIG_ONDERGEWICHT")) return R.drawable.silhouette_2;
            if(category.name().equals("GROOT_ONDERGEWICHT")) return R.drawable.silhouette_1;
        }
        return R.drawable.ic_launcher;
    }

    @Override
    public void onResume() {
        Log.d("log: " + getClass().getSimpleName(), "onResume");
        index = berekenBMI();
        Log.d("log: " + getClass().getSimpleName(), "berekenBMI");
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.d("log: " + getClass().getSimpleName(), "onStop");
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("Height", Float.parseFloat(this.uwHeightView.getText().toString()));
        editor.putInt("Mass", Integer.parseInt(this.uwMassView.getText().toString()));
        editor.commit();
        super.onStop();
    }


    public float berekenBMI(){
        if((TextUtils.isEmpty(this.uwHeightView.getText())) || (TextUtils.isEmpty(this.uwMassView.getText()))) return 0;
        float height = Float.parseFloat(this.uwHeightView.getText().toString());
        int mass = Integer.parseInt(this.uwMassView.getText().toString());

        BMIInfo info = new BMIInfo(height, mass);
        index = info.getBmiIndex();
        return Math.round(index * 100) / 100;
    }

    public void toonBMI(float index){
        String category = BMIInfo.Category.getCategory(index).name();
        this.uwIndexView.setText(Float.toString(index));
        this.uwCategoryView.setText(category);
        this.imageView.setImageResource(getResourceId(BMIInfo.Category.getCategory(index)));
    }
}
