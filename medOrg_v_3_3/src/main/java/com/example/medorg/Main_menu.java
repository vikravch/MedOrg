package com.example.medorg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main_menu extends BaseActivity implements OnClickListener {
	
	ImageButton iBtnGuide, iBtnHistory, iBtnQuestion, iBtnDoctor, iBtnHelp, iBtnInfo;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		Log.i("Главное меню","В \"onCreate()\" методе");
		
		iBtnGuide = (ImageButton) findViewById(R.id.iBtnGuide);
		iBtnHistory = (ImageButton) findViewById(R.id.iBtnHistory);
		iBtnQuestion = (ImageButton) findViewById(R.id.iBtnQuestion);
		iBtnDoctor = (ImageButton) findViewById(R.id.iBtnDoctor);
		iBtnHelp = (ImageButton) findViewById(R.id.iBtnHelp);
		iBtnInfo = (ImageButton) findViewById(R.id.iBtnInfo);
		
		iBtnGuide.setOnClickListener(this);
		iBtnHistory.setOnClickListener(this);
		iBtnQuestion.setOnClickListener(this);
		iBtnDoctor.setOnClickListener(this);
		iBtnHelp.setOnClickListener(this);
		iBtnInfo.setOnClickListener(this);

	/*Toast.makeText(Main_menu.this, "Оберіть бажану дію з меню", Toast.LENGTH_LONG).show();*/

}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
				
			case R.id.iBtnGuide: 
				startActivity(new Intent(Main_menu.this, Guide_contents.class));
				break;
			case R.id.iBtnHistory: 
				startActivity(new Intent(Main_menu.this, History.class));
				break;
			case R.id.iBtnQuestion: 
				startActivity(new Intent(Main_menu.this, Questions_to_doctor_Activity.class));
				break;
			case R.id.iBtnDoctor: 
				startActivity(new Intent(Main_menu.this, My_Doctor.class));
				break;
			case R.id.iBtnHelp: 
				startActivity(new Intent(Main_menu.this, Reference.class));
				break;
			case R.id.iBtnInfo: 
				startActivity(new Intent(Main_menu.this, InfoActivity.class));
				break;
		}
	}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	/*
	 * getMenuInflater().inflate(R.menu.main, menu); return true;
	 */

	return super.onCreateOptionsMenu(menu);
}


}
