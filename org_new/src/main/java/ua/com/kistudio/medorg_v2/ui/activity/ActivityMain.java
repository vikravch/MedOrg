package ua.com.kistudio.medorg_v2.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.ui.activity.BaseActivity;

public class ActivityMain extends BaseActivity implements OnClickListener {
	
	ImageButton iBtnGuide, iBtnHistory, iBtnQuestion, iBtnDoctor, iBtnHelp, iBtnInfo;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

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

	/*Toast.makeText(ActivityMain.this, "Оберіть бажану дію з меню", Toast.LENGTH_LONG).show();*/

}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
				
			case R.id.iBtnGuide: 
				//startActivity(new Intent(ActivityMain.this, Guide_contents.class));
				//TODO Add Guide activity
				break;
			case R.id.iBtnHistory: 
				startActivity(new Intent(ActivityMain.this, HistoryActivity.class));
				break;
			case R.id.iBtnQuestion: 
				startActivity(new Intent(ActivityMain.this, QuestionsToDoctorActivity.class));

				break;
			case R.id.iBtnDoctor: 
				startActivity(new Intent(ActivityMain.this, DoctorActivity.class));
				break;
			case R.id.iBtnHelp: 
				startActivity(new Intent(ActivityMain.this, Reference.class));
				break;
			case R.id.iBtnInfo: 
				startActivity(new Intent(ActivityMain.this, InfoActivity.class));
				break;
		}
	}


}
