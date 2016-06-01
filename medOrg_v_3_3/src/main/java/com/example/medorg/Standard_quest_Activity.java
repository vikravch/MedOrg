package com.example.medorg;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;

public class Standard_quest_Activity extends BaseActivity {

	// MO_io_class MO_io;
	String [] groups_names;
	String [] FileNames_List = {"standard_quest_group_1.txt", "standard_quest_group_2.txt", "standard_quest_group_3.txt",
								"standard_quest_group_4.txt", "standard_quest_group_5.txt", "standard_quest_group_6.txt",
								"standard_quest_group_7.txt", "standard_quest_group_8.txt", "standard_quest_group_9.txt",
								"standard_quest_group_10.txt","standard_quest_group_11.txt","standard_quest_group_12.txt",
								"standard_quest_group_23.txt"};
	int [] Files_Id = {	R.raw.standard_quest_group_1, R.raw.standard_quest_group_2, R.raw.standard_quest_group_3,
						R.raw.standard_quest_group_4, R.raw.standard_quest_group_5, R.raw.standard_quest_group_6,
						R.raw.standard_quest_group_7, R.raw.standard_quest_group_8, R.raw.standard_quest_group_9,
						R.raw.standard_quest_group_10,R.raw.standard_quest_group_11,R.raw.standard_quest_group_12,
						R.raw.standard_quest_group_13 };
	int [] size_of_grp = {11, // 1 группа 
						14, // 2 группа
						3,  // 3 группа
						19, // 4 группа
						19, // 5 группа
						12, // 6 группа
						14, // 7 группа
						14, // 8 группа
						8,  // 9 группа
						5,  // 10 группа
						11, // 11 группа
						7,  // 12 группа
						12  // 13 группа
						};
	
/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acvtivity_standard_quest);
		
		LogMO.i("Standard_quest_Activity","В \"onCreate()\"");
		
		showMenu();
/*
		
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
		
		// MO_io =  new MO_io_class();
		
		groups_names = new String[13]; // Массив имен групп
		int num;
		for ( num=0; num<groups_names.length; num++)  // Первоначальное заполнение
			groups_names[num] = (num+1) + ".";
		
		InputStream iFile = getResources().openRawResource(R.raw.standard_quest_groups);
		byte b[] = new byte[1850];
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
					LogMO.i("Из файла групп вопросов: ", str.substring(0, s) + ' ' + s + ' ' + str.length() + ' ' + "num=" + num);
					if ( str.length() > s ) 
						{
						groups_names[num++] = str.substring(0,s);
							str = str.substring(s+1)+'\n';
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
	    ListView lvMain = (ListView) findViewById(R.id.sq_listView1);
	    // создаем адаптер
	    
		LogMO.i("showMenu() ", "создаем адаптер");
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Standard_quest_Activity.this, R.layout.menu3_item, groups_names);
	    
	    // присваиваем адаптер списку
		LogMO.i("showMenu() ", "присваиваем адаптер списку");
	    lvMain.setAdapter(adapter);

	    //
	    //
	    for ( num=0; num<groups_names.length; num++)
			LogMO.i("showMenu() ", "groups_names[" + num + "] = " + groups_names[num]);
    	
	    
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
						LogMO.i("Имя файла с вопросами группы", FileNames_List[position]);
												
						// запускаємо нову актівіті
						// з даними відповідного пункту меню
							 //itemClicked.setBackgroundResource(R.color.clr2); // Видалити
							
							 Intent intent = new Intent(Standard_quest_Activity.this, Standard_quest_from_group_Activity.class); 
							 //intent.putExtra("path", MO_io.filePath);
							 intent.putExtra("groups_name", strText);
//							 intent.putExtra("file_name", FileNames_List[position]);
							 intent.putExtra("Id", Files_Id[position]);
							 intent.putExtra("size_of_grp", size_of_grp[position]);
							 intent.putExtra("N_grp", position);	// Номер группы (используется в имени файла)
							 startActivity(intent);
							 /**/
					}
				}
		); // кінець прослуховувача натискань на пункти меню
		
	//	Toast.makeText(Standard_quest_Activity.this, "Оберіть питання зі списку", Toast.LENGTH_LONG).show();
	
		LogMO.i("showMenu() ", "  Конец showMenu()");

	}
/////////////////////////////////////////////////////////////////////////////////////
	 public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	 {
		 try {
		      switch (view.getId())
		      {
		      	case 0: // R.id.new_quest:
		      		// startActivity(new Intent(Standard_quest_Activity.this, Your_quest_NEW_Activity.class));
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
	 }
////////////////////////////////////////////////////////////////////////////////////
}
