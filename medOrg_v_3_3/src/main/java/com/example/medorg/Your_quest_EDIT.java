package com.example.medorg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MO_io_class.LogMO;

public class Your_quest_EDIT extends BaseActivity {

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
	
/////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_quest_edit);
		
		quest = (EditText) findViewById(R.id.editQuest);
		answ = (EditText) findViewById(R.id.editAnsw);
/*		
		quest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Hide keyboard
                    if (getCurrentFocus() != null) {
                    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
				
		answ.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Hide keyboard
                    if (getCurrentFocus() != null) {
                    	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
*/
		
		/*Intent*/ intent = getIntent();
		
		file_Name = intent.getStringExtra("file_name");
	    question = intent.getStringExtra("quest");
	    file_Path = intent.getStringExtra("path");
	    file_Name_Answ = file_Name.replaceAll(".qst", ".ans");
	    file_Name_Audio = file_Name.replaceAll(".qst", ".3gpp");
	    file_Name_Foto = file_Name.replaceAll(".qst", ".jpg");
	    
		File fotoFile = new File(file_Path + "/" + file_Name_Foto);
		fotoUri = Uri.fromFile(fotoFile);

		/*
	     			// Создаем имя файла
			String timeStamp = String.valueOf(System.currentTimeMillis());
			fileName = MO_io.fileName + String.valueOf(System.currentTimeMillis()) + ".jpg";
			//File newFile = new File(path.getPath() + File.separator + timeStamp + ".jpg");
			File newFile = new File(fileName);
			return Uri.fromFile(newFile);

	     */
	    
	    quest.setText(question);
	    //answ.setText("");
	    newAnsw = "";
	    
		try { // открываем поток для чтения
			  File f1 = new File( file_Path + "/" + file_Name_Answ );
			  if( f1.exists())
			  {
				  BufferedReader br = new BufferedReader(new FileReader(f1));
				  String str = "";
				  // читаем содержимое
				  while ((str = br.readLine()) != null)
				  {
					  LogMO.i("EDIT "+file_Name_Answ, str);
					  newAnsw = newAnsw +  ( (newAnsw.length()>0) ? ("\n" + str) : str);
		    	  }
				  answ.setText(newAnsw);
				  br.close();
			  }
		    } catch (FileNotFoundException e)
		    {
		    	e.printStackTrace();
		    } catch (IOException e)
			{
		      e.printStackTrace();
		    }
	}
	
