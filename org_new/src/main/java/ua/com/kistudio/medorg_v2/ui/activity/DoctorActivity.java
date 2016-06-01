package ua.com.kistudio.medorg_v2.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class DoctorActivity extends Activity implements OnClickListener, AdapterView.OnItemClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor);
		findViewById(R.id.new_diag).setOnClickListener(this);
		showMenu();
	}

    @Override
    protected void onRestart() {
        super.onRestart();
        showMenu();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showMenu() {
		ListView lvMain = (ListView) findViewById(R.id.lvMain);


		Cursor c = getContentResolver().query(Params.CONTENT_URI_DOCTOR, null, null, null, null);
		try {
			c.moveToFirst();
			ArrayList<String> arrayList = new ArrayList<>();

			do {
				arrayList.add(c.getString(c.getColumnIndex(Params.DOCTOR_PIB)));
				Log.d(Params.LOG_TAG, String.format("pib - %s , spec - %s , about - %s , phone - %s",
						c.getString(c.getColumnIndex(Params.DOCTOR_PIB)),
						c.getString(c.getColumnIndex(Params.DOCTOR_SPEC)),
						c.getString(c.getColumnIndex(Params.DOCTOR_ABOUT)),
						c.getString(c.getColumnIndex(Params.DOCTOR_PHONE))
				));
			} while (c.moveToNext());

			SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.menu3_item, c
					, new String[]{Params.DOCTOR_PIB, "_id"}, new int[]{R.id.tvPibDoctor, R.id.tvIdDoctor}, Adapter.NO_SELECTION);

//		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.menu3_item, arrayList);
			lvMain.setAdapter(cursorAdapter);
			lvMain.setOnItemClickListener(this);

		/*
		AsyncLoadArrayFromFiles asyncLoadArrayFromFiles = new AsyncLoadArrayFromFiles(this,intent);
		asyncLoadArrayFromFiles.execute(Params.DOCTOR_PREFIX, Params.DOCTOR_SUFFIX, lvMain);
*/
			Toast.makeText(DoctorActivity.this, getResources().getString(R.string.doctor_toast_text), Toast.LENGTH_LONG).show();
		} catch (CursorIndexOutOfBoundsException e) {
			Toast.makeText(this, getResources().getString(R.string.doctor_empty_text), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId())
		{
		case R.id.new_diag:
			Intent intent1 = new Intent(this, NewDoctorActivity.class);
			startActivity(intent1);
		break;	
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView textViewPib = (TextView) view.findViewById(R.id.tvPibDoctor);
		TextView textViewId = (TextView) view.findViewById(R.id.tvIdDoctor);

		String strText = textViewPib.getText().toString();
		String idText = textViewId.getText().toString();
		Intent intent = new Intent(this, EditDoctorActivity.class);
		intent.putExtra(Params.EXTRA_POSITION_NUMBER_FROM_MENU, position);
		intent.putExtra("file_name", "");
		intent.putExtra("id",idText);
		intent.putExtra("quest", strText);
		intent.putExtra("path", "");
		startActivity(intent);
	}
}