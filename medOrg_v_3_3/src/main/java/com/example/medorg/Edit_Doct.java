package com.example.medorg;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.MO_io_class.LogMO;

public class Edit_Doct extends Activity {
	Intent intent;
	
	EditText quest;
	EditText answ;
	EditText spec;
	EditText about;
	EditText tel;
	
	
	String file_Name;
	String file_Path;
	String question;
	String newAnsw;
	String special;
	String telephone;
	String aboutl;
	String file_Name_Answ;
	String file_Name_Audio;
	String file_Name_Foto;
	String file_Name_spec;

	 final String LOG_TAG = "myLogs";
	
	String fromDialog;
	
	final String FILENAME = "file";
	
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
		setContentView(R.layout.edit_doct);
		
		
		quest = (EditText) findViewById(R.id.editQuest);
		spec = (EditText) findViewById(R.id.EditSpec);
		about = (EditText) findViewById(R.id.EditAbout);
		tel = (EditText) findViewById(R.id.EditTel);
		
		answ = (EditText) findViewById(R.id.editAnsw);
		
		Intent intent = getIntent();
		
		file_Name = intent.getStringExtra("file_name");
	    question = intent.getStringExtra("quest");
	    file_Path = intent.getStringExtra("path");
	    file_Name_Answ = file_Name.replaceAll(".dct", ".dct");
	    file_Name_Audio = file_Name.replaceAll(".dct", ".3gpp");
	    file_Name_Foto = file_Name.replaceAll(".dct", ".jpg");
	    file_Name_spec = file_Name.replaceAll(".dct", ".spc");
	    
	    //writeFile(file_Path, "1", "Hello");
	    String ReadStr = readFile(file_Path, file_Name);
	    String[] s = ReadStr.split("#");
	    
	    if (s[0]!=""){quest.setText(s[0]);} 
	    if (s.length>1) {spec.setText(s[1]);}
	    if (s.length>1) {about.setText(s[2]);}
	    if (s.length>2) {tel.setText(s[3]);}
	    
		File fotoFile = new File(file_Path + "/" + file_Name_Foto);
		fotoUri = Uri.fromFile(fotoFile);
		
	    quest.setText(question);
	    
	    newAnsw = "";
	    
		/*try { // открываем поток для чтения
			  File f1 = new File( file_Path + "/" + file_Name_spec );
			  if( f1.exists())
			  {
				  BufferedReader br = new BufferedReader(new FileReader(f1));
				  String str = "";
				  // читаем содержимое
				  while ((str = br.readLine()) != null)
				  {
					  LogMO.i("EDIT "+file_Name_spec, str);
					  str = str +  ( (str.length()>0) ? ("\n" + str) : str);
		    	  }
				  spec.setText("Hello");
				  br.close();
			  }
		    } catch (FileNotFoundException e)
		    {
		    	e.printStackTrace();
		    } catch (IOException e)
			{
		      e.printStackTrace();
		    }*/
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
				case R.id.clear_answer:
					quest.setText("");
					newAnsw = "";
					break;		
					
				case R.id.saveAnsw:
					/*newAnsw = quest.getText().toString();
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
		      	    	}*/
					
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
		      		
		      		/*File specFile = new File(file_Path, file_Name_spec);
				    if (specFile.exists()) { specFile.delete(); }
					special = spec.getText().toString();
		      		try {
		      			// открываем поток для записи
		      			BufferedWriter bw1 = new BufferedWriter(new FileWriter(specFile));
		      			// пишем данные
		      			bw1.write(special);
		      			// закрываем поток
		      			bw1.close();
		      			LogMO.i("Вопрос записан: ",  "Файл: " + quesFile.getAbsolutePath()+ " " + question);
		      	    	} catch (IOException e) {
		      	    		e.printStackTrace();
		      	    	}
		      		
		      		Toast.makeText(Edit_Doct.this, "Збережено", Toast.LENGTH_LONG).show();*/
		      		question = quest.getText().toString();
		      		special = spec.getText().toString();
		      		aboutl = about.getText().toString();
		      		telephone = tel.getText().toString();
		      		String out = question+ "#" + special + "#" + aboutl +"#" + telephone;
		      		writeFile(file_Path, file_Name, out);
					
					break;
				case R.id.makeAudio:
					btnAudio = (Button) findViewById(R.id.makeAudio);
					if ( btnAudio.getText().toString().equals("СТОП") )
					{
						in1.recordStop(view);
						recordStop(view);
						btnAudio.setText("Записати аудіо");
					}
						
