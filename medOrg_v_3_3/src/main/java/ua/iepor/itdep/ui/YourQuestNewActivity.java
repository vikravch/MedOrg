package ua.iepor.itdep.ui;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;
import com.example.medorg.BaseActivity;
import com.example.medorg.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ua.iepor.itdep.util.Params;

public class YourQuestNewActivity extends BaseActivity {

	public EditText e_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_quest__new);
	}

	
	 public void Save_quest(View view)  // Это - обработчик
	 {
		 e_txt = (EditText) findViewById(R.id.editQuest);
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_Save:
						ContentValues contentValues = new ContentValues();
						contentValues.put(Params.QUESTION_TEXT,e_txt.getText().toString());
						contentValues.put(Params.QUESTION_ANSWER,"");
						getContentResolver().insert(Params.CONTENT_URI_QUESTIONS,contentValues);
					break;
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}

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
