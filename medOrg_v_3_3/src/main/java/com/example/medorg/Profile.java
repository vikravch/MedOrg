package com.example.medorg;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity implements OnClickListener {
	
	SharedPreferences sp;
	EditText etMail, etPIB, etTel, etAddr;
	public final String MAIL = "mail";
	public final String PIB = "pib";
	public final String PHONE = "phone";
	public final String ADRESS = "adress";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		etMail =(EditText) findViewById(R.id.et_mail);
		etPIB = (EditText) findViewById(R.id.et_pib);
		etTel = (EditText) findViewById(R.id.et_phone);
		etAddr = (EditText) findViewById(R.id.et_adress);
		
		etMail.setText(loadText(MAIL), TextView.BufferType.EDITABLE);
		etPIB.setText(loadText(PIB), TextView.BufferType.EDITABLE);
		etTel.setText(loadText(PHONE), TextView.BufferType.EDITABLE);
		etAddr.setText(loadText(ADRESS),TextView.BufferType.EDITABLE);
		
		((Button) findViewById(R.id.btn_save_profile)).setOnClickListener(this);
	}
	
	/*protected void onRestart(){
		super.onRestart();
		etMail =(EditText) findViewById(R.id.et_mail);
		etPIB = (EditText) findViewById(R.id.et_pib);
		etTel = (EditText) findViewById(R.id.et_phone);
		
		etMail.setText(loadText(MAIL));
		etPIB.setText(loadText(PIB));
		etTel.setText(loadText(PHONE));
	}
	
	protected void onStart(){
		super.onStart();
		etMail =(EditText) findViewById(R.id.et_mail);
		etPIB = (EditText) findViewById(R.id.et_pib);
		etTel = (EditText) findViewById(R.id.et_phone);
		
		etMail.setText(loadText(MAIL));
		etPIB.setText(loadText(PIB));
		etTel.setText(loadText(PHONE));
	}*/
	
	void saveText(String key, String inpStr){
		sp = getPreferences(this.MODE_WORLD_READABLE);
		Editor ed = sp.edit();
		ed.putString(key, inpStr);  
		ed.commit();
		//Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	}
	
	String loadText(String key){
		sp = getPreferences(this.MODE_WORLD_READABLE);
		String res=sp.getString(key, "");
		//Toast.makeText(this, "load: "+res, Toast.LENGTH_SHORT).show();
		return res;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_save_profile:
			String str = (String) etMail.getText().toString();
			saveText(MAIL,str);
			Log.d("Org","Save1 "+str);
			str = (String) etPIB.getText().toString();
			saveText(PIB,str);
			Log.d("Org","Save2 "+str);
			str = (String) etTel.getText().toString();
			saveText(PHONE,str);
			str = (String) etAddr.getText().toString();
			saveText(ADRESS,str);
			Log.d("Org","Save3 "+str);
			Toast.makeText(this, "Збережено", Toast.LENGTH_SHORT).show();
			ApplMedOrg appl= new ApplMedOrg();
			appl =(ApplMedOrg) this.getApplication();
			appl.mail = etMail.getText().toString();
			
			finish();
			break;
		}
	}
	
	
}
