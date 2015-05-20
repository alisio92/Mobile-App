package be.howest.nmct.project2015.data.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import be.howest.nmct.project2015.data.Contract;
import be.howest.nmct.project2015.data.helper.Helper;
import be.howest.nmct.project2015.data.helper.Step;
import be.howest.nmct.project2015.data.json.DirectionsJSONParser;

/**
 * Created by alisio on 19/05/2015.
 */
public class DetailLoader extends AsyncTaskLoader<Cursor> {

    private Cursor mCursor;
    private final String[] columnNames = new String[]
            {
                    BaseColumns._ID,
                    Contract.MapColumns.COLUMN_INNER_DISTANCE,
                    Contract.MapColumns.COLUMN_INNER_DURATION,
                    Contract.MapColumns.COLUMN_HTML,
            };

    private static Object lock = new Object();

    public DetailLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        if (mCursor == null) {
            loadCursor();
        }
        return mCursor;
    }

    private void loadCursor() {
        synchronized (lock) {
            if (mCursor != null) return;

            MatrixCursor matrixCursor = new MatrixCursor(columnNames);

            for (int i = 0; i < DirectionsJSONParser.getPolylines().size(); i++) {
                Step step = DirectionsJSONParser.getPolylines().get(i);
                MatrixCursor.RowBuilder row = matrixCursor.newRow();
                row.add(i);
                row.add(step.getDistance());
                row.add(step.getDuration());
                row.add(step.getHtml());
            }
            mCursor = matrixCursor;
        }
    }
}