/////////////////////////////////////////////////////////////////////////////////////
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
					File answFile = new File(file_Path, file_Name_Answ);
				    if (answFile.exists()) { answFile.delete(); }
		      		try {
		      			// открываем поток для записи
		      			BufferedWriter bw = new BufferedWriter(new FileWriter(answFile));
		      			// пишем данные
		      			bw.write(newAnsw);
		      			// закрываем поток
		      			bw.close();
		      			LogMO.i("Ответ записан: " , "Файл: " + answFile.getAbsolutePath() + " " + newAnsw);
		      	    	} catch (IOException e) {
		      	    		e.printStackTrace();
		      	    	}
					
		      		File quesFile = new File(file_Path, file_Name);
				    if (quesFile.exists()) { quesFile.delete(); }
					question = quest.getText().toString();
		      		try {
		      			// открываем поток для записи
		      			BufferedWriter bw1 = new BufferedWriter(new FileWriter(quesFile));
		      			// пишем данные
		      			bw1.write(question);
		      			// закрываем поток
		      			bw1.close();
		      			LogMO.i("Вопрос записан: ",  "Файл: " + quesFile.getAbsolutePath()+ " " + question);
		      	    	} catch (IOException e) {
		      	    		e.printStackTrace();
		      	    	}
					
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
					Toast.makeText(Your_quest_EDIT.this, "Введіть текст запитання", Toast.LENGTH_LONG).show();
					
					/*AlertDialog.Builder*/ alert = new AlertDialog.Builder(Your_quest_EDIT.this);
					alert.setTitle("Введіть запитання");
					alert.setMessage("Старий варіант: " + question);
					// Добавим поле ввода
					final EditText input = new EditText(this);
					alert.setView(input);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  question = input.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Your_quest_EDIT.this, "Ви ввели: " + question, Toast.LENGTH_LONG).show();
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
					Toast.makeText(Your_quest_EDIT.this, "Введіть текст відповіді", Toast.LENGTH_LONG).show();
					
					/* AlertDialog.Builder */ alert = new AlertDialog.Builder(Your_quest_EDIT.this);
					alert.setTitle("Введіть відповідь");
					alert.setMessage("Старий варіант: " + newAnsw);
					// Добавим поле ввода
					final EditText input1 = new EditText(this);
					alert.setView(input1);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							newAnsw = input1.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Your_quest_EDIT.this, "Ви ввели: " + newAnsw, Toast.LENGTH_LONG).show();
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
						/*Intent*/ intent = new Intent(Your_quest_EDIT.this, Your_quest_SHOW_foto.class); 
						intent.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
						startActivity(intent);
					}
					else
						Toast.makeText(Your_quest_EDIT.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
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
				Toast.makeText(Your_quest_EDIT.this, "Готово", Toast.LENGTH_LONG).show();
				//mImage.setImageURI(mUri);
			}
			else if (resultCode == RESULT_CANCELED) 
				Toast.makeText(Your_quest_EDIT.this, "Відмінено", Toast.LENGTH_LONG).show();
			else 
				Toast.makeText(Your_quest_EDIT.this, "Помилка", Toast.LENGTH_LONG).show();
		}
	}	
// \\\\\\\\\\\\\  onActivityResult  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// /////////////  dlgGetString  (НАЧАЛО) //////////////////////////////////////////////////
	public String dlgGetString (String title, String msg)
	{
		//final String val;
		AlertDialog.Builder alert = new AlertDialog.Builder(Your_quest_EDIT.this);
		alert.setTitle(title);
		alert.setMessage(msg);
		// Добавим поле ввода
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				fromDialog = input.getText().toString(); // fromDialog змінна класу
				// Получили значение введенных данных!
				Toast.makeText(Your_quest_EDIT.this, "Ви ввели: " + fromDialog, Toast.LENGTH_LONG).show();
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
	      LogMO.i("Audio", "Начало записи звука");
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
	    	LogMO.d("Audio", "Прерывание при записи звука в файл");
	    	e.printStackTrace();
	    }
	  }
// \\\\\\\\  recordStart  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


// /////////////  recordStop  (НАЧАЛО) /////////////////////////////////////////////////////	
	  public void recordStop(View v) {
		try
		{
			LogMO.i("Audio", "СТОП - запись");
			if (mediaRecorder != null) { mediaRecorder.stop(); }
		} catch (Exception e) {
			  //Log.d(LOG_TAG, " Прерывание в mediaRecorder.stop()");
			  LogMO.i("Audio", "Прерывание в mediaRecorder.stop()");
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
	      LogMO.i("Audio"," Воспроизведение ");
	      releasePlayer();
	      File outFile = new File(file_Path, file_Name_Audio);
	      if (outFile.exists() == false) 
	      {Toast.makeText(Your_quest_EDIT.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
	      mediaPlayer = new MediaPlayer();
	      mediaPlayer.setDataSource(file_Path + "/" + file_Name_Audio);
	      mediaPlayer.prepare();
	      mediaPlayer.start();
	    } catch (Exception e) {
		   LogMO.i("Audio", "Прерывание при воспроизведении ");
		   e.printStackTrace();
	    }
	  }
// \\\\\\\\\\\  playStart  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// /////////////  playStop  (НАЧАЛО) /////////////////////////////////////////////////////	
	  public void playStop(View v) {
	    if (mediaPlayer != null) {
	    	LogMO.i("Audio", "СТОП при воспроизведении");
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
		getMenuInflater().inflate(R.menu.your_quest__edit, menu);
		return true;
	}

}
