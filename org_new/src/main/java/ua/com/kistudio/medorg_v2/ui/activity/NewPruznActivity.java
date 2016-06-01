package ua.com.kistudio.medorg_v2.ui.activity;


import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class NewPruznActivity extends BaseActivity {

	public EditText e_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_pruzn);
	}

	
	 public void Save_quest(View view)  // Это - обработчик
	 {
		 e_txt = (EditText) findViewById(R.id.editQuest);

		 ContentValues contentValues = new ContentValues();
		 contentValues.put(Params.PRUZN_TEXT,e_txt.getText().toString());
		 getContentResolver().insert(Params.CONTENT_URI_PRUZN,contentValues);

		 Toast.makeText(NewPruznActivity.this, "Збережено", Toast.LENGTH_LONG).show();
	 } 
	
	  @Override
	  protected void onPause() {
	    super.onPause();
	    e_txt = (EditText) findViewById(R.id.editQuest);
	  }

	  @Override
	  protected void onStop() {
	    super.onStop();
	  }
	   
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	  }
}
