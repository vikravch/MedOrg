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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;
import com.example.SwipeDetector;

public class Standard_quest_EDIT_Activity extends BaseActivity {

	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	LinearLayout linLayout_QuestStandard; // главный Layout
	LinearLayout linLayout_for_Buttons;   // для Buttons внутри главного Layout
	LinearLayout linLayout_for_Audio;	  // для Audio внутри linLayout_for_Buttons
	LinearLayout linLayout_for_Foto;	  // для Foto внутри linLayout_for_Buttons
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	Boolean is_Buttons_created = false;
	Boolean is_linLayout_for_Buttons_created = false;
	Boolean is_prohibit_for_Buttons_remove = false;
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	Button btn_Listen;
	Button btn_Record;
	Button btn_makeFoto;
	Button btn_showFoto;

	Intent intent;

	MO_io_class MO_io;

	EditText quest;
	EditText answ;

	String file_Name;
	String file_Path;
	String question;
	String newAnsw;
	String file_Name_Answ;
	String file_Name_Audio;
	String file_Name_Foto;
	
	String What;
	
	int 	N_grp;			// Номер группы вопросов
	int		Checked_quest_num[];
	int		N_quest; 		// Номер вопроса в массиве Checked_quest_num
	String	Checked_quests[];
	
	String [] FileNames_List;

	String fromDialog;

	private MediaRecorder mediaRecorder;
	private MediaPlayer mediaPlayer;

//	Button btnAudio;
	
	Uri fotoUri;  // Для файла фото

	private static final int PHOTO_INTENT_REQUEST_CODE = 100;

	private GestureDetector gestureDetector;	// Для жестов

	//AlertDialog.Builder alert;
	
///////////onCreate/////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_standard_quest_edit);
		
		quest = (EditText) findViewById(R.id.editQuestStandard);
		answ = (EditText) findViewById(R.id.editAnswStandard);
		
		MO_io =  new MO_io_class("0", "");		//  Таких файлов не бывает !!!!  
		file_Path = MO_io.filePath;
				
		/*Intent*/ intent = getIntent();
		

/*		file_Name = intent.getStringExtra("file_name");
	    question = intent.getStringExtra("quest");
	    file_Path = intent.getStringExtra("path");
*/

		What = intent.getStringExtra("What");
		
		if ( What.equals("Standard") )
		{
/*
	 		intent.putExtra("What", "Standard");	  	// Тип вопросов
	 		intent.putExtra("Checked_quests", Checked_quests);			// Выбранные вопросы
	 		intent.putExtra("N_grp", N_grp);  							// Номер группы
	 		intent.putExtra("Checked_quest_num", Checked_quest_num);	// Массив номеров выбранных вопросов
*/			
			N_grp = intent.getIntExtra("N_grp",0);
			Checked_quest_num = intent.getIntArrayExtra("Checked_quest_num");	// Номера выбранных вопросов
			Checked_quests = intent.getStringArrayExtra("Checked_quests");		// Выбранные вопросы
			
			N_quest = 0;
		    quest.setText(Checked_quests[N_quest]);	//  Вопрос - на экран

			// Создаем имя файла
			file_Name = "MO_QS_G_" + N_grp + "_Q_" + Checked_quest_num[N_quest];
		}
		else if ( What.equals("My"))
		{ 	
			//LogMO.i("My","My");
/*
			intent.putExtra("What", "My");	  					// Тип вопросов
			intent.putExtra("file_name", FileNames_List);		// Имена файлов
			intent.putExtra("N_qst", position);  				// Номер имени файла в FileNames_List
*/			
			FileNames_List = intent.getStringArrayExtra("file_name");
			N_quest = intent.getIntExtra("N_qst",0);
			file_Name = FileNames_List[N_quest].replace(".qst", "");
			//LogMO.i("file_Name =",file_Name);
			LogMO.i("FileNames_List["+N_quest+"]", file_Name);
			
			question = read_String_file(file_Path + "/" + FileNames_List[N_quest]);
			quest.setText(question);
	
		}

		// System.exit(-1);
			
			file_Name_Answ = file_Name + ".ans";
			file_Name_Audio = file_Name + ".3gpp";
			file_Name_Foto = file_Name + ".jpg";
	    
		File fotoFile = new File(file_Path + "/" + file_Name_Foto);
		fotoUri = Uri.fromFile(fotoFile);

