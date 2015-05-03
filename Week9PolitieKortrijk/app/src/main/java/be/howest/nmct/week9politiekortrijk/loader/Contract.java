package be.howest.nmct.week9politiekortrijk.loader;

import android.provider.BaseColumns;

/**
 * Created by alisio on 3/05/2015.
 */
public class Contract {
    public interface MetingColumns extends BaseColumns
    {
        public static final String COLUMN_ADRES = "Straat";
        public static final String COLUMN_GEMEENTE = "Gemeente";
        public static final String COLUMN_MAAND = "Maand";
        public static final String COLUMN_AANTAL_CONTROLES= "Aantal controles";
        public static final String COLUMN_AANTAL_VOERTUIGEN= "Gepasseerde voertuigen";
        public static final String COLUMN_AANTAL_OVERTREDINGEN= "Vtg in overtreding";
        public static final String COLUMN_X= "X";
        public static final String COLUMN_Y= "Y";
    }
}
