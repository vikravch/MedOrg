package ua.iepor.itdep.ui;

import android.app.Activity;
import android.content.Context;
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

import com.example.medorg.ApplMedOrg;
import com.example.medorg.R;

import ua.iepor.itdep.util.Params;

public class ProfileActivity extends Activity implements OnClickListener {
	
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
		
		findViewById(R.id.btn_save_profile).setOnClickListener(this);
	}
	
	void saveText(String key, String inpStr){
		final String eulaKey = key;
		Context mContext = getApplicationContext();
		sp = mContext.getSharedPreferences(Params.PREFERENCE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(eulaKey, inpStr);
		editor.commit();
	}
	
	String loadText(String key){
		sp = getSharedPreferences(Params.PREFERENCE_NAME,Context.MODE_PRIVATE);
		String res=sp.getString(key, "");
		return res;
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.btn_save_profile:

			saveText(MAIL,etMail.getText().toString());
			saveText(PIB,etPIB.getText().toString());
			saveText(PHONE, etTel.getText().toString());
			saveText(ADRESS, etAddr.getText().toString());

			Toast.makeText(this, "Збережено", Toast.LENGTH_SHORT).show();


			finish();
			break;
		}
	}
	
	
}
