package ua.com.kistudio.medorg_v2.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Android on 09.06.2016.
 */
public class ListSubVidchuttiaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    ListView listView;
    CursorLoader loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_sub_vidchuttia);
        listView = (ListView) findViewById(R.id.lvSubVidchuttja);
        getSupportLoaderManager().initLoader(1,null,this);
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"one","two","three"});
        listView.setAdapter(arrayAdapter);*/
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        loader = new CursorLoader(this, Params.OPROS_URI,null,"type='v'",null,null);
        Log.d(Params.LOG_TAG,"loader create");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data!=null){
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.menu3_item,
                    data,
                    new String[]{Params.OPROS_RESULT,Params.OPROS_DATE},
                    new int[]{R.id.tvIdDoctor,R.id.tvPibDoctor},
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
        Intent intent = new Intent(this, DetailSubVidchuttiaActivity.class);
        intent.putExtra(Params.OPROS_RESULT,((TextView) view.findViewById(R.id.tvIdDoctor)).getText());
        startActivity(intent);
    }
}
