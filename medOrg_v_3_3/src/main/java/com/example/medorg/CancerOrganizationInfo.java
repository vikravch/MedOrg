package com.example.medorg;

import com.example.medorg.DBHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CancerOrganizationInfo extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";
	DBHelper dbHelper;
	SQLiteDatabase db;
	final String LOG_TAG = "myLogs";
	TextView cancerOrganizationName, cancerOrganizationAdress, cancerOrganizationPhoneNumber, cancerOrganizationSite;
	LinearLayout linearLayoutClickable;
	int visibility = 0;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancer_organization_info);
				
		Intent intent = getIntent();
		String strText = intent.getStringExtra(AreasList.EXTRA_MESSAGE);
		cancerOrganizationName = (TextView) findViewById(R.id.cancerOrganizationName);
		cancerOrganizationAdress = (TextView) findViewById(R.id.cancerOrganizationAdress);
		cancerOrganizationPhoneNumber = (TextView) findViewById(R.id.cancerOrganizationPhoneNumber);
		cancerOrganizationSite = (TextView) findViewById(R.id.cancerOrganizationSite);
		linearLayoutClickable = (LinearLayout) findViewById(R.id.linearLayoutClickable);
		
		
		
		Log.d(LOG_TAG, "Extra message is " + strText);
		
		//пошук в БД рядка по заданому імені
		
		//dbHelper = new DBHelper(this);
		DBHelper myDBHelper = new DBHelper(this);
		//db = dbHelper.getWritableDatabase();
		
		//не звичайна практика підключення
		   try {
			   db = myDBHelper.openDataBase();
			   //myDBHelper.openDataBase();
		   } catch(SQLException sqle) {
			   throw sqle;
		   }
		   
		Cursor c = null;
		
		String selection = "name = ?";
		String[] selectionArgs = new String[] { strText };
		
		//показати всю інфу по заданому імені установи
		c = db.query("cancerorg", null, selection, selectionArgs, null, null, null);
		// ставимо позицію курсора на першу строчку вибірки
		// якщо у вибірці нема записів, вернеться false
		if (c.moveToFirst()) {
			// визначаємо номера стовпчиків по імені у вибірці
			int idColIndex = c.getColumnIndex("_id");
			int nameColIndex = c.getColumnIndex("name"); cancerOrganizationName.setText(c.getString(nameColIndex));
			int areaColIndex = c.getColumnIndex("area");
			int adressColIndex = c.getColumnIndex("adress"); cancerOrganizationAdress.setText("Адреса: " + c.getString(adressColIndex));
			int phoneColIndex = c.getColumnIndex("phone"); cancerOrganizationPhoneNumber.setText("Телефон: " + c.getString(phoneColIndex));
			int siteColIndex = c.getColumnIndex("site"); cancerOrganizationSite.setText("Сайт: " + c.getString(siteColIndex));
				do {
					// отримуємо значення по номерам стовпчиків і пишемо все в лог
					Log.d(LOG_TAG, "ID: " + c.getInt(idColIndex) +
							",\nname: " + c.getString(nameColIndex) + 
							",\nadress: " + c.getString(adressColIndex) +	
							",\narea: " + c.getString(areaColIndex) + 
							",\nphone: " + c.getString(phoneColIndex) + 
							",\nsite: " + c.getString(siteColIndex));
				// перехід на наступну строчку
				// а якщо наступної нема (поточна - остання), то false -
				// виходимо з циклу
				} while (c.moveToNext());
			} else
				Log.d(LOG_TAG, "0 rows");
		//dbHelper.close();
		myDBHelper.close();
	}
	public void onClick(View v) {
		if (visibility == 0)
			visibility = 8;
		else
			visibility = 0;
		linearLayoutClickable.setVisibility(visibility);
		cancerOrganizationName.setVisibility(visibility);
		cancerOrganizationAdress.setVisibility(visibility);
		cancerOrganizationPhoneNumber.setVisibility(visibility);
		cancerOrganizationSite.setVisibility(visibility);
		
		
		Log.d(LOG_TAG, " --- LL was clicked --- ");
	}
}
