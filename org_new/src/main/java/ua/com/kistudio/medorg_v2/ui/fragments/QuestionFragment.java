package ua.com.kistudio.medorg_v2.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import ua.com.kistudio.medorg_v2.ui.activity.DiagnozShowActivity;
import ua.com.kistudio.medorg_v2.ui.activity.MainActivity;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 01.04.2016.
 */
public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    CursorLoader loader;
    ListView listView;
    Cursor dataResult;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mNumber;
    public QuestionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuestionFragment newInstance(int sectionNumber) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = null;
        switch (mNumber){
            case 0:
                rootView = inflater.inflate(R.layout.fragment_diagnoz, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.diagnoz_about));
                Activity activity = getActivity();
                activity.findViewById(R.id.btnSaveDiagnoz).setVisibility(View.GONE);
                activity.findViewById(R.id.btnPrevDiagnoz).setVisibility(View.GONE);
                listView = ((ListView) rootView.findViewById(R.id.lvTitleFrag));
                getLoaderManager().initLoader(1, null, this);
                break;
            case 1:
                createListViewQuestion(inflater, container, getResources().getStringArray(R.array.tnm_m_array));
                break;
        }


/*
        View rootView = inflater.inflate(R.layout.fragment_diagnoz, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
*/
        return rootView;
    }

    private View createListViewQuestion(LayoutInflater inflater, ViewGroup container, String[] stringArray) {
        View localView = inflater.inflate(R.layout.fragment_diagnoz_list,container,false);
        ListView listView = (ListView) localView.findViewById(R.id.lvDiagnozQuestion);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,stringArray);
        listView.setAdapter(arrayAdapter);
        return localView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        loader = new CursorLoader(getActivity(), Params.OPROS_URI,null,"type='o'",null,null);
        Log.d(Params.LOG_TAG, "loader create");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataResult = data;
        if (data!=null){
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.menu3_item,
                    data,
                    new String[]{Params.OPROS_DATE},
                    new int[]{R.id.tvPibDoctor},
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
        dataResult.moveToPosition(position);
        String arrayRes = dataResult.getString(dataResult.getColumnIndex(Params.OPROS_RESULT));
        Log.d(Params.LOG_TAG, "result is - "+arrayRes);
        Intent intent = new Intent(getActivity(),DiagnozShowActivity.class);
        intent.putExtra("res",arrayRes);
        startActivity(intent);
    }
}
