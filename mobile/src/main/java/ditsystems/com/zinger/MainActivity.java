package ditsystems.com.zinger;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ditsystems.com.zinger.activities.BaseActivity;
import ditsystems.com.zinger.activities.SleepingModeActivity;
import ditsystems.com.zinger.activities.WaterBalanceActivity;
import ditsystems.com.zinger.activities.WeightControlActivity;
import ditsystems.com.zinger.fragments.EditProfileDialog;
import ditsystems.com.zinger.util.Const;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

/*

    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    private static final String LOG_TAG = "ZingerLogs";
    // Defines a variable for the search string
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    //private String[] mSelectionArgs = { mSearchString };
    private String[] mSelectionArgs = null;

*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //insertIntoDB();
        //queryFromDB();
        getSupportLoaderManager().initLoader(1,null,this);
    }



    private void queryFromDB() {
        Cursor c = getContentResolver().query(Const.URI_VALUE_TRAININGS,null,null,null,null);
        Log.d(Const.TAG_LOG_PROJECT,"--------------------------------------------------------------");
        c.moveToFirst();
        do {
            Log.d(Const.TAG_LOG_PROJECT,
                    String.format("name - %s, about - %s, desc - %s",
                            c.getString(c.getColumnIndex(Const.FIELD_NAME_DIETS)),
                            c.getString(c.getColumnIndex(Const.FIELD_ABOUT_DIETS)),
                            c.getString(c.getColumnIndex(Const.FIELD_DESCRIPTION_DIETS))));
        }while (c.moveToNext());
    }

    private void insertIntoDB() {
        ContentValues cv = new ContentValues();
        cv.put(Const.FIELD_NAME_DIETS,"name1");
        cv.put(Const.FIELD_DESCRIPTION_DIETS,"description text1");
        cv.put(Const.FIELD_ABOUT_DIETS,"about text1");
        getContentResolver().insert(Const.URI_VALUE_TRAININGS,cv);
    }

    private void initView() {
        findViewById(R.id.btnWeightControl).setOnClickListener(this);
        findViewById(R.id.btnWaterControl).setOnClickListener(this);
        findViewById(R.id.btnSleepingMode).setOnClickListener(this);
        findViewById(R.id.btnPedometr).setOnClickListener(this);

        findViewById(R.id.llMain).setOnClickListener(this);

        TextView tvGender = (TextView) findViewById(R.id.tvGenderMain);
        TextView tvAge = (TextView) findViewById(R.id.tvAgeMain);
        TextView tvWeight = (TextView) findViewById(R.id.tvWeightMain);
        TextView tvHeight = (TextView) findViewById(R.id.tvHeightMain);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        tvGender.setText("Gender \n"+shared.getString(Const.KEY_GENDER_SHARED,""));
        tvAge.setText("Age \n"+shared.getString(Const.KEY_AGE_SHARED,""));
        tvWeight.setText("Weight \n"+shared.getString(Const.KEY_WEIGHT_SHARED,""));
        tvHeight.setText("Height \n"+shared.getString(Const.KEY_HEIGHT_SHARED,""));

    }

    public void resetView() {
        TextView tvGender = (TextView) findViewById(R.id.tvGenderMain);
        TextView tvAge = (TextView) findViewById(R.id.tvAgeMain);
        TextView tvWeight = (TextView) findViewById(R.id.tvWeightMain);
        TextView tvHeight = (TextView) findViewById(R.id.tvHeightMain);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        tvGender.setText("Gender \n"+shared.getString(Const.KEY_GENDER_SHARED,""));
        tvAge.setText("Age \n"+shared.getString(Const.KEY_AGE_SHARED,""));
        tvWeight.setText("Weight \n"+shared.getString(Const.KEY_WEIGHT_SHARED,""));
        tvHeight.setText("Height \n"+shared.getString(Const.KEY_HEIGHT_SHARED,""));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return null; /*new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );*/
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
/*
        data.moveToFirst();
        String name = data.getString(
                data.getColumnIndex(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                        ContactsContract.Contacts.DISPLAY_NAME));
        mTvName.setText(name);
        Log.d(LOG_TAG," Load finished, name - "+name);*/

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.btnWeightControl:
                i = new Intent(this, WeightControlActivity.class);
                startActivity(i);
                break;
            case R.id.btnWaterControl:
                i = new Intent(this, WaterBalanceActivity.class);
                startActivity(i);
                break;
            case R.id.btnSleepingMode:
                i = new Intent(this, SleepingModeActivity.class);
                startActivity(i);
/*
                Toast.makeText(this,R.string.text_coming_soon,Toast.LENGTH_LONG).show();
*/
                break;
            case R.id.btnPedometr:
                Toast.makeText(this,R.string.text_coming_soon,Toast.LENGTH_LONG).show();
                //TODO Add intent to Pedometr activity
                break;
            case R.id.llMain:
                EditProfileDialog epd = new EditProfileDialog();
                epd.show(getSupportFragmentManager(),"dial");
                break;
        }

    }


}
