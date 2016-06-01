package com.example.medorg;

/*import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;*/
/*import android.support.v4.app.Fragment;*/
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class PartOneActivity extends Activity {
	final String LOG_TAG = "myLogs";
	public final static String EXTRA_MESSAGE = "com.example.medorg_guide";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_part_one);
		
		SpannableString ss = new SpannableString("		Рак молочної залози (РМЗ) - це злоякісна пухлина, яка утворюється в клітинах молочних залоз (МЗ). Злоякісна пухлина представляє собою групу злоякісних клітин, які можуть проростати в оточуючі тканини або поширюватися (метастазувати) у віддалені ділянки тіла. РМЗ зустрічається майже виключно у жінок, але на неї можуть хворіти і чоловіки. ");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
            	
            	String strText = "Рак";//"Рак молочної залози (РМЗ)";
            	Log.d(LOG_TAG, "Menu item " + strText + " was clicked!");
            	/*Intent intent = new Intent(PartOneActivity.this, WordDescription.class);
				intent.putExtra(EXTRA_MESSAGE, strText);
				startActivity(intent);*/
            	Intent intent = new Intent(PartOneActivity.this, WordDescriptionFromGuide.class);
				intent.putExtra(EXTRA_MESSAGE, strText);
				startActivity(intent);
            }
        };
        
        
        ss.setSpan(clickableSpan, 0, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        TextView textView = (TextView) findViewById(R.id.textViewPartOne);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
