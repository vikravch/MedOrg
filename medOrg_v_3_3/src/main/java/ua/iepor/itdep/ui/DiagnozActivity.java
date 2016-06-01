package ua.iepor.itdep.ui;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.medorg.R;

import ua.iepor.itdep.util.DiagnozPagerAdapter;
import ua.iepor.itdep.util.Params;

public class DiagnozActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private DiagnozPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int mNumberFrame = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnoz);

        findViewById(R.id.btnNextDiagnoz).setOnClickListener(this);
        findViewById(R.id.btnPrevDiagnoz).setOnClickListener(this);
        findViewById(R.id.btnSaveDiagnoz).setOnClickListener(this);


        getFragmentManager().beginTransaction().add(R.id.flDiagnoz,QuestionFragment.newInstance(mNumberFrame)).commit();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new DiagnozPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.container);
        //mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNextDiagnoz:
                if (mNumberFrame<Params.NUM_OF_QUESTIONS_DIAGNOZ) {
                    mNumberFrame++;
                    getFragmentManager().beginTransaction().replace(R.id.flDiagnoz, QuestionFragment.newInstance(mNumberFrame)).commit();
                }
                break;
            case R.id.btnPrevDiagnoz:
                if (mNumberFrame>0){
                    mNumberFrame--;
                    getFragmentManager().beginTransaction().replace(R.id.flDiagnoz,QuestionFragment.newInstance(mNumberFrame)).commit();
                }
                break;
            case R.id.btnSaveDiagnoz:
                break;
        }
    }

}
