package com.example.medorg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DiagnFirstQuest extends Activity implements OnClickListener, OnItemClickListener {
	int quest;
	String[] firstArr = new String[]{"TX", "TO", "Tis", "T1 mi", "T1 a", "T1 b", "T1 c", "T2", "T3", "T4 a", "T4 b", "T4 c", "T4 d"};
	String[] secondArr = new String[]{"NX", "NO mi+", "NO mol+", "N1 mi", "N1 a", "N1 b", "N1 c", "N2 a", "N2 b", "N3 a", "N3 b", "N3 c"};
	String[] thirdArr = new String[]{"MO", "cM(i+)", "M2"};
	static int[] answ = new int[]{0,0,0};
	static String[] answStr = new String[]{"","",""};
	Button btn, btnpr;
	int positionCl;
	ArrayAdapter<String> adapter;
	ListView lv;
	TextView tvRes, tvView, tvH1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diagnfirstquest);
		quest=1;
		tvView = (TextView) findViewById(R.id.tvOutQuestTitle);
		tvRes= (TextView) findViewById(R.id.tvResult);
		//tvH1 = (TextView) findViewById(R.id.tvOutQuestTitleH1);
		lv = (ListView) findViewById(R.id.lvFirstQuest);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firstArr);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		btn = (Button) findViewById(R.id.btnnext);
		btn.setOnClickListener(this); 
		btnpr = (Button) findViewById(R.id.btnprev);
		btnpr.setOnClickListener(this);
	}

	public void onRestart(){
		super.onRestart();
		finish();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btnnext:
			switch (quest){
			case 1:
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, secondArr);
				lv.setAdapter(adapter);
				if (answStr[1]==""){
					tvRes.setText("Нічого не обрано");
				}
				else{
					tvRes.setText("Обрано - "+answStr[1]);
				}//lv.setTag(1, "1");
				tvView.setText("");
				//tvH1.setText("");
				quest++;
				break;
			case 2:
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thirdArr);
				lv.setAdapter(adapter);
				if (answStr[2]==""){
					tvRes.setText("Нічого не обрано");
				}
				else{
					tvRes.setText("Обрано - "+answStr[2]);
				}
				tvView.setText("");
				//tvH1.setText("");
				quest++;
				break;
			case 3:
				Intent intent = new Intent(this, DiagnozNextQuest.class);
				intent.putExtra("answers", answStr);
				startActivity(intent);
				break;
			}
			break;
		case R.id.btnprev:
			switch (quest){
			case 1:
				finish();
			case 2:
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, firstArr);
				lv.setAdapter(adapter);
				if (answStr[0]==""){
					tvRes.setText("Нічого не обрано");
				}
				else{
					tvRes.setText("Обрано - "+answStr[0]);
				}
					//lv.setTag(1, "2");
				tvView.setText(this.getResources().getString(R.string.firstquest));
				quest--;
				break;
			case 3:
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, secondArr);
				lv.setAdapter(adapter);
				if (answStr[1]==""){
					tvRes.setText("Нічого не обрано");
				}
				else{
					tvRes.setText("Обрано - "+answStr[1]);
				}
				//lv.setTag(1, "2");
				tvView.setText("");
				//tvH1.setText("");
				quest--;
				break;
			}
			break;
		}
		
	}
	
	String resTitle="";
    String selectedFromList = "";
    int pos;
	
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			//Log.d("MedOrg","fromclick0");
        	String[] masStr = new String[]{""};
        	Log.d("MedOrg"," "+position);
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    DiagnFirstQuest.this );
        	selectedFromList =(String) (lv.getItemAtPosition(position));
        	char[] masch = selectedFromList.toCharArray();
        	
        	if (masch[0]=='T'){masStr = getResources().getStringArray(R.array.t); answ[0]=position; answStr[0]=selectedFromList;}
        	if (masch[0]=='N'){masStr = getResources().getStringArray(R.array.n); answ[1]=position; answStr[1]=selectedFromList;}
        	if ((masch[0]=='M')||(masch[0]=='c')){masStr = getResources().getStringArray(R.array.m); answ[2]=position; answStr[2]=selectedFromList;}
        	resTitle = masStr[position];
        	alertDialogBuilder.setTitle(selectedFromList);
        	Log.d("MedOrg","fromclick1");
        	//answ[0]=position;
        	alertDialogBuilder.setMessage(resTitle)
        	.setPositiveButton("Так",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, close
                    // current activity
                	//tvRes.setText("Hello");
                	printToRes();
                	Log.d("MedOrg","2");
                	
                }
              })
            .setNegativeButton("Ні",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }});
        	 AlertDialog alertDialog = alertDialogBuilder.create();
        	alertDialog.show();
        }
        protected void printToRes(){
        	//tvRes.setText("Обрано: "+selectedFromList+" answ1="+answStr[0]+" answ2="+answStr[1]+" answ3="+answStr[2]);
        	tvRes.setText("Обрано: "+selectedFromList);
        }
	
}
