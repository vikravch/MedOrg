package com.example.medorg;

import com.example.medorg.DBHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WordDescriptionFromGuide extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";
	DBHelper dbHelper;
	SQLiteDatabase db;
	final String LOG_TAG = "myLogs";
	TextView wordTextView, 
			 wordDescriptionTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word_description);
		
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		wordDescriptionTextView = (TextView) findViewById(R.id.wordDescriptionTextView);
		
		Intent intent = getIntent();
		String strText = intent.getStringExtra(PartOneActivity.EXTRA_MESSAGE);
		wordTextView.setText(strText);
		
		DBHelper myDBHelper = new DBHelper(this);
		
		
		
		   try {
			   db = myDBHelper.openDataBase();
			   
		   } catch(SQLException sqle) {
			   throw sqle;
		   }
		   
		Cursor c = null;
		
		String selection = "word = ?";
		String[] selectionArgs = new String[] { strText };
		
		//показати всю інфу по заданому слову
		c = db.query("wordsreference", null, selection, selectionArgs, null, null, null);
		// ставимо позицію курсора на перший рядок вибірки
		// якщо у вибірці нема записів, вернеться false
		if (c.moveToFirst()) {
			// визначаємо номера стовпчиків по імені у вибірці
			int idColIndex = c.getColumnIndex("_id");
			int wordColIndex = c.getColumnIndex("word"); 
			int explainColIndex = c.getColumnIndex("explain"); wordDescriptionTextView.setText(c.getString(explainColIndex));
			
				do {
					// отримуємо значення по номерам стовпчиків і пишемо все в лог
					Log.d(LOG_TAG, "ID: " + c.getInt(idColIndex) +
							",\nword: " + c.getString(wordColIndex) + 
							",\nexplain: " + c.getString(explainColIndex));
				// перехід на наступний рядок
				// а якщо наступного нема (поточна - остання), то false -
				// виходимо з циклу
				} while (c.moveToNext());
			} else
				Log.d(LOG_TAG, "0 rows");
		myDBHelper.close();
	}
	
	
	
	

}
