package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class ProfileActivity extends Activity implements OnClickListener {
	
	SharedPreferences sp;
	EditText etMail, etPIB, etTel, etAddr, etAge;
	public static final String MAIL = "mail";
	public static final String PIB = "pib";
	public static final String AGE = "age";
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
		etAge = (EditText) findViewById(R.id.et_vik);
		
		etMail.setText(loadText(MAIL), TextView.BufferType.EDITABLE);
		etPIB.setText(loadText(PIB), TextView.BufferType.EDITABLE);
		etTel.setText(loadText(PHONE), TextView.BufferType.EDITABLE);
		etAddr.setText(loadText(ADRESS),TextView.BufferType.EDITABLE);
		etAge.setText(loadText(AGE), TextView.BufferType.EDITABLE);
		
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
			saveText(AGE, etAge.getText().toString());

			Toast.makeText(this, "Збережено", Toast.LENGTH_SHORT).show();


			finish();
			break;
		}
	}
	
	
}
