package com.example.medorg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;

@SuppressLint("NewApi") public class DiagnozNextQuest extends Activity implements OnClickListener {
	
	String[] questions = new String[]{
			"2.	За патогістологічною градацією (категорія G) пухлина є:",
			"3.	Клінічна стадія:",
			"4.	Гістологічний тип пухлини:",
			"5.	Інвазивність пухлини",
			"6.	Прилеглі до пухлини лімфатичні та/або кровоносні судини залучені у пухлинний процес (за гістологічним дослідженням):",
			//"7.	Метастатичне ураження реґіонарних лімфатичних вузлів:",
			"8.	Молекулярний підтип пухлини:",
			//"9.	Статус рецепторів гормонів \n 9.1.	Вказати відсоток клітин, що мають рецептори до естрогенів (0-100%):",
			//"9.2.	Вказати відсоток клітин, що мають рецептори до прогістеронів (0-100%):",
			"10.	HER2- статус пухлини: \n 10.1.	Імуногістохімічне дослідження:",
			"10.2.	Метод FISH",
			//"11.	Результати генетичного обстеження за допомогою тестів прогнозу перебігу захворювання MammaPrint або Oncotype DX: \n 11.1. Результати обстеження оцінені в ____ балів",
			"11.2. Ризик відновлення хвороби протягом наступних 10-ти років"};
	//int[] answer = new int[]{3,8,5,2,2,2,4,2,2,3,2,1,3,2,2,2,3,2,2};
	int[] answer = new int[]{3,8,5,2,2,4,3,2,3,2,2,2,3,2,2};
	String[][] allquest = new String[][]{
			{"G1 (Високодиференційованою)","G2 (Помірнодиференційованою)","G3 (Низькодиференційованою)"},
			{"ІА", "IВ","ІІА","IIВ","ІІІА","IIIВ","IIIС","IV"},
			{"протокова карцинома","Долькова карцинома","Хвороба Педжета соска","Запальний рак молочної залози","Інший тип"},
			{"Інвазивна","Неінвазивна"},
			{"Так","Ні"},
//			{"Метастази присутні. Вкажіть кількість уражених лімфовузлів","Метастази у лімфовузлах відсутні"},
			{"Люмінальний А","Люмінальний Б","HER2-позитивний","Потрійно-негативний"},
//			{"Рецептори до естрогенів відсутні"},
//			{"Рецептори до прогістеронів відсутні"},
			{"Позитивний","Негативний","Прикордонне значення"},
			{"Позитивний (ген HER2 ампліфікований)","Негативний (ген HER2 не ампліфікований)","Немає данних"},
//			{" оцінені в ____ балів"},
			{"Низький","Середній","Високий"}
};
	static int[] res=new int[14];
	static String[] resultTest = new String[14];
	protected String mail,pib,phone,sex,adress;
	
	MO_io_class MO_io;
	String [] FileNames_List;
	
	TextView tvName,tvQuest;
	Button btnNext;
	RadioGroup rdg;
	RadioButton rbutt;
	Button button1,button2,buttonPrev;
	String resultString;
	
	
	int num = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		Intent i1 = this.getIntent();
		String[] answStr = new String[]{"","",""};
		answStr = i1.getStringArrayExtra("answers");
		Log.d("MedOrg","1 - "+answStr[0]+" 2 - "+answStr[1]+" 3 - "+answStr[2]);
		resultTest[0] = answStr[0];
		resultTest[1] = answStr[1];
		resultTest[2] = answStr[2];
		Log.d("MedOrg","1 - "+resultTest[0]+" 2 - "+resultTest[1]+" 3 - "+resultTest[2]);
		
		
		super.onCreate(savedInstanceState);
		Intent i = this.getIntent();
		String[] answFirst = i.getStringArrayExtra("answers");
		//Log.d("MedOrg","Answers: 1- "+answFirst[0]+" 2- "+answFirst[1]+" 3- "+answFirst[2]);		
		setContentView(R.layout.diagnoznext);
		Log.d("Org","Hello");
		ApplMedOrg appl= new ApplMedOrg();
		appl =(ApplMedOrg) this.getApplication();
		MO_io = new MO_io_class("", ".txt");
		mail= appl.mail;
		pib = appl.pib;
		phone = appl.phone;

		//tvName = (TextView) findViewById(R.id.textNameQuest);
		tvQuest = (TextView) findViewById(R.id.textViewQuestionDiagn);
		//tvName.setText("Питання №"+num);
		tvQuest.setText(questions[num-2]);
	
		rdg = (RadioGroup) findViewById(R.id.radioGroupDiagn);
		btnNext = (Button) findViewById(R.id.buttonNextQuest);
		btnNext.setOnClickListener(this);
		buttonPrev = (Button) findViewById(R.id.buttonPrevQuest);
		buttonPrev.setOnClickListener(this);
		
		for (int j=1; j<=allquest[0].length; j++){
			rbutt = new RadioButton(this);
			rbutt.setText(allquest[0][j-1]);
			rdg.addView(rbutt);
			}
		
