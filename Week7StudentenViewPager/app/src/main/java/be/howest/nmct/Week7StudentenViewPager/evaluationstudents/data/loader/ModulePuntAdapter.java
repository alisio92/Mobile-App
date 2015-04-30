package be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.loader;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;

import be.howest.nmct.Week7StudentenViewPager.R;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.ModulePunt;
import be.howest.nmct.Week7StudentenViewPager.evaluationstudents.data.admin.Student;

/**
 * Created by alisio on 15/04/2015.
 */
public class ModulePuntAdapter extends ArrayAdapter<ModulePunt> {
    private Collection<ModulePunt> modulepunten;

    public ModulePuntAdapter(Context context, Student student) {
        super(context, R.layout.cel_module, R.id.textViewModuleNaam);
        modulepunten = student.getScoresStudent().values();
        this.addAll(modulepunten);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        ModulePunt mp = (ModulePunt) (modulepunten.toArray())[position];

        String modulenaam = mp.getModule();
        double score = mp.getScore();

        TextView txvModuleNaam = (TextView) row
                .findViewById(R.id.textViewModuleNaam);
        txvModuleNaam.setText(modulenaam);

        TextView txvModulePunt = (TextView) row
                .findViewById(R.id.textViewScore);
        txvModulePunt.setText(score + "");

        if (score <= 9) {
            txvModulePunt.setTextColor(Color.parseColor("#FF0000"));
        } else {
            txvModulePunt.setTextColor(Color.parseColor("#000000"));
        }

        return row;
    }
}

