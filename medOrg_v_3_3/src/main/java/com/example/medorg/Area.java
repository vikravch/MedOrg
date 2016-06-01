package com.example.medorg;

import com.example.medorg.DBHelper;

import android.database.SQLException;
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

public class Area extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";
	DBHelper dbHelper;
	SQLiteDatabase db;
	TextView areaTextView;
	final String LOG_TAG = "myLogs";
	ListView areaListView;
	int menuArrNumber, menuArrCounter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area);
		
		Intent intent = getIntent();
		String strText = intent.getStringExtra(EXTRA_MESSAGE);
		
		areaTextView = (TextView) findViewById(R.id.areaTextView);
		
		Log.d(LOG_TAG, "Extra message is " + strText);
		
		areaListView = (ListView) findViewById(R.id.areaListView);
		
		// створюємо об'єкт для створення і керування версіями БД
		//dbHelper = new DBHelper(this);
		DBHelper myDBHelper = new DBHelper(this);
		
		// підключаємось до БД
		//db = dbHelper.getWritableDatabase();
		
		//не звичайна практика підключення
		   try {
			   db = myDBHelper.openDataBase();
			   //myDBHelper.openDataBase();
		   } catch(SQLException sqle) {
			   throw sqle;
		   }
		
		//формуємо масив назв закладів по конкретній області, для створення меню з них
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
		myDBHelper.close();
		
		// onClickListener areaListView
		areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
				TextView textView = (TextView) itemClicked;
				String strText = textView.getText().toString();
				Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
				Intent intent = new Intent(Area.this, CancerOrganizationInfo.class);
				intent.putExtra(EXTRA_MESSAGE, strText);
				startActivity(intent);
			}
		}); // кінець прослуховувача натискань на пункти меню
	}
}