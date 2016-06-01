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

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

public class YourQuestEditActivity extends BaseActivity {

	Intent intent;
	
	EditText quest;
	EditText answ;
	
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
	
	//AlertDialog.Builder alert;
	String id;

/////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_quest_edit);
		
		quest = (EditText) findViewById(R.id.editQuest);
		answ = (EditText) findViewById(R.id.editAnsw);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		Cursor c = getContentResolver().query(Params.CONTENT_URI_QUESTIONS,null,"_id=="+id,null,null);
		c.moveToFirst();
	    quest.setText(c.getString(c.getColumnIndex(Params.QUESTION_TEXT)));
		answ.setText(c.getString(c.getColumnIndex(Params.QUESTION_ANSWER)));
	}

	public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	{
		AlertDialog.Builder alert;
		try 
		{
			switch (view.getId())
			{
				case R.id.clear_answer:
					answ.setText("");
					newAnsw = "";
					break;		      
				case R.id.saveAnsw:

					newAnsw = answ.getText().toString();
					question = quest.getText().toString();
					ContentValues contentValues = new ContentValues();
					contentValues.put(Params.QUESTION_TEXT, question);
					contentValues.put(Params.QUESTION_ANSWER, newAnsw);
					getContentResolver().update(Params.CONTENT_URI_QUESTIONS,contentValues,"_id="+id,null);

					break;
				case R.id.makeAudio:
					btnAudio = (Button) findViewById(R.id.makeAudio);
					if ( btnAudio.getText().toString().equals("СТОП") )
					{
						recordStop(view);
						btnAudio.setText("Записати аудіо");
					}
						
					else
					{
						recordStart(view);
						btnAudio.setText("СТОП");						
					}
						
					break;
				case R.id.listenAudio:
					btnAudio = (Button) findViewById(R.id.listenAudio);
					if ( btnAudio.getText().toString().equals("СТОП") )
					{
						playStop(view);
						btnAudio.setText("Прослухати запис");
					}
					else
					{
						playStart(view);
						btnAudio.setText("СТОП");
					}
					break;
				case R.id.editQuest:
					Toast.makeText(YourQuestEditActivity.this, "Введіть текст запитання", Toast.LENGTH_LONG).show();
					
					/*AlertDialog.Builder*/ alert = new AlertDialog.Builder(YourQuestEditActivity.this);
					alert.setTitle("Введіть запитання");
					alert.setMessage("Старий варіант: " + question);
					// Добавим поле ввода
					final EditText input = new EditText(this);
					alert.setView(input);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  question = input.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(YourQuestEditActivity.this, "Ви ввели: " + question, Toast.LENGTH_LONG).show();
						  quest.setText(question);
						  }
						});

					alert.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});

						alert.show();
					
					break;
				case R.id.editAnsw:
					/*
					fromDialog = dlgGetString("Введіть відповідь", "Старе значення: " + answ.getText().toString());
					if( fromDialog.length() > 0 )
					{
						newAnsw = fromDialog;
						answ.setText(newAnsw);
					} */
					Toast.makeText(YourQuestEditActivity.this, "Введіть текст відповіді", Toast.LENGTH_LONG).show();
					
					/* AlertDialog.Builder */ alert = new AlertDialog.Builder(YourQuestEditActivity.this);
					alert.setTitle("Введіть відповідь");
					alert.setMessage("Старий варіант: " + newAnsw);
					// Добавим поле ввода
					final EditText input1 = new EditText(this);
					alert.setView(input1);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							newAnsw = input1.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(YourQuestEditActivity.this, "Ви ввели: " + newAnsw, Toast.LENGTH_LONG).show();
						  answ.setText(newAnsw);
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
						/*Intent*//* intent = new Intent(YourQuestEditActivity.this, Your_quest_SHOW_foto.class);
						intent.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
						startActivity(intent);*/
					}
					else
						Toast.makeText(YourQuestEditActivity.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
					break;
			}
		}	catch ( Exception e) 
			{
				e.printStackTrace();
			}
	}
///////////////////////////////////////////////////////////////////////////////////////////

	
// /////////////  onActivityResult  (НАЧАЛО) //////////////////////////////////////////////////
	@Override
	public void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_INTENT_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(YourQuestEditActivity.this, "Готово", Toast.LENGTH_LONG).show();
				//mImage.setImageURI(mUri);
			}
			else if (resultCode == RESULT_CANCELED) 
				Toast.makeText(YourQuestEditActivity.this, "Відмінено", Toast.LENGTH_LONG).show();
			else 
				Toast.makeText(YourQuestEditActivity.this, "Помилка", Toast.LENGTH_LONG).show();
		}
	}	
// \\\\\\\\\\\\\  onActivityResult  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// /////////////  dlgGetString  (НАЧАЛО) //////////////////////////////////////////////////
	public String dlgGetString (String title, String msg)
	{
		//final String val;
		AlertDialog.Builder alert = new AlertDialog.Builder(YourQuestEditActivity.this);
		alert.setTitle(title);
		alert.setMessage(msg);
		// Добавим поле ввода
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				fromDialog = input.getText().toString(); // fromDialog змінна класу
				// Получили значение введенных данных!
				Toast.makeText(YourQuestEditActivity.this, "Ви ввели: " + fromDialog, Toast.LENGTH_LONG).show();
			}
		});

		alert.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int whichButton) {
			    // Если отменили.
				 fromDialog = "";
			  }
			});

		alert.show();		
		return fromDialog;
	}
// \\\\\\\\\\\\\  dlgGetString  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

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
	      {Toast.makeText(YourQuestEditActivity.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
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