	    //newAnsw = "";
	    
	    newAnsw = read_String_file(file_Path + "/" + file_Name_Answ);
	    answ.setText(newAnsw);

/*		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} System.exit(-1);
*/
		// Функция initGestureDetector() определена в данной активности
		gestureDetector = initGestureDetector();		// Для жестов
	
		LogMO.i("Жесты!!!","В \"onCreate()\" методе: Создан объект gestureDetector");

		linLayout_QuestStandard = (LinearLayout)findViewById(R.id.LinearLayout_QuestStandard);	// LinearLayout_QuestStandard"
		
		if( linLayout_QuestStandard == null ) {LogMO.i("В \"onCreate()\" методе:!!!", "  Не удалось создать объект View view = findViewById();");}
		else LogMO.i("Жесты!!!","В \"onCreate()\" методе: Создан объект linLayout_QuestStandard = findViewById();");
		
		//LogMO.i("Путь к файлам", io.filePath);
		
		linLayout_QuestStandard.setOnTouchListener(new View.OnTouchListener()
		    	{
		        	public boolean onTouch(View v, MotionEvent event)
		        					{
										//long t = (long)event.getDownTime();
		        						//LogMO.i("OnTouchListener!!!","В \"onTouch()\" методе. DownTime = " + t + " X=" + event.getX() + " Y=" + event.getY());
		        						return gestureDetector.onTouchEvent(event);
		        					}
		    	});

		LogMO.i("Жесты!!!","В \"onCreate()\" методе: Установлен обработчик linLayout_QuestStandard.setOnTouchListener()");

		linLayout_QuestStandard.setOnClickListener(new OnClickListener() {
		        public void onClick(View arg0) {
		        	LogMO.i("Жесты!!!","В \"OnClickListener()\"");
		        	// Создаем LinearLayout для кнопок
		    		if( ! is_linLayout_for_Buttons_created )
		        	{
		        		create_linLayout_for_Buttons();
		        	}
		            // создаем Buttons и добавляем в LinearLayout для кнопок
		        	if(! is_Buttons_created)
		        	{
		        		create_Buttons();
		        	}
		            		            
		            // Создадим новый поток для удаления кнопок через несколько секунд
		            Thread thread_for_remove = new Thread(new Runnable() 
		            {
		                public void run() 
		                {
		                  try	{
		                	  		//TimeUnit.SECONDS.sleep(5);
		                	  		//runOnUiThread(runn_for_remove);  // Activity.runOnUiThread(Runnable)
		                       		linLayout_for_Buttons.postDelayed(runn_for_remove, 3000);  // View.post(Runnable)
		                       		//linLayout_for_quests.post(runn2);  // View.postDelayed(Runnable, long)
		                  		} 	//catch (InterruptedException e) // для TimeUnit.SECONDS.sleep(5);
		                  			catch (Exception e) // если без TimeUnit.SECONDS.sleep(0);
		                  			{
		                  				e.printStackTrace();
		                  			}
		                }
		            }									 );
		            
		            thread_for_remove.start();
		        }
		    });
/**/
	}
//////////onCreate (Конец)//////////////////////////////////////////////////////////////////

// Задача для нового потока (Начало)
	 Runnable runn_for_remove = new Runnable() {
		    public void run() 
		    {
		    	if( ! is_prohibit_for_Buttons_remove )
		    	{
		    		linLayout_for_Buttons.removeAllViews(); // удалять все созданное
		    		is_Buttons_created = false;
		    	}
		    }
		  };
// Задача для нового потока (Конец)

