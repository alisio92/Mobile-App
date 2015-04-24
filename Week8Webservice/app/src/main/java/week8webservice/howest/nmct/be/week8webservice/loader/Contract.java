package week8webservice.howest.nmct.be.week8webservice.loader;

import android.provider.BaseColumns;

/**
 * Created by alisio on 24/04/2015.
 */
public class Contract
{
    public interface KotColumns extends BaseColumns
    {
        public static final String COLUMN_ADRES = "ADRES";
        public static final String COLUMN_GEMEENTE = "GEMEENTE";
        public static final String COLUMN_HUISNUMMER = "HUISNR";
        public static final String COLUMN_BISNUMMER = "Bisnr.";
        public static final String COLUMN_AANTAL_KAMERS = "aantal kamers";
    }
}
