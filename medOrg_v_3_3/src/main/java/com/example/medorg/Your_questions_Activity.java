package com.example.medorg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;

public class Your_questions_Activity extends BaseActivity {

	MO_io_class MO_io;
	String [] FileNames_List;
	String [] questions;
	
/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_questions);
		
		LogMO.i("Your_questions_Activity","В \"onCreate()\"");
		
		showMenu();
		/*
		MO_io =  new MO_io_class();
		
		FileNames_List = MO_io.Get_FileNames_List("MO_QO");
		
		questions = new String[FileNames_List.length]; // Массив вопросов
		
		String [] tmp = {"Перше запитання", "Друге запитання", "Третє запитання", "Четверте запитання", "П'яте запитання",
			"Шосте запитання", "Сьоме запитання", "Восьме запитання", "Дев'яте запитання", "Десяте запитання" };
		
		for( int i=0; i < FileNames_List.length; i++ )
		{
			questions[i] = "";
			try {
			      // открываем поток для чтения
				  File f1 = new File( MO_io.filePath + "/" + FileNames_List[i] );
				  BufferedReader br = new BufferedReader(new FileReader(f1));
			      String str = "";
			      // читаем содержимое
			      while ((str = br.readLine()) != null) {
			    	  LogMO.i("Из файла " + FileNames_List[i], str);
			    	  questions[i] = questions[i] + '\n' + str;
			      }
			    } catch (FileNotFoundException e) {
			      e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		}
		
		if(questions.length == 0)
		{
			questions = tmp;
		}
		
		// находим список
	    ListView lvMain = (ListView) findViewById(R.id.listView1);
	    // создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, questions);
	    // присваиваем адаптер списку
	    lvMain.setAdapter(adapter);
		*/
	}
/////////////////////////////////////////////////////////////////////////////////////
	@Override
	  protected void onRestart() {
	    super.onRestart();
	    showMenu();
	  }
/////////////////////////////////////////////////////////////////////////////////////
	protected void showMenu()
	{
		MO_io =  new MO_io_class("MO_QO", ".qst");
		
		FileNames_List =  MO_io.f_array;   //Get_FileNames_List("MO_QO", ".qst"); // Массив строк
		
		questions = new String[FileNames_List.length]; // Массив вопросов
		/*
		String [] tmp = {"Перше запитання", "Друге запитання", "Третє запитання", "Четверте запитання", "П'яте запитання",
			"Шосте запитання", "Сьоме запитання", "Восьме запитання", "Дев'яте запитання", "Десяте запитання" };
		*/
		for( int i=0; i < FileNames_List.length; i++ )
		{
			questions[i] = "";
			try {
			      // открываем поток для чтения
				  File f1 = new File( MO_io.filePath + "/" + FileNames_List[i] );
				  BufferedReader br = new BufferedReader(new FileReader(f1));
			      String str = "";
			      // читаем содержимое
			      while ((str = br.readLine()) != null) {
			    	  LogMO.i("Из файла " + FileNames_List[i], str);
			    	  questions[i] = questions[i] +  ( (questions[i].length()>0) ? ("\n" + str) : str);
			      }
			      br.close();
			    } catch (FileNotFoundException e) {
			      e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
		}
		
		if(questions.length == 0)
		{
			questions = new String[1]; // Массив вопросов
			questions[0] = "Місце для першого запитання";
			FileNames_List = new String[1]; // Массив имен файлов
			FileNames_List[0] = "MO_QO" + String.valueOf(System.currentTimeMillis()) + ".qst";
      		LogMO.i("Новое (единственное) имя", FileNames_List[0]);			
		}
		
		// находим список
	    ListView lvMain = (ListView) findViewById(R.id.listView1);
	    // создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        R.layout.menu3_item, questions);
	    // присваиваем адаптер списку
	    lvMain.setAdapter(adapter);

		// створюємо прослуховувач натискань на пункти меню за допомогою
		// setOnItemClickListener
	    lvMain.setOnItemClickListener
		(
				new AdapterView.OnItemClickListener()
				{
					public void onItemClick( AdapterView<?> parent, View itemClicked, int position, long id )
					{
						TextView textView = (TextView) itemClicked;
						String strText = textView.getText().toString();
						
						LogMO.i("id, position", id+", "+position+" -> "+strText);
												
						// запускаємо нову актівіті
						// з даними відповідного пункту меню
						if(!true) // (false)
						{
							 itemClicked.setBackgroundResource(R.color.clr2); // Видалити
							 Intent intent = new Intent(Your_questions_Activity.this, Your_quest_EDIT.class); 
							 intent.putExtra("file_name", FileNames_List[position]);
							 intent.putExtra("quest", questions[position]);
							 intent.putExtra("path", MO_io.filePath);
							 startActivity(intent);
						}
						else
						{
							// LogMO.i("You", "Вызваем Standard_quest_EDIT_Activity");
							 Intent intent = new Intent(Your_questions_Activity.this, Standard_quest_EDIT_Activity.class); 
							 intent.putExtra("What", "My");	  					// Тип вопросов
							 intent.putExtra("file_name", FileNames_List);		// Имена файлов
							 intent.putExtra("N_qst", position);  				// Номер имени файла в FileNames_List
							 startActivity(intent);

						}
					}
				}
		); // кінець прослуховувача натискань на пункти меню
		
		Toast.makeText(Your_questions_Activity.this, "Оберіть питання зі списку", Toast.LENGTH_LONG).show();
	/**/
	}
/////////////////////////////////////////////////////////////////////////////////////
	 public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	 {
		 try {
		      switch (view.getId())
		      {
		      	case R.id.new_quest:
		      		startActivity(new Intent(Your_questions_Activity.this, Your_quest_NEW_Activity.class));
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
	 }
////////////////////////////////////////////////////////////////////////////////////
}
