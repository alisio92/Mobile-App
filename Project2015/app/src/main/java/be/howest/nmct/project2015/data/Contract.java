package be.howest.nmct.project2015.data;

import android.provider.BaseColumns;

/**
 * Created by alisio on 19/05/2015.
 */
public class Contract {
    public interface MapColumns extends BaseColumns
    {
        public static final String COLUMN_ROUTES = "routes";
        public static final String COLUMN_LEGS = "legs";
        public static final String COLUMN_STEPS = "steps";

        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_POINTS = "points";

        public static final String COLUMN_DISTANCE = "distance";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_STARTADRES = "start_address";
        public static final String COLUMN_ENDADRES = "end_address";

        public static final String COLUMN_ARRAY = "array";
        public static final String COLUMN_POLYLINE = "polyline";
        public static final String COLUMN_HTML = "html_instructions";
        public static final String COLUMN_INNER_DISTANCE = "distance";
        public static final String COLUMN_INNER_DURATION = "duration";
    }
}
