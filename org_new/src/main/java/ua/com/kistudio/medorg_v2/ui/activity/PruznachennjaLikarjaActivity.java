package ua.com.kistudio.medorg_v2.ui.activity;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.model.MO_io_class;


public class PruznachennjaLikarjaActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

	MO_io_class MO_io;
	String[] FileNames_List;
	String[] questions;
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

			Toast.makeText(this,getResources().getString(R.string.pruzn_toast_text), Toast.LENGTH_LONG).show();
		} catch (CursorIndexOutOfBoundsException e) {
			Toast.makeText(this, getResources().getString(R.string.pruzn_empty_text), Toast.LENGTH_LONG).show();
		}
		// Имена файлов


		Toast.makeText(this, "Оберіть призначення зі списку", Toast.LENGTH_LONG).show();
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

