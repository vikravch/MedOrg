package com.example.medorg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Log.i("Заставка","В \"onCreate()\" методе");

		
		Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
		Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);

		TextView logotype = (TextView) findViewById(R.id.textView1);
		logotype.startAnimation(fade2);
		
		ImageView logoview = (ImageView) findViewById(R.id.imageView1);
		logoview.startAnimation(fade2);
		
		LinearLayout lay1 = (LinearLayout) findViewById(R.id.Layout2);

		// 		Перебор элементов контейнера и установка анимации:		
		for ( int i=0; i<lay1.getChildCount(); i++)
		{
			lay1.getChildAt(i).setAnimation(spinin);
		}

		// 		Установка анимации для всех элеметов контейнера:		
/*
		LayoutAnimationController controller = new LayoutAnimationController(spinin);
		lay1.setLayoutAnimation(controller);
*/	

		spinin.setAnimationListener(new AnimationListener() 
		{
			  @Override
			  public void onAnimationStart(Animation animation) {
			  // TODO Auto-generated method stub
			           }
			   
			  @Override
			  public void onAnimationRepeat(Animation animation) {
			  // TODO Auto-generated method stub
			           }
			  
			  @Override
			  public void onAnimationEnd(Animation animation)
			  {
				startActivity(new Intent(SplashActivity.this, Main_menu.class));
				SplashActivity.this.finish();
			  }
		}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
