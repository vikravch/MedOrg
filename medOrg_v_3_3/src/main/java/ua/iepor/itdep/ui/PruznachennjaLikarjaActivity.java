package ua.iepor.itdep.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;
import com.example.medorg.BaseActivity;
import com.example.medorg.Edit_Pruzb;
import com.example.medorg.MedOrg;
import com.example.medorg.New_Pruzn;
import com.example.medorg.R;

import java.util.ArrayList;

import ua.iepor.itdep.util.Params;


public class PruznachennjaLikarjaActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

	MO_io_class MO_io;
	String [] FileNames_List;
	String [] questions;
	Button btn1;
	ListView lvOut;
	
/////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pruzn_likarja);

		lvOut = (ListView) findViewById(R.id.lvMainPr);
		btn1 = (Button) findViewById(R.id.new_diag);
		btn1.setOnClickListener(this);
		
		LogMO.i("Your_questions_Activity","В \"onCreate()\"");
		
		showMenu();
		
	}
/////////////////////////////////////////////////////////////////////////////////////
	@Override
	  protected void onRestart() {
	    super.onRestart();
	    showMenu();
	  }
/////////////////////////////////////////////////////////////////////////////////////
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showMenu()
	{

		Cursor c = getContentResolver().query(Params.CONTENT_URI_PRUZN, null, null, null, null);
		try {
			c.moveToFirst();

			do {
				Log.d(Params.LOG_TAG, String.format("text - %s",
						c.getString(c.getColumnIndex(Params.PRUZN_TEXT)))
				);
			} while (c.moveToNext());

			SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.menu3_item, c
					, new String[]{Params.PRUZN_TEXT, "_id"}, new int[]{R.id.tvPibDoctor, R.id.tvIdDoctor}, Adapter.NO_SELECTION);

			lvOut.setAdapter(cursorAdapter);
			lvOut.setOnItemClickListener(this);

			Toast.makeText(this, getResources().getString(R.string.doctor_toast_text), Toast.LENGTH_LONG).show();
		} catch (CursorIndexOutOfBoundsException e) {
			Toast.makeText(this, getResources().getString(R.string.doctor_empty_text), Toast.LENGTH_LONG).show();
		}
		// Имена файлов


		Toast.makeText(this, "Оберіть питання зі списку", Toast.LENGTH_LONG).show();
	}

/////////////////////////////////////////////////////////////////////////////////////
	 public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	 {
		 switch (view.getId())
			{
			case R.id.new_diag:
				Intent intent1 = new Intent(this, NewPruznActivity.class);
				startActivity(intent1);
			break;	
			}
	 }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String idStr = ((TextView) view.findViewById(R.id.tvIdDoctor)).getText().toString();
		Intent intent = new Intent(this,EditPruznachennjaActivity.class).putExtra("id",idStr);
		startActivity(intent);
	}
////////////////////////////////////////////////////////////////////////////////////
}

