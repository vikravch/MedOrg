package ua.com.kistudio.medorg_v2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class QuestionsToDoctorActivity extends BaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d(Params.LOG_TAG,"Created");
		setContentView(R.layout.activity_questions_to_doctor);
		findViewById(R.id.btnStandartQuestToDoct).setOnClickListener(this);
		findViewById(R.id.btnYourQuestToDoct).setOnClickListener(this);
	}

	 public void onClick(View view)
	 {
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btnStandartQuestToDoct:
		      		startActivity(new Intent(this, StandardQuestActivity.class));
		      		break;
		      	case R.id.btnYourQuestToDoct:
		      		startActivity(new Intent(this, YourQuestionsActivity.class));
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 
	 }
}
