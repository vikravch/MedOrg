package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ua.com.kistudio.medorg_v2.R;

public class AreasListActivity extends Activity {

	public static final String EXTRA_ID = "com.example.organizer_04_dovidnykzaklady.ID";
	ListView areasListView;
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";
	final String LOG_TAG = "myLogs";
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_areas_list);
		areasListView = (ListView) findViewById(R.id.areasListView);
		
		String[] areasList = getResources().getStringArray(R.array.district_array);



		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.menu3_item,R.id.tvPibDoctor,areasList);
		areasListView.setAdapter(adapt);
		areasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View itemClicked,
		int position, long id) {
			TextView textView = (TextView)  itemClicked.findViewById(R.id.tvPibDoctor);
			String strText = textView.getText().toString();
			
			Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
			Intent intent = new Intent(AreasListActivity.this, AreaActivity.class);
			intent.putExtra(EXTRA_MESSAGE, strText);
			intent.putExtra(EXTRA_ID,position);
			startActivity(intent);
			}
		});
		
	}
}