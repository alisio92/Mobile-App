package be.nmct.howest.week8horoscoop;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import be.nmct.howest.week8horoscoop.data.Data;


public class HoroscoopActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_horoscoop);
        setListAdapter(new HoroscoopAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_horoscoop, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_HOROSCOOP, horoscope.getDrawable());
        setResult(RESULT_OK, intent);
        finish();
    }

    class ViewHolder{
        public ImageView imageViewHoroscoop = null;
        public TextView textViewNaamHoroscoop = null;
        public Button btnToonInfo = null;

        public ViewHolder(View row)
        {
            this.imageViewHoroscoop = (ImageView) row.findViewById(R.id.imgIcon);
            this.textViewNaamHoroscoop = (TextView) row.findViewById(R.id.txtHoroscoopName);
            this.btnToonInfo = (Button) row.findViewById(R.id.btnInfo);
        }
    }

    class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop>
    {
        public HoroscoopAdapter()
        {
            super(HoroscoopActivity.this, R.layout.custom_list_item,R.id.txtHoroscoopName ,Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscope = Data.Horoscoop.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if(holder == null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewName = holder.textViewNaamHoroscoop;
            textViewName.setText("" + horoscope.getNaamHoroscoop());

            ImageView imageViewIcon = holder.imageViewHoroscoop;
            imageViewIcon.setImageResource(horoscope.getDrawable());

            Button buttonInfo = holder.btnToonInfo;
            buttonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), horoscope.getBeginDatum() + " - " + horoscope.getEindDatum(), Toast.LENGTH_SHORT).show();
                }
            });

            return row;
        }

        private void showInfo(Data.Horoscoop horoscoop)
        {
            String bericht = horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum();
            Toast.makeText(HoroscoopActivity.this, bericht , Toast.LENGTH_SHORT).show();
        }
    }
}


