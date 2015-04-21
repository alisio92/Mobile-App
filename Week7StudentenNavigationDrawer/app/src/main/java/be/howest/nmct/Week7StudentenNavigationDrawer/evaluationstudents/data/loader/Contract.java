package be.howest.nmct.Week7StudentenNavigationDrawer.evaluationstudents.data.loader;

import android.provider.BaseColumns;

/**
 * Created by alisio on 30/03/2015.
 */
public final class Contract {
    public interface StudentColumns extends BaseColumns {
        public static final String COLUMN_STUDENT_NAAM = "student_naam";
        public static final String COLUMN_STUDENT_VOORNAAM = "student_voornaam";
        public static final String COLUMN_STUDENT_VOLLEDIGE_NAAM = "student_volledige_naam";
        public static final String COLUMN_STUDENT_EMAIL = "student_email";
        public static final String COLUMN_STUDENT_TOTAAL = "student_score_totaal";
    }
}
