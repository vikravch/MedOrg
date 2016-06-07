package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import ua.com.kistudio.medorg_v2.model.MedOrg;
import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class EditPruznachennjaActivity extends BaseActivity {


	Intent intent;
	
	EditText quest;
	/*EditText answ;*/
	
	String file_Name;
	String file_Path;
	String question;
	String newAnsw;
	String file_Name_Answ;
	String file_Name_Audio;
	String file_Name_Foto;
	
	String fromDialog;
	
	private MediaRecorder mediaRecorder;
	private MediaPlayer mediaPlayer;

	Button btnAudio;
	
	Uri fotoUri;  // Для файла фото
	
	private static final int PHOTO_INTENT_REQUEST_CODE = 100;

	String id;
	//AlertDialog.Builder alert;
	
/////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editdiagnoz);
		
		quest = (EditText) findViewById(R.id.editQuest);

		intent = getIntent();
		id = intent.getStringExtra("id");


		Cursor c = getContentResolver().query(Params.CONTENT_URI_PRUZN,null,"_id=="+id,null,null);
		c.moveToFirst();
		if (c.getCount()!=0){
			quest.setText(c.getString(c.getColumnIndex(Params.PRUZN_TEXT)));
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////
	public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	{
		MedOrg in1 = new MedOrg();
		AlertDialog.Builder alert;
		try 
		{
			switch (view.getId())
			{
				case R.id.clearAnswerEdit:
					quest.setText("");
					newAnsw = "";
					break;		
					
				case R.id.saveAnsw:
					question = quest.getText().toString();
					ContentValues contentValues = new ContentValues();
					contentValues.put(Params.PRUZN_TEXT,question);
					if (id==null){
						getContentResolver().insert(Params.CONTENT_URI_PRUZN,contentValues);
					} else {
						getContentResolver().update(Params.CONTENT_URI_PRUZN,contentValues,"_id="+id,null);
					}
					Toast.makeText(EditPruznachennjaActivity.this, "Збережено", Toast.LENGTH_LONG).show();
					finish();
					
					break;
				case R.id.makeAudio:
					btnAudio = (Button) findViewById(R.id.makeAudio);
					if ( btnAudio.getText().toString().equals("СТОП") )
					{
						/*in1.recordStop(view);*/
						recordStop(view);
						btnAudio.setText("Записати аудіо");
					}
						
					else
					{
						/*in1.recordStart(view, file_Path, file_Name_Audio); */
						recordStart(view);
						btnAudio.setText("СТОП");						
					}
						
					break;
				case R.id.listenAudio:
					btnAudio = (Button) findViewById(R.id.imageButton2);
					if ( btnAudio.getText().toString().equals("СТОП") )
					{
						in1.playStop(view);
						btnAudio.setText("Прослухати запис");
						btnAudio.setBackgroundResource(R.drawable.btn_play);
					}
					else
					{
						in1.playStart(view, file_Path, file_Name_Audio, EditPruznachennjaActivity.this);
						btnAudio.setText("СТОП");
						btnAudio.setBackgroundResource(R.drawable.btn_stop);
					}
					break;
				case R.id.editQuest:
					//Toast.makeText(EditPruznachennjaActivity.this, "Введіть текст запитання", Toast.LENGTH_LONG).show();
					
					/*AlertDialog.Builder*//* alert = new AlertDialog.Builder(EditPruznachennjaActivity.this);
					alert.setTitle("Введіть запитання");
					alert.setMessage("Старий варіант: " + question);
					// Добавим поле ввода
					final EditText input = new EditText(this);
					alert.setView(input);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  question = input.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(EditPruznachennjaActivity.this, "Ви ввели: " + question, Toast.LENGTH_LONG).show();
						  quest.setText(question);
						  }
						});

					alert.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});

						alert.show();*/
					
					break;
				case R.id.editAnsw:
					
					Toast.makeText(EditPruznachennjaActivity.this, "Введіть текст відповіді", Toast.LENGTH_LONG).show();
					
					/* AlertDialog.Builder */ alert = new AlertDialog.Builder(EditPruznachennjaActivity.this);
					alert.setTitle("Введіть відповідь");
					alert.setMessage("Старий варіант: " + newAnsw);
					// Добавим поле ввода
					final EditText input1 = new EditText(this);
					alert.setView(input1);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							newAnsw = input1.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(EditPruznachennjaActivity.this, "Ви ввели: " + newAnsw, Toast.LENGTH_LONG).show();
						  /*answ.setText(newAnsw);*/
						  }
						});

					alert.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});
						alert.show();					
					break;
				case R.id.makeFoto:
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);   // Зображення у файлі fotoUri
					startActivityForResult(intent, PHOTO_INTENT_REQUEST_CODE); // Потрібна функція onActivityResult
					break;
				case R.id.showFoto:
					
					File ff = new File(file_Path + "/" + file_Name_Foto);
					if ( ff.exists() )
					{
						/*Intent*/// intent = new Intent(EditPruznachennjaActivity.this, Your_quest_SHOW_foto.class);
						//intent.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
					//	startActivity(intent);
					}
					else
						Toast.makeText(EditPruznachennjaActivity.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
					break;
			}
		}	catch ( Exception e) 
			{
				e.printStackTrace();
			}
	}
