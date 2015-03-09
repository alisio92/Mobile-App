package be.howest.nmct.Week2BMI;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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

public class BMIFragment extends Fragment {

    private EditText uwHeightView;
    private EditText uwMassView;
    private TextView uwIndexView;
    private TextView uwCategoryView;
    private Button buttonUpdate;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);
        initVariables(v);
        this.imageView.setImageResource(getResourceId(null));
        this.buttonUpdate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toonBMI(v);
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwHeightView = (EditText) v.findViewById(R.id.txtHeight);
        this.uwMassView = (EditText) v.findViewById(R.id.txtMass);
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
        super.onResume();
    }

    public void toonBMI(View v){
        float height = Float.parseFloat(this.uwHeightView.getText().toString());
        int mass = Integer.parseInt(this.uwMassView.getText().toString());

        BMIInfo info = new BMIInfo(height, mass);
        float index = info.getBmiIndex();
        index = Math.round(index * 100) / 100;
        String category = BMIInfo.Category.getCategory(index).name();

        this.uwIndexView.setText(Float.toString(index));
        this.uwCategoryView.setText(category);
        this.imageView.setImageResource(getResourceId(BMIInfo.Category.getCategory(index)));
    }
}
