package com.example.medorg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.MO_io_class;




public class SaveResultActivity extends Activity implements OnClickListener {

	
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

	
	static String[] strRes = new String[]{};
	static String[] answ = new String[14];
	Button btnSave, btnSend, btnQuit;
	
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_test);
		Intent i = this.getIntent();
		strRes = i.getStringArrayExtra("inpansw");
		answ = i.getStringArrayExtra("answers");
		btnSave = (Button) findViewById(R.id.btnSaveTest);
		btnSave.setOnClickListener(this);
		btnSend = (Button) findViewById(R.id.btnSendTest);
		btnSend.setOnClickListener(this);
		for (int k=0; k<strRes.length; k++){
			Log.d("MedOrg"," Res, quest - "+k+" strRes - "+strRes[k]);
		}
		
		for (int k=0; k<answ.length-2; k++){
			Log.d("MedOrg"," Res, quest - "+k+" strRes - "+answ[k]);
		}
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String resultString="Результати тестування: \n";
		
		
		for (int k=0; k<strRes.length; k++){
			Log.d("MedOrg"," Res, quest - "+k+" strRes - "+strRes[k]);
		}
		
		for (int k=0; k<answ.length-2; k++){
			Log.d("MedOrg"," Res, quest - "+k+" strRes - "+answ[k]);
		}
		
		resultString+="Питання 1\n - "+answ[0]+"  "+answ[1]+"  "+answ[2];
		
		for (int j=3; j<12; j++){
			//res+="\n Питання "+questions[j-3];
			//resultString+="\n Питання "+questions[j]+"\n відповідь: "+ allquest[j][res[j]-1]; 
//			if (answ[j]!=""){res+="\n Питання "+questions[j]+"\n відповідь: "+/*allquest[j][Integer.parseInt(answ[j])*/];}
			if (answ[j]!="-1"){resultString+="\n Питання "+questions[j-3]+"\n відповідь: "+allquest[j-3][0];}
			else{resultString+="\n Питання "+questions[j-3]+"\n відповіді не має";}
			//resultString+="sda";
		}
	
		
		switch (v.getId()){
		
		case R.id.btnSaveTest:
			saveTest(resultString);
			Log.d("MedOrg",resultString);
		break;
		case R.id.btnSendTest:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
			intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
			intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

			startActivity(Intent.createChooser(intent, "Send Email"));
		break;
		}

	}
	
	void saveTest(String res){
		
		Log.d("MedOrg","results"+res);
		MO_io_class MO_io;
		MO_io = new MO_io_class("", ".txt");
		String [] FileNames_List;
		
		
		String path = MO_io.filePath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date =sdf.format(new Date());
		String nameFile= "D_"+date+".txt";
		//String content = "Hello!!!";
		writeFile(path, nameFile, res);
		Log.d("MedOrg","path - "+path+" nameFile - "+nameFile+ " date - "+date);
		finish();
	}

}
