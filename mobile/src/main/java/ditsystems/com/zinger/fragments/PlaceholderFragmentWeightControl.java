package ditsystems.com.zinger.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.loader.MyLoader;
import ditsystems.com.zinger.util.Const;

public class PlaceholderFragmentWeightControl extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int FRAGMENT_TRAINING_KEY = 1;
    private static final int FRAGMENT_DIET_KEY = 2;
    private static final int FRAGMENT_DIAGNOSTICS_KEY = 3;

    private EditText mEtGender;
    private EditText mEtAge;
    private EditText mEtWeight;
    private EditText mEtHeight;
    private EditText mEtAction;
    private TextView tvOutCalculate;
    private RecyclerView recyclerView;
    public PlaceholderFragmentWeightControl() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentWeightControl newInstance(int sectionNumber) {
        PlaceholderFragmentWeightControl fragment = new PlaceholderFragmentWeightControl();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)){

            case FRAGMENT_DIAGNOSTICS_KEY:
                rootView = inflater.inflate(R.layout.fragment_weight_control_training, container, false);
                rootView.findViewById(R.id.btnCalculate).setOnClickListener(this);

                mEtGender = (EditText) rootView.findViewById(R.id.etGender);
                mEtAge = (EditText) rootView.findViewById(R.id.etAge);
                mEtWeight = (EditText) rootView.findViewById(R.id.etWeight);
                mEtHeight = (EditText) rootView.findViewById(R.id.etHeight);
                mEtAction = (EditText) rootView.findViewById(R.id.etActivity);
                tvOutCalculate = (TextView) rootView.findViewById(R.id.tvResult);

                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
                mEtGender.setText(shared.getString(Const.KEY_GENDER_SHARED,""));
                mEtAge.setText(shared.getString(Const.KEY_AGE_SHARED,""));
                mEtWeight.setText(shared.getString(Const.KEY_WEIGHT_SHARED,""));
                mEtHeight.setText(shared.getString(Const.KEY_HEIGHT_SHARED,""));

                return rootView;

            case FRAGMENT_DIET_KEY:
                Log.d(Const.TAG_LOG_PROJECT,"Diet");
                rootView = inflater.inflate(R.layout.fragment_weight_control_diet, container, false);

                getActivity().getSupportLoaderManager().initLoader(MyLoader.LOAD_DIET,null,new MyLoader(getActivity()));

                return rootView;

            case FRAGMENT_TRAINING_KEY:
                Log.d(Const.TAG_LOG_PROJECT,"Train");
                rootView = inflater.inflate(R.layout.fragment_weight_control_diagnostic, container, false);

                getActivity().getSupportLoaderManager().initLoader(MyLoader.LOAD_TRAINING,null,new MyLoader(getActivity()));

                return rootView;

            default:
                rootView = inflater.inflate(R.layout.fragment_weight_control, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCalculate:
                double value = calculateBMI();
                tvOutCalculate.setText(String.format("result - %.2f",value));
                break;
        }
    }

    private double calculateBMI() {
        String gender = "M";
        int age = 0;
        int weight = 0;
        double height = 0.0;
        double action = 0.0;
        try {
            gender = mEtGender.getText().toString();
            age = Integer.parseInt(mEtAge.getText().toString());
            weight = Integer.parseInt(mEtWeight.getText().toString());
            height = (double) Integer.parseInt(mEtHeight.getText().toString()) / 100;
            //double height = (double) 130/100;
            action = Double.parseDouble(mEtAction.getText().toString());
            Log.d(Const.TAG_LOG_PROJECT, "height - " + height);
            Log.d(Const.TAG_LOG_PROJECT, "square - " + (height * height));
        }
        catch (NumberFormatException e){

        }
        return weight/((height)*(height));
    }
}