		button1 = new Button(this);
		button1.setText("Надіслати лікарю");
		button1.setTag("1");
		button1.setOnClickListener(onCl);
		
		
		button2 = new Button(this);
		button2.setText("Зберегти");
		button2.setTag("2");
		button2.setOnClickListener(onCl);
		
	}
	
	OnClickListener onCl = new OnClickListener(){
		@Override
		public void onClick(View v){
			Log.d("MedOrg","Click");
			//tvName = (TextView) findViewById(R.id.textNameQuest);
			
			if (v.getTag()=="1"){
					}
			if (v.getTag()=="2"){
				
				resultString="Результати тестування: \n";
				
			
				
				for (int j=0; j<questions.length; j++){
					//resultString+="\n Питання "+questions[j]+"\n відповідь: "+ allquest[j][res[j]-1]; 
					if (res[j]!=-1){resultString+="\n Питання "+questions[j]+"\n відповідь: "+allquest[j][res[j]];}
					else{resultString+="\n Питання "+questions[j]+"\n відповіді не має";}
					//resultString+="sda";
				}
				
				
				String path = MO_io.filePath;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String date =sdf.format(new Date());
				String nameFile= "D_"+date+".txt";
				//String content = "Hello!!!";
				writeFile(path, nameFile, resultString);
				Log.d("MedOrg","path - "+path+" nameFile - "+nameFile+ " date - "+date);
				
			}
			
		}
	};
	
	protected void saveInFile(){
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		int lengthRadio=1;
		//tvName = (TextView) findViewById(R.id.textNameQuest);
		rdg = (RadioGroup) findViewById(R.id.radioGroupDiagn);
		
		int radioId = -1;
		radioId = rdg.getCheckedRadioButtonId();
		if (radioId==-1){
			Toast.makeText(this, "Оберіть пункт", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d("MedOrg","Radio - "+radioId);
		View radioButton = rdg.findViewById(radioId);
		res[num-2] = 100;
		res[num-2]=rdg.indexOfChild(radioButton);
		int index = num+1;
		resultTest[num+1] = rdg.indexOfChild(radioButton)+"";
		Log.d("MedOrg"," num - "+index+" res - "+resultTest[index]);
		rdg.removeAllViews();

		switch (v.getId()){
		case R.id.buttonNextQuest:
			num++;
			if (num<=10){
				
			//	tvName.setText("Питання №"+num);
				tvQuest.setText(questions[num-2]);
				lengthRadio = allquest[num-2].length;
				for (int j=1; j<=lengthRadio; j++){
					rbutt = new RadioButton(this);
					rbutt.setText(allquest[num-2][j-1]);
					rdg.addView(rbutt);
					}
			}
				
			else{/*
				String resultString="";
				for (int j=0; j<=9; j++){
					resultString+=j+"= "+res[j];
				}
				//tvName.setText("Дякуємо за відповіді! "+resultString);
				tvQuest.setText("");
				
				LinearLayout ll = (LinearLayout) findViewById(R.id.linearDiagn);
				
				ll.addView(button1);
				ll.addView(button2);
				buttonPrev.setClickable(false);*/
				Intent i1 = new Intent(DiagnozNextQuest.this,DiagnozInputs.class);
				i1.putExtra("answers", resultTest);
				startActivity(i1);
			}
			/*
			if (num==9){
				//tvName.setText("Питання №"+num);
				TextView tv1 = new TextView(this);
				tv1.setText("Вказати відсоток клітин, що мають рецептори до естрогенів (0-100%):");
				EditText et1 = new EditText(this);
				et1.setEms(10);

				LinearLayout ll = (LinearLayout) findViewById(R.id.linearDiagn);
				ll.addView(tv1);
				ll.addView(et1);
				
			}
			if (num==10){
				//tvName.setText("Питання №"+num);
				TextView tv1 = new TextView(this);
				tv1.setText("Вказати відсоток клітин, що мають рецептори до прогістеронів (0-100%):");
				EditText et1 = new EditText(this);
				et1.setEms(10);
				LinearLayout ll = (LinearLayout) findViewById(R.id.linearDiagn);
				ll.addView(tv1);
				ll.addView(et1);
				
			}*/
			break;
			
		case R.id.buttonPrevQuest:
			num--;
			if (num>=2){
				
				//tvName.setText("Питання №"+num);
				tvQuest.setText(questions[num-2]);
				lengthRadio = allquest[num-2].length;
				for (int j=1; j<=lengthRadio; j++){
					rbutt = new RadioButton(this);
					rbutt.setText(allquest[num-2][j-1]);
					rdg.addView(rbutt);
					}
			}
			else{finish();}
			break;
		}
	}
	
	String loadText(String key){
		SharedPreferences sp = getPreferences(Profile.MODE_WORLD_READABLE);
		String res=sp.getString(key, "dsa");
		//Toast.makeText(this, "load: "+res, Toast.LENGTH_SHORT).show();
		return res;
	}
	
	public void writeFile(String Path, String File_name, String Content) {
		//File f1 = new File( Path + "/" + File_name + ".txt" );
		File f1 = new File( Path + "/" + File_name);
	    try {
	      // отрываем поток для записи
	      BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
	      // пишем данные
	      bw.write(Content);
	      // закрываем поток
	      bw.close();
	      Log.d("MedOrg", "Файл записан");
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    Toast.makeText(this, "Результати збережено", Toast.LENGTH_SHORT).show();
		finish();
	  }
}
