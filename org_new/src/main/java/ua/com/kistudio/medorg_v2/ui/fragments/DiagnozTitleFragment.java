package ua.com.kistudio.medorg_v2.ui.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozActivity;
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozShowActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 18.03.2016.
 */
public class DiagnozTitleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    CursorLoader loader;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diagnoz_title, container, false);
        /*listView = ((ListView) v.findViewById(R.id.lvDiagnozTitle));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[]{"one","two","three"});
        listView.setAdapter(arrayAdapter);
        */
        ((TextView) getActivity().findViewById(R.id.tvAnswerActivityDiagnoz)).setText("");

        getLoaderManager().initLoader(1, null, this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        loader = new CursorLoader(getActivity(),Params.OPROS_URI,null,"type='o'",null,null);
        Log.d(Params.LOG_TAG,"loader create");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data!=null){
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{"date"},
                new int[]{android.R.id.text1},
                Adapter.NO_SELECTION);
        listView.setAdapter(simpleCursorAdapter);
            listView.setOnItemClickListener(this);
    }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(Params.LOG_TAG,"Click");
        startActivity(new Intent(getActivity(),DiagnozShowActivity.class));
    }
}