//////////onClick (Начало)//////////////////////////////////////////////////////////////////
	public boolean onClick(View view)  // Это - обработчик для кнопок с методом onClick
	{
		AlertDialog.Builder alert;
		try 
		{
			switch (view.getId())
			{
/*				case R.id.clear_answerStandard: // clear_answerStandard
					answ.setText("");
					newAnsw = "";
					break;		      
*/
/*				case R.id.saveAnswStandard:		// saveAnswStandard
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
*/					
/*
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
*/
/*
				case R.id.makeAudioStandard:	// makeAudioStandard	// Для кнопок нужно обрабатывать взаимоисключающие состояния  !!!! 
					btnAudio = (Button) findViewById(R.id.makeAudioStandard);
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
				case R.id.listenAudioStandard:		// listenAudioStandard
					btnAudio = (Button) findViewById(R.id.listenAudioStandard);
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
*/
/*
				case R.id.editQuest:
					Toast.makeText(Standard_quest_EDIT_Activity.this, "Введіть текст запитання", Toast.LENGTH_LONG).show();
					
					alert = new AlertDialog.Builder(Standard_quest_EDIT_Activity.this);
					alert.setTitle("Введіть запитання");
					alert.setMessage("Старий варіант: " + question);
					// Добавим поле ввода
					final EditText input = new EditText(this);
					alert.setView(input);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  question = input.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Standard_quest_EDIT_Activity.this, "Ви ввели: " + question, Toast.LENGTH_LONG).show();
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
				*/
				case R.id.editAnswStandard:		// editAnswStandard - не кнопка, а EditText
					/*
					fromDialog = dlgGetString("Введіть відповідь", "Старе значення: " + answ.getText().toString());
					if( fromDialog.length() > 0 )
					{
						newAnsw = fromDialog;
						answ.setText(newAnsw);
					} */
					Toast.makeText(getApplicationContext(), "Введіть текст відповіді", Toast.LENGTH_LONG).show();
					
					// AlertDialog.Builder 
					alert = new AlertDialog.Builder(Standard_quest_EDIT_Activity.this);
					alert.setTitle("Введіть відповідь");
					alert.setMessage("Старий варіант: " + newAnsw);
					// Добавим поле ввода
					final EditText input1 = new EditText(this);
					alert.setView(input1);
					alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						  newAnsw = input1.getText().toString();
						  // Получили значение введенных данных!
						  Toast.makeText(Standard_quest_EDIT_Activity.this, "Ви ввели: " + newAnsw, Toast.LENGTH_LONG).show();
						  answ.setText(newAnsw);

							//newAnsw = answ.getText().toString();
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

						  }
						});

					alert.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
						 public void onClick(DialogInterface dialog, int whichButton) {
						    // Если отменили
						  }
						});
						alert.show();					
					break;
/*				case R.id.makeFotoStandard:		// makeFotoStandard
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);   // Зображення у файлі fotoUri
					startActivityForResult(intent, PHOTO_INTENT_REQUEST_CODE); // Потрібна функція onActivityResult
					break;
				case R.id.showFotoStandard:			// showFotoStandard
					
					File ff = new File(file_Path + "/" + file_Name_Foto);
					if ( ff.exists() )
					{
						intent = new Intent(Standard_quest_EDIT_Activity.this, Your_quest_SHOW_foto.class); 
						intent.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
						startActivity(intent);
					}
					else
						Toast.makeText(Standard_quest_EDIT_Activity.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
					break;
*/			}
		}	catch ( Exception e) 
			{
				e.printStackTrace();
			}
		return false;
	}
/////////onClick (Конец)/////////////////////////////////////////////////////////////////////////
	
// /////////////  onActivityResult  (НАЧАЛО) //////////////////////////////////////////////////
	@Override
	public void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_INTENT_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(Standard_quest_EDIT_Activity.this, "Готово", Toast.LENGTH_LONG).show();
				//mImage.setImageURI(mUri);
			}
			else if (resultCode == RESULT_CANCELED) 
				Toast.makeText(Standard_quest_EDIT_Activity.this, "Відмінено", Toast.LENGTH_LONG).show();
			else 
				Toast.makeText(Standard_quest_EDIT_Activity.this, "Помилка", Toast.LENGTH_LONG).show();
		}
	}	
