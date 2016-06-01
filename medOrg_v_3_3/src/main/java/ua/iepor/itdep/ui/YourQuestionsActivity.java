package ua.iepor.itdep.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;
import com.example.medorg.BaseActivity;
import com.example.medorg.R;
import com.example.medorg.Standard_quest_EDIT_Activity;
import com.example.medorg.Your_quest_EDIT;
import com.example.medorg.Your_quest_NEW_Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ua.iepor.itdep.util.AsyncLoadArrayFromFiles;
import ua.iepor.itdep.util.Params;

public class YourQuestionsActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

	String [] FileNames_List;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d(Params.LOG_TAG, "Created Your Quest");
		setContentView(R.layout.activity_your_questions);
		findViewById(R.id.new_quest).setOnClickListener(this);
		showMenu();
	}

	@Override
	  protected void onRestart() {
	    super.onRestart();
	    showMenu();
	  }

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showMenu()
	{
		ListView lvMain = (ListView) findViewById(R.id.listView1);

		Intent intent = new Intent(this, StandardQuestEditActivity.class);
        intent.putExtra(Params.EXTRA_QUESTION_TYPE , "My");
        intent.putExtra(Params.EXTRA_FILE_NAME_FOR_LIST, FileNames_List);

		Cursor c = getContentResolver().query(Params.CONTENT_URI_QUESTIONS, null, null, null, null);
		try {
			c.moveToFirst();
			ArrayList<String> arrayList = new ArrayList<>();

			do {
				arrayList.add(c.getString(c.getColumnIndex(Params.QUESTION_TEXT)));
				Log.d(Params.LOG_TAG, String.format("question - %s , answer - %s ",
						c.getString(c.getColumnIndex(Params.QUESTION_TEXT)),
						c.getString(c.getColumnIndex(Params.QUESTION_ANSWER))
				));
			} while (c.moveToNext());

			SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.menu3_item, c
					, new String[]{Params.QUESTION_TEXT, "_id"}, new int[]{R.id.tvPibDoctor, R.id.tvIdDoctor}, Adapter.NO_SELECTION);

//		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu3_item, arrayList);
			lvMain.setAdapter(cursorAdapter);
			lvMain.setOnItemClickListener(this);

			Toast.makeText(this, getResources().getString(R.string.doctor_toast_text), Toast.LENGTH_LONG).show();
		} catch (CursorIndexOutOfBoundsException e) {
			Toast.makeText(this, getResources().getString(R.string.doctor_empty_text), Toast.LENGTH_LONG).show();
		}
                // Имена файлов

/*
        AsyncLoadArrayFromFiles asyncLoadArrayFromFiles = new AsyncLoadArrayFromFiles(this,intent);
        asyncLoadArrayFromFiles.execute("MO_QO", ".qst", lvMain);*/

		Toast.makeText(YourQuestionsActivity.this, "Оберіть питання зі списку", Toast.LENGTH_LONG).show();
	}

	 public void onClick(View view)
	 {
		 Log.d(Params.LOG_TAG,"click add");
		 try {
		      switch (view.getId())
		      {
		      	case R.id.new_quest:
					
		      		startActivity(new Intent(this, YourQuestNewActivity.class));
		      		break;		      
		      }
		     }	catch ( Exception e) 
		    	{
		    		e.printStackTrace();
		    	}
	 }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String idStr = ((TextView) view.findViewById(R.id.tvIdDoctor)).getText().toString();
		Intent intent = new Intent(this,YourQuestEditActivity.class).putExtra("id",idStr);
		startActivity(intent);
	}
}
