package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.util.Params;

public class AreaActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";

	public static final int[] ID_DIST = {R.array.crimea,R.array.vinnutsja,R.array.volyn,R.array.dnipro,
	R.array.doneck,R.array.zhytomyr,R.array.zakarp, R.array.zaporiz, R.array.ivanofr,
	R.array.kyiv,R.array.kirovograd,R.array.luhansk,R.array.lviv,R.array.mikolajiv,R.array.odessa,
	R.array.poltava,R.array.rivne, R.array.sumy,R.array.ternopil,R.array.harkiv,R.array.herson,
	R.array.hmel,R.array.cherkassy,R.array.chernivtsy,R.array.chernihiv};
	public static final String EXTRA_DIST_ID = "dist_id";
	public static final String EXTRA_DIST_POSITION = "dist_position";

	SQLiteDatabase db;
	TextView areaTextView;
	final String LOG_TAG = "myLogs";
	ListView areaListView;
	int distId;
	String[] distClinics;
    String[] distClinicsNames;
	int menuArrNumber, menuArrCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area);
		
		Intent intent = getIntent();
		String strText = intent.getStringExtra(AreasListActivity.EXTRA_MESSAGE);
		distId = intent.getIntExtra(AreasListActivity.EXTRA_ID,0);
		
		areaTextView = (TextView) findViewById(R.id.areaTextView);
		
		Log.d(LOG_TAG, "Extra message is " + strText);
		
		areaListView = (ListView) findViewById(R.id.areaListView);

		distClinics = getResources().getStringArray(ID_DIST[distId]);
        Log.d(Params.LOG_TAG,"Number of - "+distClinics.length);

		distClinicsNames = shortArray(distClinics);
        areaTextView.setText(strText);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,distClinicsNames);
		areaListView.setAdapter(arrayAdapter);
		/*//формуємо масив назв закладів по конкретній області, для створення меню з них
		String[] columns = null;
		String selection = null;
		String[] selectionArgs = null;
				
		//лічильник для створення масиву для назв, 
		//що будуть використовуватись для створення меню
		menuArrNumber = 0;
				
		// курсор
		Cursor c = null;
		
		//фільтр для показу певної інфи (по області)
		selection = "area = ?";
		selectionArgs = new String[] { strText };
		columns = new String[] { "name" };
		
		c = db.query("cancerorg", columns, selection, selectionArgs, null, null, null);
		if (c != null) {
			if (c.moveToFirst()) {
												
				do {
					menuArrNumber++;
					
				} while (c.moveToNext());
				Log.d(LOG_TAG, "Number of rows is " + String.valueOf(menuArrNumber));
			};
			if (c.moveToFirst()) {
												
				String[] menuArr = new String[menuArrNumber];
				menuArrCounter = 0;
				do {										
					for (String cn : c.getColumnNames()) {
						menuArr[menuArrCounter] = c.getString(c.getColumnIndex(cn));
					}
					Log.d(LOG_TAG, menuArr[menuArrCounter] + menuArrCounter);
					menuArrCounter++;
				} while (c.moveToNext());
				
				// маємо масив menuArr типу string зі значеннями name для подальшого використання 
				// в areaListView	
				
				// оголошуємо ArrayAdapter "adapt" типу string, вказуємо параметри
				// зовнішнього вигляду кожного пункту menuList. Вони описані в файлі
				// res/layout/menu_item.xml.
				// І передаємо значення з масиву menuArr.
				ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.menu_item, menuArr);
				Log.d(LOG_TAG, "First element of array is " + menuArr[0]);
				
				areaListView.setAdapter(adapt);
				
			}
									
		} else
			Log.d(LOG_TAG, "Cursor is null");
		
		//dbHelper.close();

		
		// onClickListener areaListView*/
		areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
				TextView textView = (TextView)  itemClicked.findViewById(android.R.id.text1);
				String strText = textView.getText().toString();
				Log.d(LOG_TAG, "Menu item " + strText + " was clicked! position - "+id);
				Intent intentToDetail = new Intent(AreaActivity.this,AreaDetailActivity.class);
				intentToDetail.putExtra(AreaActivity.EXTRA_DIST_ID,distId);
				intentToDetail.putExtra(AreaActivity.EXTRA_DIST_POSITION,id);
				startActivity(intentToDetail);
				//Intent intent = new Intent(AreaActivity.this, CancerOrganizationInfo.class);
				//intent.putExtra(EXTRA_MESSAGE, strText);
				//startActivity(intent);
			}
		}); // кінець прослуховувача натискань на пункти меню
	}

	private String[] shortArray(String[] distClinics) {
		String[] res = new String[distClinics.length];
		for (int i=0; i<distClinics.length; i++){
			res[i]=distClinics[i].split("\\n")[0];
		}
		return res;
	}
}