package be.nmct.howest.week6horoscoop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
            super(HoroscoopActivity.this, R.layout.activity_horoscoop,R.id.txtHoroscoopName ,Data.Horoscoop.values());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = super.getView(position, convertView, parent);

            final Data.Horoscoop horoscoop = Data.Horoscoop.values()[position];

            ViewHolder holder = (ViewHolder) row.getTag();

            if(holder == null)
            {
                holder = new ViewHolder(row);
                row.setTag(holder);
            }

            TextView textViewNaamHoroscoop = holder.textViewNaamHoroscoop;
            textViewNaamHoroscoop.setText(horoscoop.getNaamHoroscoop());

            ImageView icon = holder.imageViewHoroscoop;
            icon.setImageResource(getResourceId(horoscoop));

            Button btnInfo = holder.btnToonInfo;
            btnInfo.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    showInfo(horoscoop);
                }
            });

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    onListItemClick(v, horoscoop);
                }
            });
            return row;
        }

        protected void onListItemClick(View v, Data.Horoscoop horoscoop)
        {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXTRA_HOROSCOOP, getResourceId(horoscoop));
            setResult(MainActivity.REQUEST_HOROSCOOP, intent);
            finish();
        }

        private void showInfo(Data.Horoscoop horoscoop)
        {
            String bericht = horoscoop.getBeginDatum() + " - " + horoscoop.getEindDatum();
            Toast.makeText(HoroscoopActivity.this, bericht , Toast.LENGTH_SHORT).show();
        }

        private int getResourceId(Data.Horoscoop horoscoop)
        {
            switch(horoscoop)
            {
                case WATERMAN:
                    return R.drawable.waterman;
                case VISSEN:
                    return R.drawable.vissen;
                case RAM:
                    return R.drawable.ram;
                case STIER:
                    return R.drawable.stier;
                case TWEELING:
                    return R.drawable.tweeling;
                case KREEFT:
                    return R.drawable.kreeft;
                case LEEUW:
                    return R.drawable.leeuw;
                case MAAGD:
                    return R.drawable.maagd;
                case WEEGSCHAAL:
                    return R.drawable.weegschaal;
                case SCHORPIOEN:
                    return R.drawable.schorpioen;
                case BOOGSCHUTTER:
                    return R.drawable.boogschutter;
                case STEENBOK:
                    return R.drawable.steenbok;
                default:
                    return 0;
            }
        }
    }
}


