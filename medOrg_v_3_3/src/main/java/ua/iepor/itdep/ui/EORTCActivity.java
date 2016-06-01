package ua.iepor.itdep.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.medorg.NewDiagQuest;
import com.example.medorg.R;
import com.example.medorg.Sub_Vidchuttja;

public class EORTCActivity extends Activity implements View.OnClickListener {

  final String LOG_TAG = "myLogs";
  ListView lvMain;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.eortc_activity);
	findViewById(R.id.btnC30).setOnClickListener(this);
	findViewById(R.id.btnBR23).setOnClickListener(this);
  }
  
  public void onClick(View view)  // Это - обработчик для
	 {
		 try {
		      switch (view.getId())
		      {
				  case R.id.btnC30:
						Intent intent = new Intent(this,SubVidchuttjaActivity.class);
					    intent.putExtra("type",0);
					  	startActivity(intent);
					  break;
				  case R.id.btnBR23:
					  	Intent intent1 = new Intent(this,SubVidchuttjaActivity.class);
					  	intent1.putExtra("type",1);
					  	startActivity(intent1);
					  break;
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
	 }
}
