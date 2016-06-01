package ditsystems.com.zinger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 05.05.2016.
 */

public class PlaceholderFragmentSleepingMode extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final int FRAGMENT_CALCULATE_KEY = 1;
    private EditText mEtGoSleep;
    private EditText mEtWakeUp;

    private int[] wakeUpTime = {6,6,6,6,6,
                                6,0,0,0,0,
                                0,0,0,0,0,
                                0,0,0,0,21,
                                23,24,6,6};

    private int[] goToSleepTime = { 21,21,21,21,22,
                                    22,22,0,0,0,
                                    0,0,0,0,0,
                                    0,0,0,0,0,
                                    19,19,20,20};

    public PlaceholderFragmentSleepingMode() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragmentSleepingMode newInstance(int sectionNumber) {
        PlaceholderFragmentSleepingMode fragment = new PlaceholderFragmentSleepingMode();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case FRAGMENT_CALCULATE_KEY:
                rootView = inflater.inflate(R.layout.fragment_sleeping_mode, container, false);
                rootView.findViewById(R.id.btnSleepingMode).setOnClickListener(this);
                mEtGoSleep = (EditText) rootView.findViewById(R.id.etSleepSleepingMode);
                mEtWakeUp = (EditText) rootView.findViewById(R.id.etWakeUpSleepingMode);
                return rootView;

            default:
                rootView = inflater.inflate(R.layout.fragment_sleeping_mode, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSleepingMode:
                int goToSleepTime = 0;
                int wakeUpTime = 0;

                if (!mEtGoSleep.getText().toString().equals(""))
                {
                    goToSleepTime = Integer.parseInt(mEtGoSleep.getText().toString());
                    //int wakeUpTimeCount = (goToSleepTime+8)%24;

                    mEtWakeUp.setText(""+calculateWakeUp(goToSleepTime));
                }

                if (!mEtWakeUp.getText().toString().equals("")) {
                    wakeUpTime = Integer.parseInt(mEtWakeUp.getText().toString());
                    //int goToSleepTimeCount = (wakeUpTime+24-8)%24;
                    mEtGoSleep.setText(""+calculateGoToSleep(wakeUpTime));
                }

                Log.d(Const.TAG_LOG_PROJECT,"go - "+goToSleepTime+" wake up - "+wakeUpTime);

                break;
        }
    }

    private int calculateGoToSleep(int wakeUpTime) {
        return goToSleepTime[wakeUpTime];
    }

    private int calculateWakeUp(int goToSleepTime) {
        return wakeUpTime[goToSleepTime];
    }
}