package com.example.medorg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Questions_to_doctor_Activity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_to_doctor);
		// Show the Up button in the action bar.
	}
	 public void onClick(View view)  // Это - обработчик для
	 {
		 try {
		      /*switch (view.getId())
		      {
		      	case R.id.btn_standard_quest_to_doct:
		      		startActivity(new Intent(Questions_to_doctor_Activity.this, Standard_quest_Activity.class));
		      		break;
		      	case R.id.btn_your_quest_to_doct:
		      		startActivity(new Intent(Questions_to_doctor_Activity.this, Your_questions_Activity.class));
		      		break;		      
		      }*/
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 
	 } 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return super.onCreateOptionsMenu(menu);
	}

}
