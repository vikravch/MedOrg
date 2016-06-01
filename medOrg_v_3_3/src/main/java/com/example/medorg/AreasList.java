package com.example.medorg;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.medorg.DBHelper;

public class AreasList extends Activity {
	
	ListView areasListView;
	public final static String EXTRA_MESSAGE = "com.example.organizer_04_dovidnykzaklady.MESSAGE";
	final String LOG_TAG = "myLogs";
	DBHelper dbHelper;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_areas_list);
		areasListView = (ListView) findViewById(R.id.areasListView);
		
		// створюємо об'єкт для створення і керування версіями БД - старий варіант
		//dbHelper = new DBHelper(this);
		
		DBHelper myDBHelper = new DBHelper(this);
		   try {
			   myDBHelper.createDataBase();
		   } catch (IOException ioe) {
			   throw new Error("Unable to create database");
		   }
		   /*//не звичайна практика підключення
		   try {
			   db = myDBHelper.openDataBase();
			   //myDBHelper.openDataBase();
		   } catch(SQLException sqle) {
			   throw sqle;
		   }*/
		
		// оголошуємо масив areasList типу string для подальшого використання його
		// значень в areasListView
		String[] areasList = { "АР Крим", "Вінницька", "Волинська", "Дніпропетровська",
								"Донецька", "Житомирська", "Закарпатська", "Запорізька", 
								"Івано-Франківська", "м. Київ", "Кіровоградська", 
								"Луганська", "Львівська", "Миколаївська", "Одеська", 
								"Полтавська", "Рівненська", "м. Севастополь", "Сумська", 
								"Тернопільська", "Харківська", "Херсонська", "Хмельницька", 
								"Черкаська", "Чернівецька", "Чернігівська" };
		
		// оголошуємо ArrayAdapter "adapt" типу string, вказуємо параметри
		// зовнішнього вигляду
		// кожного пункту areasListView. Вони описані в файлі
		// res/layout/menu_item.xml.
		// І передаємо значення з масиву areasList.
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.menu_item, areasList);
		
		// передаємо значення з adapt в areasListiew, таким чином формуючи
		// безпосередньо пункти в з назвами областей.
		areasListView.setAdapter(adapt);
		
		// створюємо прослуховувач натискань на пункти меню за допомогою
		// setOnItemClickListener
		areasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View itemClicked,
		int position, long id) {
			TextView textView = (TextView) itemClicked;
			String strText = textView.getText().toString();
			
			Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
			Intent intent = new Intent(AreasList.this, Area.class);
			intent.putExtra(EXTRA_MESSAGE, strText);
			startActivity(intent);							
			}
		}); // кінець прослуховувача натискань на пункти меню
		
	}
}