package ua.iepor.itdep.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;
import com.example.medorg.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ua.iepor.itdep.util.AsyncCreateNewFile;
import ua.iepor.itdep.util.Params;

public class NewDoctorActivity extends Activity implements View.OnClickListener {
	
	public EditText e_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_doct);
		findViewById(R.id.btn_Save).setOnClickListener(this);
	}

	
	 public void save_quest(View view)  // Это - обработчик
	 {
		 e_txt = (EditText) findViewById(R.id.editQuest);
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_Save:
     				insertDoctor(e_txt.getText().toString(),"","","");
					break;
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 Toast.makeText(NewDoctorActivity.this, "Збережено", Toast.LENGTH_LONG).show();
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

	private void insertDoctor(String pib, String spec, String about, String phone) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(Params.DOCTOR_PIB,pib);
		contentValues.put(Params.DOCTOR_SPEC,spec);
		contentValues.put(Params.DOCTOR_ABOUT,about);
		contentValues.put(Params.DOCTOR_PHONE, phone);
		this.getContentResolver().insert(Params.CONTENT_URI_DOCTOR, contentValues);
	}

	@Override
	public void onClick(View v) {
		save_quest(v);
	}
}