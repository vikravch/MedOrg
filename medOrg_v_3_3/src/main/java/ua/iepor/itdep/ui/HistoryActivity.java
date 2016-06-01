package ua.iepor.itdep.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.medorg.NewDiagQuest;
import com.example.medorg.Profile;
import com.example.medorg.Pruzn_likarja;
import com.example.medorg.R;
import com.example.medorg.Sub_Vidchuttja;

public class HistoryActivity extends Activity {

  final String LOG_TAG = "myLogs";
  ListView lvMain;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.history);
  }
  
  public void onClick(View view)  // Это - обработчик для
	 {
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_diag:
		      		startActivity(new Intent(this, DiagnozActivity.class));
		      		break;
		      	case R.id.btn_feelings:
		      		startActivity(new Intent(this, NewDiagQuest.class));
		      		break;	
		      	case R.id.btn_receipt:
		      		startActivity(new Intent(this, PruznachennjaLikarjaActivity.class));
		      		break;
		      	case R.id.btn_profile:
		      		startActivity(new Intent(this, ProfileActivity.class));
		      		break;
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
	 }
}
