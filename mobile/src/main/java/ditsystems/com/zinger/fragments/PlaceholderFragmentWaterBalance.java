package ditsystems.com.zinger.fragments;

/**
 * Created by Android on 28.04.2016.
 */

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.loader.MyLoader;
import ditsystems.com.zinger.util.Const;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentWaterBalance extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final int FRAGMENT_SUMMARY_KEY = 1;
    public static final int FRAGMENT_COUNTER_KEY = 2;

    private EditText mEtGender;
    private EditText mEtAge;
    private EditText mEtWeight;
    private EditText mEtHeight;
    private EditText mEtAction;
    private TextView tvOutCalculate;
    private TextView tvCounterWater;

    public PlaceholderFragmentWaterBalance() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentWaterBalance newInstance(int sectionNumber) {
        PlaceholderFragmentWaterBalance fragment = new PlaceholderFragmentWaterBalance();
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
            case FRAGMENT_SUMMARY_KEY:
                rootView = inflater.inflate(R.layout.fragment_water_balance_summary, container, false);
                rootView.findViewById(R.id.btnCalculate).setOnClickListener(this);

                mEtGender = (EditText) rootView.findViewById(R.id.etGender);
                mEtAge = (EditText) rootView.findViewById(R.id.etAge);
                mEtWeight = (EditText) rootView.findViewById(R.id.etWeight);
                mEtHeight = (EditText) rootView.findViewById(R.id.etHeight);
                mEtAction = (EditText) rootView.findViewById(R.id.etActivity);


                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
                mEtGender.setText(shared.getString(Const.KEY_GENDER_SHARED,""));
                mEtAge.setText(shared.getString(Const.KEY_AGE_SHARED,""));
                mEtWeight.setText(shared.getString(Const.KEY_WEIGHT_SHARED,""));
                mEtHeight.setText(shared.getString(Const.KEY_HEIGHT_SHARED,""));

                tvOutCalculate = (TextView) rootView.findViewById(R.id.tvResult);

                return rootView;

            case FRAGMENT_COUNTER_KEY:

                rootView = inflater.inflate(R.layout.fragment_water_balance_counter, container, false);
                tvCounterWater = (TextView) rootView.findViewById(R.id.tvWaterBalanceCounter);

                getActivity().getSupportLoaderManager().initLoader(MyLoader.LOAD_WATER_BALANCE,null,new MyLoader(getActivity()));

                return rootView;

            default:
                rootView = inflater.inflate(R.layout.fragment_water_balance, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCalculate:
                double value = calculateWaterBalance();
                int glasses = (int) (value*1000)/250;
                tvOutCalculate.setText("result - "+value+ " or "+glasses+" * 250");
                break;
        }
    }

    private double calculateWaterBalance() {

        String gender = "M";
        int age = 0;
        int weight = 0;
        double height = 0.0;
        double action = 0.0;
        try {
        gender = mEtGender.getText().toString();
        age = Integer.parseInt(mEtAge.getText().toString());
        weight = Integer.parseInt(mEtWeight.getText().toString());
        height = Integer.parseInt(mEtHeight.getText().toString());
        action = Double.parseDouble(mEtAction.getText().toString());
       }
        catch (NumberFormatException e){
        }
        switch (gender){
            case "M":
                return weight * 0.04 + action * 0.6;
            case "W":
                return weight * 0.03 + action * 0.4;
            default:
                return 0;
        }
    }
}