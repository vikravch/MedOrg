package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Android on 31.08.2016.
 */
public class AreaDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView textViewHeader = (TextView) findViewById(R.id.areasTextView);
        textViewHeader.setText(getResources().getString(R.string.header_areas_detail));

        Intent intent = getIntent();
        int distID = AreaActivity.ID_DIST[intent.getIntExtra(AreaActivity.EXTRA_DIST_ID,0)];
        String aboutClinic = getResources().getStringArray(distID)[(int) intent.getLongExtra(AreaActivity.EXTRA_DIST_POSITION,0)];
        Log.d(Params.LOG_TAG,"AreaDetailActivity: "+intent.getLongExtra(AreaActivity.EXTRA_DIST_POSITION,0));
        TextView textViewAbout = (TextView) findViewById(R.id.textView1);
        textViewAbout.setText(aboutClinic);
        ((TextView) findViewById(R.id.textView2)).setText("");
        Log.d(Params.LOG_TAG,"AreaDetailActivity: "+aboutClinic);
    }
}
