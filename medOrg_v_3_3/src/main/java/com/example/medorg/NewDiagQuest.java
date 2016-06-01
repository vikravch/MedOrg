package com.example.medorg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.MO_io_class;

public class NewDiagQuest extends Activity implements OnClickListener {
	
	Button btnAdd;
	ListView lvFiles;
	String[] files = new String[]{"file1","file2","file3"};
	MO_io_class MO_io, MO_io1;
	String [] FileNames_List;
	MedOrg id1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diagnoz_new);
		btnAdd = (Button) findViewById(R.id.newdiag);
		btnAdd.setOnClickListener(this);

		id1 = new MedOrg();
		MO_io = new MO_io_class("D_", ".txt");
		id1.getQuestions(MO_io);
		//questions = id1.questions;
		FileNames_List = id1.FileNames_List;
		
		lvFiles = (ListView) findViewById(R.id.lvDiagnozFiles);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,FileNames_List);
		lvFiles.setAdapter(adapter);
		
		lvFiles.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				String selectedFromList =(String) (lvFiles.getItemAtPosition(position));
				Intent intent = new Intent(Intent.ACTION_VIEW);
				String path = MO_io.filePath+"/";
				path = "file://"+path+""+selectedFromList;
				Log.d("MedOrg", "Click clack on "+path);
				
				//Uri uri= Uri.parse(path+""+selectedFromList);
				Uri uri= Uri.parse(path);
				intent.setDataAndType(uri, "text/plain");
				
				startActivity(intent);
			}
			
		});
		
	}
	protected void onRestart(){
		super.onRestart();
		MO_io1 = new MO_io_class("D_", ".txt");
		id1.getQuestions(MO_io);
		//questions = id1.questions;
		FileNames_List = id1.FileNames_List;
		
		lvFiles = (ListView) findViewById(R.id.lvDiagnozFiles);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,FileNames_List);
		lvFiles.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.newdiag:
			Intent int1 = new Intent(this,DiagnFirstQuest.class); 
			startActivity(int1);
			break;
		}
	}

}