// \\\\\\\\\\\\\  onActivityResult  (КОНЕЦ) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// /////////////  dlgGetString  (НАЧАЛО) //////////////////////////////////////////////////
	public String dlgGetString (String title, String msg)
	{
		//final String val;
		AlertDialog.Builder alert = new AlertDialog.Builder(Standard_quest_EDIT_Activity.this);
		alert.setTitle(title);
		alert.setMessage(msg);
		// Добавим поле ввода
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				fromDialog = input.getText().toString(); // fromDialog змінна класу
				// Получили значение введенных данных!
				Toast.makeText(Standard_quest_EDIT_Activity.this, "Ви ввели: " + fromDialog, Toast.LENGTH_LONG).show();
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
	      {Toast.makeText(Standard_quest_EDIT_Activity.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
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
		Log.i("Стандартные вопросы","В \"onDestroy()\" методе");
		releaseRecorder(); /// 
		releasePlayer();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.your_quest__edit, menu);
		return true;
	}

// \\\\\\\\\\\ initGestureDetector (Start) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	

		private GestureDetector initGestureDetector()
		{  return new GestureDetector(this, new SimpleOnGestureListener()
			{
		        private SwipeDetector detector = new SwipeDetector();

		        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		        {	//showToast("onFling");
		        	//linLayout_for_Buttons.removeAllViews();
		            try {	/*  // onFling вызывается только для жеста
		                	if (detector.isNotSwipe(e1, e2, velocityX, velocityY))
		                	{
		                		showToast("No Swipe");
		                		return false;
		                	}
		                	else */
		            		if (detector.isSwipeDown(e1, e2, velocityY)) 
		                	{	
		                		// Intent intent = new Intent(MainActivity.this, Ativity2.class);
		    					// startActivity(intent);
		                		showToast("Down Swipe");
		                		return false;
		                	}
		                	else 	
		                		if (detector.isSwipeUp(e1, e2, velocityY))
		                		{	
		                			showToast("Up Swipe");
		                			return false;
		                		}
		                		else 
		                			if (detector.isSwipeLeft(e1, e2, velocityX)) 
		                			{	
		                				showToast("Left Swipe");
	                					Animation anim = null;
	                			        LinearLayout rl;
	                			        rl = (LinearLayout) findViewById(R.id.LinearLayout_QuestStandard);
	                			        anim = AnimationUtils.loadAnimation(Standard_quest_EDIT_Activity.this, R.anim.anim_left_swipe_into_standard_quest_edit);
	                			        rl.startAnimation(anim);
	                			        if( What.equals("Standard") )
	                			        {
	                			        	if( N_quest == (Checked_quest_num.length-1) ) N_quest =  0;
	                			        	else N_quest++;
	                			        	quest.setText(Checked_quests[N_quest]);	//  Вопрос - на экран
	                			        	file_Name = "MO_QS_G_" + N_grp + "_Q_" + Checked_quest_num[N_quest];	                			        	
	                			        }
	                			        else if( What.equals("My") )
	                			        { 	
	                			        	if ( N_quest == (FileNames_List.length-1) ) N_quest =  0;
	                			        	else N_quest++;
	                						question = read_String_file(file_Path + "/" + FileNames_List[N_quest]);
	                						quest.setText(question);
	                			        	file_Name = FileNames_List[N_quest].replace(".qst", "");
	                			        }
	                			        file_Name_Answ = file_Name + ".ans";
	                				    file_Name_Audio = file_Name + ".3gpp";
	                				    file_Name_Foto = file_Name + ".jpg";
	                				    
	                					File fotoFile = new File(file_Path + "/" + file_Name_Foto);
	                					fotoUri = Uri.fromFile(fotoFile);

	                				    newAnsw = read_String_file(file_Path + "/" + file_Name_Answ);;
	                				    answ.setText(newAnsw);
		                			}
		                			else 	
		                				if (detector.isSwipeRight(e1, e2, velocityX))
		                				{	
		                			        showToast("Right Swipe");
		    	                			// Intent intent = new Intent(MainActivity.this, Ativity2.class);
		    	                			// startActivity(intent);
		                					Animation anim = null;
		                			        LinearLayout rl;
		                			        rl = (LinearLayout) findViewById(R.id.LinearLayout_QuestStandard);
		           		                	/* 				
		                			        anim = AnimationUtils.loadAnimation(Standard_quest_EDIT_Activity.this, R.anim.anim_right_swipe_out_standard_quest_edit);
		                			        rl.startAnimation(anim);
		                		          					
		                					anim = null;
		                					rl = null;
		                					for(int i=0; i<100000000; i++){}
		                					TimeUnit.SECONDS.sleep(5);
		                					*/		                					
		                					
		                			        anim = AnimationUtils.loadAnimation(Standard_quest_EDIT_Activity.this, R.anim.anim_right_swipe_into_standard_quest_edit);
		                			        rl.startAnimation(anim);
		                			      
		                			        /*
		                			    	int 	N_grp;			// Номер группы вопросов
		                			    	int		Checked_quest_num[];
		                			    	int		N_quest; 		// Номер вопроса в массиве Checked_quest_num
		                			    	String	Checked_quests[];
											*/
		                			        if( What.equals("Standard") )
		                			        {
		                			        	if( N_quest == 0 ) N_quest = Checked_quest_num.length-1;
		                			        	else N_quest--;
		                			        	quest.setText(Checked_quests[N_quest]);	//  Вопрос - на экран
		                			        	file_Name = "MO_QS_G_" + N_grp + "_Q_" + Checked_quest_num[N_quest];	                			        	
		                			        }
		                			        else if( What.equals("My") )
		                			        { 	
		                			        	if ( N_quest == 0 ) N_quest = FileNames_List.length-1;
		                			        	else N_quest--;
		                						question = read_String_file(file_Path + "/" + FileNames_List[N_quest]);
		                						quest.setText(question);
		                			        	file_Name = FileNames_List[N_quest].replace(".qst", "");
		                			        }
		                			        // Создаем имя файла
		                					file_Name_Answ = file_Name + ".ans";
		                				    file_Name_Audio = file_Name + ".3gpp";
		                				    file_Name_Foto = file_Name + ".jpg";
		                				    
		                					File fotoFile = new File(file_Path + "/" + file_Name_Foto);
		                					fotoUri = Uri.fromFile(fotoFile);

		                					newAnsw = read_String_file(file_Path + "/" + file_Name_Answ);;
		                				    answ.setText(newAnsw);
		                /*				    
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

*/
		                				}
		                				else
		                				{
		                					showToast("Касание");
		                				}
				        	linLayout_for_Buttons.removeAllViews();
		            	} catch (Exception e) {} //for now, ignore

		            //linLayout_for_Buttons.removeAllViews();

		            return false;
		        }

		        private void showToast(String phrase){
		            Toast.makeText(getApplicationContext(), phrase, Toast.LENGTH_SHORT).show();
		        }
		    });
		}
// \\\\\\\\\\\\\\\initGestureDetector (End) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	

// \\\\\\\\\\\\\\\ create_linLayout_for_Buttons (Start) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		private void create_linLayout_for_Buttons()
		{
			linLayout_QuestStandard = (LinearLayout)
					  findViewById(R.id.LinearLayout_QuestStandard);
	        LinearLayout.LayoutParams linLayout_LayoutParam_for_quests;
	        // создание LinearLayout для Buttons внутри главного Layout
	        linLayout_for_Buttons = new LinearLayout(linLayout_QuestStandard.getContext());
	        // установим вертикальную ориентацию
	        linLayout_for_Buttons.setOrientation(LinearLayout.VERTICAL);
	        // создаем LayoutParams  
	        LayoutParams linLayoutParam_for_quests = 
	            		new LayoutParams(LayoutParams.MATCH_PARENT,
	            						 LayoutParams.WRAP_CONTENT);
	        linLayout_QuestStandard.addView(linLayout_for_Buttons, linLayoutParam_for_quests);
	        linLayout_LayoutParam_for_quests = (LinearLayout.LayoutParams) linLayout_for_Buttons.getLayoutParams();
	        linLayout_LayoutParam_for_quests.weight = 1;
	        is_linLayout_for_Buttons_created = true;
		}
// \\\\\\\\\\\\\\\create_linLayout_for_Buttons (End) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	

// \\\\\\\\\\\\\\\ create_Buttons (Start) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  private void create_Buttons()
  {
	  Drawable drw_play = getResources().getDrawable(R.drawable.btn_play);
		Drawable drw_rec = getResources().getDrawable(R.drawable.btn_record);
		Drawable drw_camera = getResources().getDrawable(R.drawable.btn_camera);
		Drawable drw_photo = getResources().getDrawable(R.drawable.btn_view);
	if( is_linLayout_for_Buttons_created )
	{
		// linLayout_for_Audio
		linLayout_for_Audio = new LinearLayout(linLayout_for_Buttons.getContext());
		linLayout_for_Audio.setOrientation(LinearLayout.HORIZONTAL);
		
        LayoutParams linLayoutParam_for_Audio = 
        		new LayoutParams(LayoutParams.MATCH_PARENT,
        						 LayoutParams.WRAP_CONTENT);
        linLayout_for_Buttons.addView(linLayout_for_Audio, linLayoutParam_for_Audio);
        
        LinearLayout.LayoutParams linLayout_LayoutParam_for_Audio
        			= (LinearLayout.LayoutParams) linLayout_for_Audio.getLayoutParams();
        linLayout_LayoutParam_for_Audio.weight = 1;

        // btn_Listen 
    	btn_Listen = new Button(linLayout_for_Audio.getContext());
    	btn_Listen.setText("Прослухати");
    	btn_Listen.setBackgroundResource(R.drawable.btn_rmz);
    	btn_Listen.setCompoundDrawablesWithIntrinsicBounds(drw_play, null, null, null);
    	
    	linLayout_for_Audio.addView(btn_Listen, new LinearLayout.LayoutParams( // ширина, высота, вес
    			0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
    	
/*		
    	LinearLayout.LayoutParams linLayout_LayoutParam_for_btn_Listen = 
    			(LinearLayout.LayoutParams) btn_Listen.getLayoutParams();
    	linLayout_LayoutParam_for_btn_Listen.width = LinearLayout.LayoutParams.MATCH_PARENT;
 */  	
    	//так не виводить зображення на кнопці - дивно
    	//Rect bounds = new Rect();
		//drw_play.setBounds(bounds);
		//btn_Listen.setCompoundDrawables(drw_play, null, null, null);
    	
    	btn_Listen.setOnClickListener( new OnClickListener()
    	{
			@Override
			public void onClick(View v)
			{
				if ( btn_Listen.getText().toString().equals("Стоп") )
				{
					playStop(v);
					//btn_Listen.setText("Прослухати запис");
					linLayout_for_Buttons.removeAllViews();
					is_prohibit_for_Buttons_remove = false;
					is_Buttons_created = false;
				}
				else
				{
					is_prohibit_for_Buttons_remove = true;
					/*linLayout_for_Foto.removeAllViews();
					linLayout_for_Buttons.removeView(linLayout_for_Foto);
					linLayout_for_Audio.removeView(btn_Record);
			    	LinearLayout.LayoutParams linLayout_LayoutParam_for_btn_Listen = 
			    			(LinearLayout.LayoutParams) btn_Listen.getLayoutParams();
			    	linLayout_LayoutParam_for_btn_Listen.width = LinearLayout.LayoutParams.MATCH_PARENT;
			    	linLayout_LayoutParam_for_btn_Listen.height = LinearLayout.LayoutParams.MATCH_PARENT;
			    	linLayout_LayoutParam_for_btn_Listen.leftMargin = 20;
			    	linLayout_LayoutParam_for_btn_Listen.rightMargin = 20;
			    	linLayout_LayoutParam_for_btn_Listen.topMargin = 40;
			    	linLayout_LayoutParam_for_btn_Listen.bottomMargin = 20;
			    	btn_Listen.setTextSize(50);
			    	btn_Listen.setTextColor(Color.RED);*/
					playStart(v);
					btn_Listen.setText("Стоп");
					Drawable drw_stop = getResources().getDrawable(R.drawable.btn_stop);
					btn_Listen.setCompoundDrawablesWithIntrinsicBounds(drw_stop, null, null, null);
					
				}
			}
		} );
    	

    	// btn_Record
    	btn_Record = new Button(linLayout_for_Audio.getContext());
    	btn_Record.setText("Записати");
    	btn_Record.setBackgroundResource(R.drawable.btn_rmz);
    	btn_Record.setCompoundDrawablesWithIntrinsicBounds(drw_rec, null, null, null);
    	linLayout_for_Audio.addView(btn_Record, new LinearLayout.LayoutParams( // ширина, высота, вес
    			0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
    	
    	btn_Record.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if ( btn_Record.getText().toString().equals("Стоп") )
				{
					recordStop(v);
					//btn_Record.setText("Записати аудіо");
					linLayout_for_Buttons.removeAllViews();
					is_prohibit_for_Buttons_remove = false;
					is_Buttons_created = false;
				}
					
				else
				{
					is_prohibit_for_Buttons_remove = true;
					/*linLayout_for_Foto.removeAllViews();
					linLayout_for_Buttons.removeView(linLayout_for_Foto);
					linLayout_for_Audio.removeView(btn_Listen);

			    	LinearLayout.LayoutParams linLayout_LayoutParam_for_btn_Record = 
			    			(LinearLayout.LayoutParams) btn_Record.getLayoutParams();
			    	linLayout_LayoutParam_for_btn_Record.width = LinearLayout.LayoutParams.MATCH_PARENT;
			    	linLayout_LayoutParam_for_btn_Record.height = LinearLayout.LayoutParams.MATCH_PARENT;
			    	linLayout_LayoutParam_for_btn_Record.leftMargin = 20;
			    	linLayout_LayoutParam_for_btn_Record.rightMargin = 20;
			    	linLayout_LayoutParam_for_btn_Record.topMargin = 40;
			    	linLayout_LayoutParam_for_btn_Record.bottomMargin = 20;
			    	btn_Record.setTextSize(50);
			    	btn_Record.setTextColor(Color.RED);*/
					Drawable drw_stop = getResources().getDrawable(R.drawable.btn_stop);
					btn_Record.setPadding(25, 0, 0, 0);
					btn_Record.setCompoundDrawablesWithIntrinsicBounds(drw_stop, null, null, null);
					recordStart(v);
					btn_Record.setText("Стоп");						
				}
			}
    	} );

		// linLayout_for_Foto
    	linLayout_for_Foto = new LinearLayout(linLayout_for_Buttons.getContext());
    	linLayout_for_Foto.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams linLayoutParam_for_Foto = 
        		new LayoutParams(LayoutParams.MATCH_PARENT,
        						 LayoutParams.WRAP_CONTENT);
        linLayout_for_Buttons.addView(linLayout_for_Foto, linLayoutParam_for_Foto);

        LinearLayout.LayoutParams linLayout_LayoutParam_for_Foto
        			= (LinearLayout.LayoutParams) linLayout_for_Foto.getLayoutParams();
        linLayout_LayoutParam_for_Foto.weight = 1;
        
        // btn_showFoto 
        btn_showFoto = new Button(linLayout_for_Foto.getContext());
        btn_showFoto.setText("Переглянути фото");
        btn_showFoto.setBackgroundResource(R.drawable.btn_rmz);
        btn_showFoto.setCompoundDrawablesWithIntrinsicBounds(drw_photo, null, null, null);
        linLayout_for_Foto.addView(btn_showFoto, new LinearLayout.LayoutParams( // ширина, высота, вес
    			0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        
        btn_showFoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				File ff = new File(file_Path + "/" + file_Name_Foto);
				if ( ff.exists() )
				{
					intent = new Intent(Standard_quest_EDIT_Activity.this, Your_quest_SHOW_foto.class); 
					intent.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
					// intent.putExtra("path", MO_io.filePath);
					startActivity(intent);
				}
				else
					Toast.makeText(Standard_quest_EDIT_Activity.this, "Немає фото", Toast.LENGTH_LONG).show();
			}
    	} );

        // btn_makeFoto 
        btn_makeFoto = new Button(linLayout_for_Foto.getContext());
        btn_makeFoto.setText("Зфотографувати");
        btn_makeFoto.setBackgroundResource(R.drawable.btn_rmz);
        btn_makeFoto.setCompoundDrawablesWithIntrinsicBounds(drw_camera, null, null, null);
        linLayout_for_Foto.addView(btn_makeFoto, new LinearLayout.LayoutParams( // ширина, высота, вес
    			0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        
        btn_makeFoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);   // Зображення у файлі fotoUri
				startActivityForResult(intent, PHOTO_INTENT_REQUEST_CODE); // Потрібна функція onActivityResult
			}
    	} );

	} // if() {}

	is_Buttons_created = true;
  }
// \\\\\\\\\\\\\  create_Buttons (End) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	

//\\\\\\\\\\\\\  read_String_file (Start) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private String read_String_file(String file_Name)
  {
	  	String rez = "";
		try { // открываем поток для чтения своего вопроса
			  File f1 = new File( file_Name );
			  if( f1.exists())
			  {
				  BufferedReader br = new BufferedReader(new FileReader(f1));
				  String str = "";
				  // читаем содержимое
				  while ((str = br.readLine()) != null)
				  {
					  LogMO.i(file_Name, str);
					  rez = rez +  ( (rez.length()>0) ? ("\n" + str) : str);
		    	  }
				  //quest.setText(question);
				  br.close();
			  }
		    } catch (FileNotFoundException e)
		    {
		    	e.printStackTrace();
		    } catch (IOException e)
			{
		      e.printStackTrace();
		    }
		
		return(rez);
  }
//\\\\\\\\\\\\\  read_String_file (End) \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	
  
}