///////////////////////////////////////////////////////////////////////////////////////////

	// /////////////  recordStart  (Начало) /////////////////////////////////////////////////////	
		  public void recordStart(View v) 
		  {
		    try 
		    {
		      releaseRecorder();
		      File outFile = new File(file_Path, file_Name_Audio);
		      if (outFile.exists()) { outFile.delete(); }
		      Log.i("Audio", "Начало записи звука");
		      mediaRecorder = new MediaRecorder();
		      mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		      mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		      mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		      //mediaRecorder.setOutputFile(MO_io.fileName);
		      //fileName = MO_io.fileName + String.valueOf(System.currentTimeMillis()) + ".3gpp";
		      mediaRecorder.setOutputFile(file_Path + "/" + file_Name_Audio);
		      mediaRecorder.prepare();
		      mediaRecorder.start();
		    } catch (Exception e) {
		    	Log.d("Audio", "Прерывание при записи звука в файл");
		    	e.printStackTrace();
		    }
		  }
	// \\\\\\\\  recordStart  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


	// /////////////  recordStop  (НАЧАЛО) /////////////////////////////////////////////////////	
		  public void recordStop(View v) {
			try
			{
				Log.i("Audio", "СТОП - запись");
				if (mediaRecorder != null) { mediaRecorder.stop(); }
			} catch (Exception e) {
				  //Log.d(LOG_TAG, " Прерывание в mediaRecorder.stop()");
				  Log.i("Audio", "Прерывание в mediaRecorder.stop()");
				  e.printStackTrace();
			  }
		  }
	// \\\\\\\\\\\\  recordStop  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


	// /////////////  releaseRecorder  (НАЧАЛО) /////////////////////////////////////////////////////	
		  private void releaseRecorder() {
			    if (mediaRecorder != null) {
			      mediaRecorder.release();
			      mediaRecorder = null;
			    }
			  }
	// \\\\\\\\\\\  releaseRecorder  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


	// /////////////  playStart  (НАЧАЛО) /////////////////////////////////////////////////////	
		  public void playStart(View v) {
		    try {
		      Log.i("Audio"," Воспроизведение ");
		      releasePlayer();
		      File outFile = new File(file_Path, file_Name_Audio);
		      if (outFile.exists() == false) 
		      {Toast.makeText(EditPruznachennjaActivity.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
		      mediaPlayer = new MediaPlayer();
		      mediaPlayer.setDataSource(file_Path + "/" + file_Name_Audio);
		      mediaPlayer.prepare();
		      mediaPlayer.start();
		    } catch (Exception e) {
			   Log.i("Audio", "Прерывание при воспроизведении ");
			   e.printStackTrace();
		    }
		  }
	// \\\\\\\\\\\  playStart  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// /////////////  playStop  (НАЧАЛО) /////////////////////////////////////////////////////	
		  public void playStop(View v) {
		    if (mediaPlayer != null) {
		    	Log.i("Audio", "СТОП при воспроизведении");
		  	    mediaPlayer.stop();
		    }
		  }
	// \\\\\\\\\\\  playStop  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	// /////////////  releasePlayer  (НАЧАЛО) /////////////////////////////////////////////////////	
		  private void releasePlayer() {
		    if (mediaPlayer != null) {
		      mediaPlayer.release();
		      mediaPlayer = null;
		    }
		  }
	// \\\\\\\\\\\  releasePlayer  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	  
	protected void onDestroy()
	{
		super.onDestroy();
		Log.i("Свои вопросы","В \"onDestroy()\" методе");
		releaseRecorder(); /// 
		releasePlayer();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.your_quest__edit, menu);
		return true;
	}

}
