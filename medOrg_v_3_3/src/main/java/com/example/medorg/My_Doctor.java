package com.example.medorg;

import android.app.Activity;
import android.os.Bundle;

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

public class My_Doctor extends Activity implements OnClickListener {
	
	MO_io_class MO_io;
	String [] FileNames_List;
	String [] questions;
	Button btn1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor);
		
		btn1 = (Button) findViewById(R.id.new_diag);
		btn1.setOnClickListener(this);
		
		LogMO.i("Your_questions_Activity","В \"onCreate()\"");
		
		showMenu();
	    }
	

	
	protected void showMenu()
	{
		MedOrg id1 = new MedOrg();
		MO_io = new MO_io_class("mdc", ".dct");
		
		getQuestions(MO_io);
		//questions = id1.questions;
		//FileNames_List = id1.FileNames_List;
		
		if(questions.length == 0)
		{
			questions = new String[1]; // Массив вопросов
			questions[0] = "Місце для першого лікаря";
			FileNames_List = new String[1]; // Массив имен файлов
			FileNames_List[0] = "mdc" + String.valueOf(System.currentTimeMillis()) + ".dct";
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
							 
							 Intent intent = new Intent(My_Doctor.this, Edit_Doct.class); 
							 intent.putExtra("file_name", FileNames_List[position]);
							 //	else intent.putExtra("file_name", "Нет такого файла");
							 intent.putExtra("quest", questions[position]);
							 intent.putExtra("path", MO_io.filePath);
							 startActivity(intent);
					}
				}
		); // кінець прослуховувача натискань на пункти меню
		
		Toast.makeText(My_Doctor.this, "Оберіть раніше введого лікаря для перегляду або редагування. Якщо потрібно ввести нового, натисніть Додати.", Toast.LENGTH_LONG).show();
	
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId())
		{
		case R.id.new_diag:
			Intent intent1 = new Intent(this, New_Doctor.class);
			startActivity(intent1);
		break;	
		}
		
	}
	
	public void getQuestions(MO_io_class MO_io) 
	{

		FileNames_List = MO_io.f_array; // Get_FileNames_List("MO_QO", ".qst");
										// // Массив строк

		questions = new String[FileNames_List.length]; // Массив вопросов
		int pt = 0;

		for (int i = 0; i < FileNames_List.length; i++) {
			questions[i] = "";
			try {
				// открываем поток для чтения
				File f1 = new File(MO_io.filePath + "/" + FileNames_List[i]);
				BufferedReader br = new BufferedReader(new FileReader(f1));
				String str = "";
				/*pt = i + 1;
				questions[i] = "" + pt + ". ";*/
				// читаем содержимое
				while ((str = br.readLine()) != null) {
					LogMO.i("Из файла " + FileNames_List[i], str);
					questions[i] = questions[i]
							+ ((questions[i].length() > 0) ? ("" + str) : str);
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String[] res;
		for (int k=0; k<questions.length; k++){
			res = questions[k].split("#");
			questions[k] = res[0];
		}
	}
}