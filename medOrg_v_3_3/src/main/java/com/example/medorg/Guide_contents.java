package com.example.medorg;

/*import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;*/
/*import android.support.v4.app.Fragment;*/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class Guide_contents extends /*ActionBar*/Activity {
	final String LOG_TAG = "myLogs";
	Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_contents);
        
        ListView guideContentsListView = (ListView) findViewById(R.id.guideContentsListView);
        
        String[] guideParts = { "1. Що таке рак молочної залози?", "2. Статистичні дані.", "3. Нормальні тканини молочних залоз.", "4. Лімфатична система молочних залоз.",
				"5. Захворювання молочних залоз, що не є злоякісними.", "6. Типи злоякісних захворювань молочної залози.", "7. Обстеження на виявлення раку молочної залози: скринінг, діагностика та моніторинг.", "8. Раннє виявлення раку молочної залози.", 
				"9. Діагностика раку молочної залози.", "10. Стадії раку молочної залози.", "11. Чинники, що впливають на прогноз (шанс одужання) та вибір методу лікування.", 
				"12. Лікування." };
        
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,	R.layout.menu_item, guideParts);
		
		
        guideContentsListView.setAdapter(adapt);
		
		
        guideContentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View itemClicked,
		int position, long id) {
			TextView textView = (TextView) itemClicked;
			String strText = textView.getText().toString();
			
			Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
			
			
			switch (position) {
				case 0:
					intent = new Intent(Guide_contents.this, PartOneActivity.class);
				break;
				/*case 1:
					intent = new Intent(Guide_contents.this, PartTwoActivity.class);
				break;
				case 2:
					intent = new Intent(Guide_contents.this, PartThreeActivity.class);
				break;
				case 3:
					intent = new Intent(Guide_contents.this, PartFourActivity.class);
				break;
				case 4:
					intent = new Intent(Guide_contents.this, PartFiveActivity.class);
				break;
				case 5:
					intent = new Intent(Guide_contents.this, PartSixActivity.class);
				break;
				case 6:
					intent = new Intent(Guide_contents.this, PartSevenActivity.class);
				break;
				case 7:
					intent = new Intent(Guide_contents.this, PartEightActivity.class);
				break;
				case 8:
					intent = new Intent(Guide_contents.this, PartNineActivity.class);
				break;
				case 9:
					intent = new Intent(Guide_contents.this, PartTenActivity.class);
				break;
				case 10:
					intent = new Intent(Guide_contents.this, PartElevenActivity.class);
				break;
				case 11:
					intent = new Intent(Guide_contents.this, PartTwelveActivity.class);
				break;*/
			}
			startActivity(intent);							
			}
		});
        
    }
}
