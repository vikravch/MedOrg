package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ua.com.kistudio.medorg_v2.R;

public class Reference extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reference);
	}

	public void onClick(View view)  // Это - обработчик для
	 {
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_words:
		      		//startActivity(new Intent(Reference.this, Glosarij.class));
		      		break;
		      	case R.id.btn_clinics:
		      		//startActivity(new Intent(Reference.this, AreasList.class));
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 
	 } 

}
