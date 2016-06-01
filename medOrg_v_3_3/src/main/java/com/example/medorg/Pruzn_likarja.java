package com.example.medorg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;


public class Pruzn_likarja extends BaseActivity implements OnClickListener {

	MO_io_class MO_io;
	String [] FileNames_List;
	String [] questions;
	Button btn1;
	
/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pruzn_likarja);
		
		btn1 = (Button) findViewById(R.id.new_diag);
		btn1.setOnClickListener(this);
		
		LogMO.i("Your_questions_Activity","В \"onCreate()\"");
		
		showMenu();
		
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
		MedOrg id1 = new MedOrg();
		MO_io = new MO_io_class("bb", ".prz");
		
		id1.getQuestions(MO_io);
		questions = id1.questions;
		FileNames_List = id1.FileNames_List;
		
		if(questions.length == 0)
		{
			questions = new String[1]; // Массив вопросов
			questions[0] = "Місце для першого запитання";
			FileNames_List = new String[1]; // Массив имен файлов
			FileNames_List[0] = "bb" + String.valueOf(System.currentTimeMillis()) + ".prz";
      		LogMO.i("Новое (единственное) имя", FileNames_List[0]);			
		}
		
		// находим список
	    ListView lvMain = (ListView) findViewById(R.id.lvMain);
	    // создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questions);
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
							 
							 Intent intent = new Intent(Pruzn_likarja.this, Edit_Pruzb.class); 
							 intent.putExtra("file_name", FileNames_List[position]);
							 //	else intent.putExtra("file_name", "Нет такого файла");
							 intent.putExtra("quest", questions[position]);
							 intent.putExtra("path", MO_io.filePath);
							 startActivity(intent);
					}
				}
		); // кінець прослуховувача натискань на пункти меню
		
		Toast.makeText(Pruzn_likarja.this, "Оберіть раніше введене призначення лікаря для перегляду або редагування. Якщо потрібно ввести нове, натисніть Додати призначення.", Toast.LENGTH_LONG).show();
	/**/
	}
/////////////////////////////////////////////////////////////////////////////////////
	 public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	 {
		 switch (view.getId())
			{
			case R.id.new_diag:
				Intent intent1 = new Intent(this, New_Pruzn.class);
				startActivity(intent1);
			break;	
			}
	 }
////////////////////////////////////////////////////////////////////////////////////
}