					else
					{
						in1.recordStart(view, file_Path, file_Name_Audio); 
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
						in1.playStart(view, file_Path, file_Name_Audio, Edit_Doct.this);
						btnAudio.setText("СТОП");
						btnAudio.setBackgroundResource(R.drawable.btn_stop);
					}
					break;
				case R.id.editQuest:
					Toast.makeText(Edit_Doct.this, "Введіть нове ПІБ", Toast.LENGTH_LONG).show();
					
					AlertDialog.Builder alert1 = new AlertDialog.Builder(Edit_Doct.this);
					alert1.setTitle("Введіть ПІП");
					alert1.setMessage("Старий варіант: " + question);
					// Добавим поле ввода
					final EditText input = new EditText(this);
					alert1.setView(input);
					alert1.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  question = input.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Edit_Doct.this, "Ви ввели: " + question, Toast.LENGTH_LONG).show();
						  quest.setText(question);
						  }
						});

					alert1.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});

						alert1.show();
					
					break;
				
			case R.id.EditSpec:
					Toast.makeText(Edit_Doct.this, "Введіть нову спеціальність", Toast.LENGTH_LONG).show();
					AlertDialog.Builder alert3 = new AlertDialog.Builder(Edit_Doct.this);
					alert3.setTitle("Введіть ПІП");
					special = spec.getText().toString();
					alert3.setMessage("Старий варіант: " + special);
					// Добавим поле ввода
					final EditText input3 = new EditText(this);
					alert3.setView(input3);
					alert3.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  special = input3.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Edit_Doct.this, "Ви ввели: " + special, Toast.LENGTH_LONG).show();
						  spec.setText(special);
						  }
						});

					alert3.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});

						alert3.show();
					
					break;

			case R.id.EditTel:
				Toast.makeText(Edit_Doct.this, "Введіть новий телефон", Toast.LENGTH_LONG).show();
				AlertDialog.Builder alert4 = new AlertDialog.Builder(Edit_Doct.this);
				alert4.setTitle("Введіть телефон");
				telephone = tel.getText().toString();
				alert4.setMessage("Старий варіант: " + telephone);
				// Добавим поле ввода
				final EditText input4 = new EditText(this);
				alert4.setView(input4);
				alert4.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						telephone = input4.getText().toString();
					  // Получили значение введенных данных!
					  Toast.makeText(Edit_Doct.this, "Ви ввели: " + telephone, Toast.LENGTH_LONG).show();
					  tel.setText(telephone);
					  }
					});

				alert4.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int whichButton) {
					    // Если отменили.
					  }
					});

					alert4.show();
				
				break;

				case R.id.EditAbout:
					
					Toast.makeText(Edit_Doct.this, "Введіть дані про лікаря", Toast.LENGTH_LONG).show();
					
					 AlertDialog.Builder  alert2 = new AlertDialog.Builder(Edit_Doct.this);
					alert2.setTitle("Введіть про лікаря");
					aboutl = about.getText().toString();
					alert2.setMessage("Старий варіант: " + aboutl);
					// Добавим поле ввода
					final EditText input1 = new EditText(this);
					alert2.setView(input1);
					alert2.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							aboutl = input1.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Edit_Doct.this, "Ви ввели: " + aboutl, Toast.LENGTH_LONG).show();
						  about.setText(aboutl);
						  }
						});

					alert2.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили.
						  }
						});
						alert2.show();					
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
						Intent intent1 = new Intent(Edit_Doct.this, Your_quest_SHOW_foto.class); 
						intent1.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
						startActivity(intent1);
					}
					else
						Toast.makeText(Edit_Doct.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
					break;
				
				case R.id.Phone:
					telephone = tel.getText().toString();
					Uri uri = Uri.parse("tel:"+telephone);
					Intent intent1 = new Intent(Intent.ACTION_DIAL);
					intent1.setData(uri);
					startActivity(intent1);
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
		      {Toast.makeText(Edit_Doct.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
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
	
	public void writeFile(String Path, String File_name, String Content) {
		//File f1 = new File( Path + "/" + File_name + ".txt" );
		File f1 = new File( Path + "/" + File_name);
	    try {
	      // отрываем поток для записи
	      BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
	      // пишем данные
	      bw.write(Content);
	      // закрываем поток
	      bw.close();
	      Log.d(LOG_TAG, "Файл записан");
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	
	public String readFile(String Path, String File_name) {
		  //File f1 = new File( Path + "/" + File_name + ".txt" );
		File f1 = new File( Path + "/" + File_name);
		  String str = "";
		  String res = "";
	    try {
	      // открываем поток для чтения
	      BufferedReader br = new BufferedReader(new FileReader(f1));
	      
	      // читаем содержимое
	      while ((str = br.readLine()) != null) {
	        Log.d(LOG_TAG, str);
	        res+=str;
	      }
	      //res=str;
	      
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return res;
	  }

}
