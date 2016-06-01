package ua.iepor.itdep.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medorg.R;

/**
 * Created by Вiталя on 01.04.2016.
 */
public class QuestionFragment extends Fragment{

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
}
