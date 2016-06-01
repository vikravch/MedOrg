package ua.iepor.itdep.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.MO_io_class.LogMO;
import com.example.medorg.BaseActivity;
import com.example.medorg.R;
import com.example.medorg.Standard_quest_from_group_Activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ua.iepor.itdep.util.Params;

public class StandardQuestActivity extends BaseActivity {

	// MO_io_class MO_io;
	private String [] mGroupsNames;
/*
	String [] FileNames_List = {"standard_quest_group_1.txt", "standard_quest_group_2.txt", "standard_quest_group_3.txt",
								"standard_quest_group_4.txt", "standard_quest_group_5.txt", "standard_quest_group_6.txt",
								"standard_quest_group_7.txt", "standard_quest_group_8.txt", "standard_quest_group_9.txt",
								"standard_quest_group_10.txt","standard_quest_group_11.txt","standard_quest_group_12.txt",
								"standard_quest_group_23.txt"};
*/

	int [] Files_Id = {	R.raw.standard_quest_group_1, R.raw.standard_quest_group_2, R.raw.standard_quest_group_3,
						R.raw.standard_quest_group_4, R.raw.standard_quest_group_5, R.raw.standard_quest_group_6,
						R.raw.standard_quest_group_7, R.raw.standard_quest_group_8, R.raw.standard_quest_group_9,
						R.raw.standard_quest_group_10,R.raw.standard_quest_group_11,R.raw.standard_quest_group_12,
						R.raw.standard_quest_group_13 };
	int [] size_of_grp = {11,
						14,
						3,
						19,
						19,
						12,
						14,
						14,
						8,
						5,
						11,
						7,
						12
						};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acvtivity_standard_quest);
		showMenu();
	}

	@Override
	protected void onRestart() {
	    super.onRestart();
	    showMenu();
	  }

	protected void showMenu()
	{
		mGroupsNames = new String[Params.STANDART_QUEST_FILES_NUM];
		int num;
		for ( num=1; num<mGroupsNames.length; num++)
			mGroupsNames[num] = (num) + ".";
		
		InputStream iFile = getResources().openRawResource(R.raw.standard_quest_groups);
		byte b[] = new byte[1850];
		int s;
		try {
				s = iFile.read(b);
				String str = new String(b,0,s);
				// str = s + " " + str;
				s = str.indexOf('\n');
				num=0;
				while ( s > 0 )
				{
					// str = str + ' ' + s;
					LogMO.i(Params.LOG_TAG, str.substring(0, s) + ' ' + s + ' ' + str.length() + ' ' + "num=" + num);
					if ( str.length() > s ) 
						{
						mGroupsNames[num++] = str.substring(0,s);
							str = str.substring(s+1)+'\n';
						}
					s = str.indexOf('\n');
				}
				
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		ListView lvMain = (ListView) findViewById(R.id.sq_listView1);


		ArrayList<HashMap<String,String>> data = new ArrayList<>();
		for (int i = 0; i<mGroupsNames.length; i++){
			HashMap<String,String> map = new HashMap<>();
			map.put(Params.STANDART_QUESTION_TEXT,mGroupsNames[i]);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.menu3_item,  new String[]{Params.STANDART_QUESTION_TEXT}, new int[]{R.id.tvPibDoctor});

		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(StandardQuestActivity.this, R.layout.menu3_item, mGroupsNames);
	    lvMain.setAdapter(adapter);
		for ( num=0; num<mGroupsNames.length; num++)
			LogMO.i(Params.LOG_TAG, "groups_names[" + num + "] = " + mGroupsNames[num]);

	    lvMain.setOnItemClickListener
		(
				new AdapterView.OnItemClickListener()
				{
					public void onItemClick( AdapterView<?> parent, View itemClicked, int position, long id )
					{
						TextView textView = (TextView) itemClicked;
						String strText = textView.getText().toString();
						LogMO.i(Params.LOG_TAG, id + ", " + position + " -> " + strText);
						Intent intent = new Intent(StandardQuestActivity.this, StandartQuestFromGroupActivity.class);
                        intent.putExtra(Params.EXTRA_GROUPS_NAME_STANDART_QUEST, strText);
                        intent.putExtra(Params.EXTRA_ID_STANDART_QUEST, Files_Id[position]);
                        intent.putExtra(Params.EXTRA_SIZE_OF_GROUP_STANDART_QUEST, size_of_grp[position]);
                        intent.putExtra(Params.EXTRA_GROUP_NUMBER_STANDART_QUEST, position);

						startActivity(intent);
					}
				}
		);
	}
}
