package com.example.medorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class DiagnozInputs extends Activity implements OnClickListener{

	TextView tv1,tv2,tv3,tv4;
	Button btn;
	EditText et1,et2,et3,et4;
	
	String[] questions = new String[]{
			"7.	Метастатичне ураження реґіонарних лімфатичних вузлів. Вкажіть кількість уражених клітин (0-відсутні)",
			"9.1.	Відсоток клітин, що мають рецептори до естрогенів (0-100%):",
			"9.2.	Відсоток клітин, що мають рецептори до прогістеронів (0-100%):",
			"11.	Результати генетичного обстеження за допомогою тестів прогнозу перебігу захворювання MammaPrint або Oncotype DX: \n 11.1. Результати обстеження оцінені (бали):",
			};
	static String[] strRes = new String[4];
	static String[] answ = new String[14];
	
	protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.diagnoznextinput);
			Intent i1 = this.getIntent();
			
			answ = i1.getStringArrayExtra("answers");
			tv1 = (TextView) findViewById(R.id.textDiagnIn1);
			tv1.setText(questions[0]);
			tv2 = (TextView) findViewById(R.id.textDiagnIn2);
			tv2.setText(questions[1]);
			tv3 = (TextView) findViewById(R.id.textDiagnIn3);
			tv3.setText(questions[2]);
			tv4 = (TextView) findViewById(R.id.textDiagnIn4);
			tv4.setText(questions[3]);
			btn = (Button) findViewById(R.id.buttonNextQuest);
			btn.setOnClickListener(this);
			et1 = (EditText) findViewById(R.id.editText1);
			et2 = (EditText) findViewById(R.id.editText2);
			et3 = (EditText) findViewById(R.id.editText3);
			et4 = (EditText) findViewById(R.id.editText4);
		}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.buttonNextQuest:
			
			strRes[0] = et1.getText().toString();
			strRes[1] = et2.getText().toString();
			strRes[2] = et3.getText().toString();
			strRes[3] = et4.getText().toString();
			
			
			Intent i1 = new Intent(DiagnozInputs.this,SaveResultActivity.class);
			i1.putExtra("answers", answ);
			i1.putExtra("inpansw", strRes);
			startActivity(i1);
			break;
		}
	}
	
}
