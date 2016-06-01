package com.example.medorg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class History extends Activity {

  final String LOG_TAG = "myLogs";
  ListView lvMain;

  /** Called when the activity is first created. */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.history);

  /*lvMain = (ListView) findViewById(R.id.lvMain);
  
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names, R.layout.menu_item);
    lvMain.setAdapter(adapter);

    lvMain.setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view,
          int position, long id) {
    	  doSwitch(id);
      }
    });*/

  }
  
  public void onClick(View view)  // Это - обработчик для
	 {
		 try {
		      switch (view.getId())
		      {
		      	case R.id.btn_diag:
		      		startActivity(new Intent(History.this, NewDiagQuest.class));
		      		break;
		      	case R.id.btn_feelings:
		      		startActivity(new Intent(History.this, Sub_Vidchuttja.class));
		      		break;	
		      	case R.id.btn_receipt:
		      		startActivity(new Intent(History.this, Pruzn_likarja.class));
		      		break;
		      	case R.id.btn_profile:
		      		startActivity(new Intent(History.this, Profile.class));
		      		break;
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
		 
	 }
  
  /*public void doSwitch(long id){
	  Intent intent = new Intent();
	  
	 switch ((int) id){
	  case 0:
		  intent = new Intent(this, Diagnoz.class);
		  startActivity(intent);
	  break;  
	  
	  case 1:
		 intent = new Intent(this, Sub_Vidchuttja.class);
		  startActivity(intent);
	  break;
	  
	  case 2:
		  intent = new Intent(this, Pruzn_likarja.class);
		  startActivity(intent);
	  break;
	  }
	  
	  
  }*/
}
