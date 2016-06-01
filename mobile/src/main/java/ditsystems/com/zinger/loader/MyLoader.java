package ditsystems.com.zinger.loader;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.adapter.WeightControlAdapter;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 29.04.2016.
 */
public class MyLoader implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOAD_WATER_BALANCE = 1;
    public static final int LOAD_DIET = 2;
    public static final int LOAD_TRAINING = 3;
    private CursorLoader mCursorLoader;
    private Context context;

    public MyLoader(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOAD_WATER_BALANCE:
                Calendar today = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String formatted = simpleDateFormat.format(today.getTime());
                Log.d(Const.TAG_LOG_PROJECT,formatted);
                return new CursorLoader(context, Const.URI_VALUE_WATER,null, Const.FIELD_DAY_WATER+"=?", new String[]{formatted}, null);
            case LOAD_DIET:
                Log.d(Const.TAG_LOG_PROJECT,"Start load");
                return new CursorLoader(context,Const.URI_VALUE_DIETS,null,null,null,null);
            case LOAD_TRAINING:
                return new CursorLoader(context,Const.URI_VALUE_TRAININGS,null,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(Const.TAG_LOG_PROJECT,"Finish load id - "+loader.getId());

        switch (loader.getId()){
            case LOAD_WATER_BALANCE:
                showWaterBalance(data);
                break;
            case LOAD_DIET:
                showDiet(data);
                break;
            case LOAD_TRAINING:
                showTraining(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void showTraining(Cursor data) {
        RecyclerView recyclerView = (RecyclerView) ((Activity) context).findViewById(R.id.rvWeightControlDiagnostic);
        try {
            LinearLayoutManager lManager;
            lManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(lManager);

            WeightControlAdapter adapter = new WeightControlAdapter(context, data);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e){}
    }

    private void showDiet(Cursor data) {

        RecyclerView recyclerView = (RecyclerView) ((Activity) context).findViewById(R.id.rvWeightControlDiet);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        WeightControlAdapter adapter = new WeightControlAdapter(context,data);
        recyclerView.setAdapter(adapter);
    }

    private void showWaterBalance(Cursor data) {
        ListView lvOut = (ListView) ((Activity) context).findViewById(R.id.lvWaterCounter);
        TextView tvOut = (TextView) ((Activity) context).findViewById(R.id.tvWaterBalanceCounter);

        int totalVol = 0;

        SimpleCursorAdapter cAdapter = null;
        try {

            data.moveToFirst();
            do {

                int vol = data.getInt(data.getColumnIndex(Const.FIELD_VOLUME_WATER));
                totalVol += vol;

            } while (data.moveToNext());
            Log.d(Const.TAG_LOG_PROJECT, "total - " + totalVol);
            tvOut.setText("total - " + totalVol);

            cAdapter = new SimpleCursorAdapter(
                    context,
                    android.R.layout.simple_list_item_2,
                    data,
                    new String[]{Const.FIELD_VOLUME_WATER, Const.FIELD_DAY_WATER},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    Adapter.NO_SELECTION);
        }
        catch (CursorIndexOutOfBoundsException exception){
            MatrixCursor c = new MatrixCursor(new String[]{"_id","empty"});
            c.addRow(new Object[]{1,context.getResources().getString(R.string.no_water_text)});

            cAdapter = new SimpleCursorAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    c,
                    new String[]{"empty"},
                    new int[]{android.R.id.text1},
                    Adapter.NO_SELECTION);
        }
        lvOut.setAdapter(cAdapter);
    }

}
