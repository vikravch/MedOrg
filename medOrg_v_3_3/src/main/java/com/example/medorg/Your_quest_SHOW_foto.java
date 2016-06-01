package com.example.medorg;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class Your_quest_SHOW_foto extends BaseActivity {
	
	ImageView mImage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_quest_show_foto);
		
		Intent intent = getIntent();

		String fotoFile = intent.getStringExtra("fotoFile");

		File file = new File(fotoFile);

		Uri fotoUri = Uri.fromFile(file);

		mImage = (ImageView) findViewById(R.id.answFoto);

		mImage.setImageURI(fotoUri);
		
	}
}
