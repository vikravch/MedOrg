package com.example.medorg;


import java.io.IOException;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Glosarij extends Activity {

	ListView wordsListView;
	public final static String EXTRA_MESSAGE = "com.example.organizer_05_dovidnykglosarij.MESSAGE";
	final String LOG_TAG = "myLogs";
	DBHelper dbHelper;
	SQLiteDatabase db;
	TextView wordsTextView;
	int menuArrNumber,
		menuArrCounter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glosarij);
	
		wordsListView = (ListView) findViewById(R.id.wordsListView);
		wordsTextView = (TextView) findViewById(R.id.wordsTextView);
		
		DBHelper myDBHelper = new DBHelper(this);
		
		// створюємо/підключаємось до БД
		try {
			   myDBHelper.createDataBase();
		   } catch (IOException ioe) {
			   throw new Error("Unable to create database");
		   }
		
		try {
		   db = myDBHelper.openDataBase();
		} catch(SQLException sqle) {
		   throw sqle;
		}
		  
		//формуємо масив слів, для створення меню з них
		String[] columns = null;
		//String selection = null;
		//String[] selectionArgs = null;
					
		//лічильник для створення масиву для слів, 
		//що будуть використовуватись для створення меню
		menuArrNumber = 0;
		
		// курсор
		Cursor c = null;
			
		//фільтр для відбору інфи 
		//selection = "area = ?";
		//selectionArgs = new String[] { strText };
		columns = new String[] { "word" };
		
		c = db.query("wordsreference", columns, null, null, null, null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
												
				do {
					menuArrNumber++;
					
				} while (c.moveToNext());
				//wordsTextView.setText(String.valueOf(menuArrNumber));
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
				
				// маємо масив menuArr типу string зі значеннями word для подальшого використання 
				// оголошуємо ArrayAdapter "adapt" типу string, вказуємо параметри
				// зовнішнього вигляду кожного пункту. Вони описані в файлі
				// res/layout/menu_item.xml.
				// І передаємо значення з масиву menuArr.
				ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.menu_item, menuArr);
				Log.d(LOG_TAG, "First element of array is " + menuArr[0]);
				
				wordsListView.setAdapter(adapt);
			}
									
		} else
			Log.d(LOG_TAG, "Cursor is null");
		myDBHelper.close();
		
		wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View itemClicked,
			int position, long id) {
				TextView textView = (TextView) itemClicked;
				String strText = textView.getText().toString();
				Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
				Intent intent = new Intent(Glosarij.this, WordDescription.class);
				intent.putExtra(EXTRA_MESSAGE, strText);
				startActivity(intent);
				
			}
		}); // кінець прослуховувача натискань на пункти меню
	}
	
	
	
}
