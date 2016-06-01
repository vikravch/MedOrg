package com.example.medorg;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;

public class Standard_quest_from_group_Activity extends BaseActivity {

	MO_io_class MO_io;
	String [] FileNames_List;
	String [] questions;
	ListView lvMain;
	int N_grp;

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard_quest_from_group);
		
		LogMO.i("Standard_quest_from_group_Activity","В \"onCreate()\"");
		
		
		Intent intent = getIntent();
		/*
		 					 intent.putExtra("groups_name", strText);
							 intent.putExtra("file_name", FileNames_List[position]);
							 intent.putExtra("Id", Files_Id[position]);
							 intent.putExtra("size_of_grp", size_of_grp[position]);
							 intent.putExtra("N_grp", position);	// Номер группы (используется в имени файла)
		 */
		int 	size_of_grp = intent.getIntExtra("size_of_grp",0);
		int 	id = intent.getIntExtra("Id",0);
//		String 	file_Name = intent.getStringExtra("file_name");	// Не используется, т.к. известен Files_Id
		String 	groups_name = intent.getStringExtra("groups_name");
				N_grp = intent.getIntExtra("N_grp",0);		// Номер группы (используется в именах файлов ответов на вопросы)
		
		TextView e_txt = (TextView) findViewById(R.id.g_standard_quest_to_doct);
		e_txt.setText(groups_name);
		
		showMenu(id, size_of_grp);
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

	protected void showMenu(int file_id, int size_of_grp)
	{
		
		// MO_io =  new MO_io_class();
		
		questions = new String[size_of_grp]; // Массив вопросов
		int num;
		for ( num=0; num<questions.length; num++)
				questions[num] = num + ".";
		
		InputStream iFile = getResources().openRawResource(file_id);
		byte b[] = new byte[3500];
		int s;
		try {
				s = iFile.read(b);
				String str = new String(b,0,s);
				// str = s + " " + str;
				s = str.indexOf('\n');
				num=0;
				while ( s > 0 )
				{
					// str = str + ' ' + s;
					LogMO.i("Из файла вопросов: ", str.substring(0, s) + ' ' + s + ' ' + str.length() + ' ' + "num=" + num);
					if ( str.length() > s ) 
						{
							questions[num++] = str.substring(0,s);
							str = str.substring(s+1);
						}
					s = str.indexOf('\n');
				}
				
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

/*		
		FileNames_List = MO_io.Get_FileNames_List("MO_QO", ".qst");
		
		
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
			    	  questions[i] = questions[i] +  ( (questions[i].length()>0) ? ("\n" + str) : str);
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
*/
		// находим список
		LogMO.i("showMenu() ", "находим список");
	    lvMain = (ListView) findViewById(R.id.sqg_listView1);
	    
	    // устанавливаем режим выбора пунктов списка 
	    lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	    
	    // создаем адаптер
	    LogMO.i("showMenu() ", "создаем адаптер");
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Standard_quest_from_group_Activity.this, R.layout.simple_list_item_multiple_choice, questions);
	    
	    // присваиваем адаптер списку
		LogMO.i("showMenu() ", "присваиваем адаптер списку");
	    lvMain.setAdapter(adapter);

	    //
	    //
	    for ( num=0; num<questions.length; num++)
			LogMO.i("showMenu() ", "questions[" + num + "] = " + questions[num]);
    	
	    
		// створюємо прослуховувач натискань на пункти меню
		LogMO.i("showMenu() ", "створюємо прослуховувач натискань на пункти меню");
	    lvMain.setOnItemClickListener
		(
				new AdapterView.OnItemClickListener()
				{
					public void onItemClick( AdapterView<?> parent, View itemClicked, int position, long id )
					{
						TextView textView = (TextView) itemClicked;
						String strText = textView.getText().toString();
						
						LogMO.i("Выбрано: id, position", id+", "+position+" -> "+strText);
												
						// запускаємо нову актівіті
						// з даними відповідного пункту меню
							 //itemClicked.setBackgroundResource(R.color.clr2); // Видалити
							 /*
							 Intent intent = new Intent(Your_questions_Activity.this, Your_quest_EDIT.class); 
							 intent.putExtra("file_name", FileNames_List[position]);
							 intent.putExtra("quest", questions[position]);
							 intent.putExtra("path", MO_io.filePath);
							 startActivity(intent);
							 */
					}
				}
		); // кінець прослуховувача натискань на пункти меню
		
	//	Toast.makeText(Standard_quest_Activity.this, "Оберіть питання зі списку", Toast.LENGTH_LONG).show();
	
		LogMO.i("showMenu() ", "  Конец showMenu()");

	}
/////////////////////////////////////////////////////////////////////////////////////

public void onClick(View view)  // Обработчик
{
	 SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();  //  Все вопросы, выбор которых изменялся
	 int checked_number = 0;
	 try{
	      switch (view.getId())	// Кнопка одна: switch можно убрать
	      {
	      	case R.id.btnGoToEdit:
	      		LogMO.i("Кнопка нажата", "Выбранные элементы: ");
	      	    for (int i = 0; i < sbArray.size(); i++)
	      	    {
	      	      int key = sbArray.keyAt(i);
	      	      if (sbArray.get(key))		// Оставшиеся выбранными вопросы
	      	      {
	      	    	  LogMO.i("Вопрос: i=" + i, "key=" + key + ' ' +questions[key]);
	      	    	  checked_number++;
	      	      }
	      	    }
	      		break;
	      	case 0:
	      		break;
	      }
	    }	catch ( Exception e) 
	    	{
	    		e.printStackTrace();
	    	}
	 if ( checked_number == 0 ) 
		 {
		 	Toast.makeText(getApplicationContext(), "Оберіть запитання!" ,Toast.LENGTH_LONG).show();
		 	return;
		 }
	 
	 String  Checked_quests[];
	 Checked_quests = new String[checked_number]; // Массив выбранных вопросов
	 int Checked_quest_num[];	 // Массив номеров выбранных вопросов
	 Checked_quest_num = new int[checked_number];
	 int n = 0;
	 for (int i = 0; i < sbArray.size(); i++)
	    {
	      int key = sbArray.keyAt(i);
	      if (sbArray.get(key))		// Оставшиеся выбранными вопросы
	      {
	    	  if( n < checked_number )
	    	  {
	    		  Checked_quests[n] = questions[key];
	    		  Checked_quest_num[n] = key;
	    	  }
	    	  n++;
	      }
	    }
	 
	 Intent intent = new Intent(Standard_quest_from_group_Activity.this, Standard_quest_EDIT_Activity.class); 
	 intent.putExtra("What", "Standard");	  	// Тип вопросов
	 intent.putExtra("Checked_quests", Checked_quests);		// Выбранные вопросы
	 intent.putExtra("N_grp", N_grp);  						// Номер группы
	 intent.putExtra("Checked_quest_num", Checked_quest_num);	 // Массив номеров выбранных вопросов
	 startActivity(intent);

}

}
