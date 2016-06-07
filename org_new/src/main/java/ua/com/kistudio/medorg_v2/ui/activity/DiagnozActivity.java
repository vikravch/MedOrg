package ua.com.kistudio.medorg_v2.ui.activity;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ua.com.kistudio.medorg_v2.ui.fragments.DiagnozQuestionsFirstFragment;
import ua.com.kistudio.medorg_v2.ui.fragments.DiagnozQuestionsSecondFragment;
import ua.com.kistudio.medorg_v2.ui.fragments.DiagnozQuestionsThirdFragment;
import ua.com.kistudio.medorg_v2.ui.fragments.QuestionFragment;
import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class DiagnozActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     */
    private DiagnozPagerAdapter mSectionsPagerAdapter;
    public String[] answersOnQuestions = new String[Params.NUM_OF_QUESTIONS_DIAGNOZ];
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    QuestionFragment firstFragment = QuestionFragment.newInstance(0);
    private ViewPager mViewPager;
    private int mNumberFrame = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Arrays.fill(answersOnQuestions,"-1");
        setContentView(R.layout.activity_diagnoz);

        findViewById(R.id.btnNextDiagnoz).setOnClickListener(this);
        findViewById(R.id.btnPrevDiagnoz).setOnClickListener(this);
        findViewById(R.id.btnSaveDiagnoz).setOnClickListener(this);

        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().add(R.id.flDiagnoz, firstFragment).commit();
        }

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
                if (mNumberFrame<=Params.NUM_OF_QUESTIONS_DIAGNOZ) {

                    mNumberFrame++;
                    showFragmentByName(mNumberFrame);
                }
                break;
            case R.id.btnPrevDiagnoz:
                if (mNumberFrame>0){
                    mNumberFrame--;
                    showFragmentByName(mNumberFrame);
                }
                break;
            case R.id.btnSaveDiagnoz:
                ContentValues cv = new ContentValues();
                cv.put(Params.OPROS_TYPE,"o");



                Calendar calendar = new GregorianCalendar();
                calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String day = simpleDateFormat.format(calendar.getTime());

                cv.put(Params.OPROS_DATE,day);



                SharedPreferences sp = getSharedPreferences(Params.PREFERENCE_NAME, Context.MODE_PRIVATE);
                String name=sp.getString(ProfileActivity.PIB, "");
                cv.put(Params.OPROS_WHO,name);

                String res=answersOnQuestions[0];
                for (int i=1; i<answersOnQuestions.length; i++){
                    res+=";"+answersOnQuestions[i];
                }

                cv.put(Params.OPROS_RESULT, res);
                getContentResolver().insert(Params.OPROS_URI, cv);
                Toast.makeText(this,R.string.diagnoz_saved,Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void showFragmentByName(int mNumberFrame) {
        Fragment currentFragment = null;
        Bundle bundle = new Bundle();
        switch (mNumberFrame){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.flDiagnoz, firstFragment).commit();
                ((TextView) findViewById(R.id.tvAnswerActivityDiagnoz)).setText("");

                break;
            case 1:
                findViewById(R.id.btnSaveDiagnoz).setVisibility(View.VISIBLE);
                findViewById(R.id.btnPrevDiagnoz).setVisibility(View.VISIBLE);
            case 2:
            case 3:
            case 4:
                DiagnozQuestionsFirstFragment diagnozQuestionsFirstFragment = new DiagnozQuestionsFirstFragment();
                bundle.putInt("pos", mNumberFrame);
                diagnozQuestionsFirstFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flDiagnoz, diagnozQuestionsFirstFragment).commit();
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 13:
            case 14:

                DiagnozQuestionsSecondFragment diagnozQuestionsSecondFragment = new DiagnozQuestionsSecondFragment();
                bundle.putInt("pos", mNumberFrame - 2);
                diagnozQuestionsSecondFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flDiagnoz, diagnozQuestionsSecondFragment, "" + mNumberFrame).commit();
                break;
            case 16:
                DiagnozQuestionsSecondFragment diagnozQuestionsSecondFragment1 = new DiagnozQuestionsSecondFragment();
                bundle.putInt("pos", mNumberFrame - 2);
                diagnozQuestionsSecondFragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flDiagnoz, diagnozQuestionsSecondFragment1,""+mNumberFrame).commit();
                findViewById(R.id.btnNextDiagnoz).setVisibility(View.GONE);
                break;
            case 9:
            case 11:
            case 12:
            case 15:
                DiagnozQuestionsThirdFragment diagnozQuestionsThirdFragment = new DiagnozQuestionsThirdFragment();
                bundle.putInt("pos", mNumberFrame - 2);
                diagnozQuestionsThirdFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flDiagnoz,diagnozQuestionsThirdFragment,""+mNumberFrame).commit();
                findViewById(R.id.btnNextDiagnoz).setVisibility(View.VISIBLE);
                break;
        }

    }

}
