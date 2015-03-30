package be.nmct.howest.week6horoscoop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectGeboortejaarActivity extends ListActivity {

    private ListAdapter adapter;
    private final static List<String> GEBOORTEJAREN;
    static {
        GEBOORTEJAREN = new ArrayList<>(Calendar.getInstance().get(Calendar.YEAR) - 1900);
        for(int jaar = 1900; jaar < Calendar.getInstance().get(Calendar.YEAR); jaar++){
            GEBOORTEJAREN.add("" + jaar);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, GEBOORTEJAREN);
        //adapter = new ArrayAdapter<>(this, R.layout.activity_select_geboortejaar, GEBOORTEJAREN);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_geboortejaar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        String sGeboortejaar = GEBOORTEJAREN.get(position);
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_BIRTHYEAR, sGeboortejaar);
        setResult(RESULT_OK, intent);
        finish();
    }
}
