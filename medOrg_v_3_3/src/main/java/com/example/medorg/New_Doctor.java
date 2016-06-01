package com.example.medorg;

import android.app.Activity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;

public class New_Doctor extends Activity {
	
	public EditText e_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_doct);
	}

	
	 public void Save_quest(View view)  // Это - обработчик
	 {
		 e_txt = (EditText) findViewById(R.id.editQuest);
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_Save:
		      		MO_io_class io = new MO_io_class("]_", "]_");
		      		LogMO.i("Путь к файлам", io.filePath);
		      		String f_Name = "mdc" + String.valueOf(System.currentTimeMillis()) + ".dct";
		      		LogMO.i("Новое имя", f_Name);
		      		
		      		File qstFile = new File(io.filePath, f_Name);
		      	    try {
		      	      // открываем поток для записи
		      	      BufferedWriter bw = new BufferedWriter(new FileWriter(qstFile));
		      	      // пишем данные
		      	      bw.write(e_txt.getText().toString());
		      	      // закрываем поток
		      	      bw.close();
		      	      LogMO.i("Файл записан: " , qstFile.getAbsolutePath());
		      	    } catch (IOException e) {
		      	      e.printStackTrace();
		      	    }
		      		
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 Toast.makeText(New_Doctor.this, "Збережено", Toast.LENGTH_LONG).show();

		 
	 } 
	
	  @Override
	  protected void onPause() {
	    super.onPause();
	    e_txt = (EditText) findViewById(R.id.editQuest);
	    LogMO.d("1", "Your_quest_NEW_Activity: onPause()." + e_txt.getText().toString());
	  }

	  @Override
	  protected void onStop() {
	    super.onStop();
	    LogMO.d("2", "Your_quest_NEW_Activity: onStop()");
	  }
	   
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	    LogMO.d("3", "Your_quest_NEW_Activity: onDestroy()");
	  }

}