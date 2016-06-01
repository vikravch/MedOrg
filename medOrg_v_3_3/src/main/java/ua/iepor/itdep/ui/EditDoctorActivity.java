package ua.iepor.itdep.ui;

import android.app.Activity;
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

import com.example.MO_io_class.LogMO;
import com.example.medorg.MedOrg;
import com.example.medorg.R;
import com.example.medorg.Your_quest_SHOW_foto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ua.iepor.itdep.util.AsyncReadFile;
import ua.iepor.itdep.util.Params;

public class EditDoctorActivity extends Activity {
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

    private String mId;
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

        //insertDoctor("1111","1111","1111","1111");

        Intent intent = getIntent();
		
		file_Name = intent.getStringExtra("file_name");
	    question = intent.getStringExtra("quest");
	    file_Path = intent.getStringExtra("path");
        mId = intent.getStringExtra("id");
	    file_Name_Answ = file_Name.replaceAll(".dct", ".dct");
	    file_Name_Audio = file_Name.replaceAll(".dct", ".3gpp");
	    file_Name_Foto = file_Name.replaceAll(".dct", ".jpg");
	    file_Name_spec = file_Name.replaceAll(".dct", ".spc");
	    
	    //writeFile(file_Path, "1", "Hello");
		AsyncReadFile asyncRead = new AsyncReadFile();
		asyncRead.execute(file_Path, file_Name);
		String readStr = null;
		try {
			readStr = asyncRead.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        if (readStr!=null){
            initViews(readStr.split("#"),question);
        }

        File fotoFile = new File(file_Path + "/" + file_Name_Foto);
		fotoUri = Uri.fromFile(fotoFile);

	    
	    newAnsw = "";

		Log.d(Params.LOG_TAG, "Created");
	}

    private void insertDoctor(String pib, String spec, String about, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.DOCTOR_PIB,pib);
        contentValues.put(Params.DOCTOR_SPEC,spec);
        contentValues.put(Params.DOCTOR_ABOUT,about);
        contentValues.put(Params.DOCTOR_PHONE, phone);
        this.getContentResolver().insert(Params.CONTENT_URI_DOCTOR, contentValues);
    }

    private void initViews(String[] s, String question) {
		quest = (EditText) findViewById(R.id.editQuest);
		spec = (EditText) findViewById(R.id.EditSpec);
		about = (EditText) findViewById(R.id.EditAbout);
		tel = (EditText) findViewById(R.id.EditTel);
		answ = (EditText) findViewById(R.id.editAnsw);

        Cursor c =getContentResolver().query(Params.CONTENT_URI_DOCTOR, null, "_id=="+mId, null, null);


        c.moveToFirst();

		if (c.getString(c.getColumnIndex(Params.DOCTOR_PIB))!=null)
        	quest.setText(c.getString(c.getColumnIndex(Params.DOCTOR_PIB)));

		if (c.getString(c.getColumnIndex(Params.DOCTOR_SPEC))!=null)
        	spec.setText(c.getString(c.getColumnIndex(Params.DOCTOR_SPEC)));

		if (c.getString(c.getColumnIndex(Params.DOCTOR_ABOUT))!=null)
        	about.setText(c.getString(c.getColumnIndex(Params.DOCTOR_ABOUT)));

		if (c.getString(c.getColumnIndex(Params.DOCTOR_PHONE))!=null)
	        tel.setText(c.getString(c.getColumnIndex(Params.DOCTOR_PHONE)));
/*

        do{
            Log.d(Params.LOG_TAG, String.format("id - %s , pib - %s , spec - %s , about - %s , phone - %s",
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex(Params.DOCTOR_PIB)),
                    c.getString(c.getColumnIndex(Params.DOCTOR_SPEC)),
                    c.getString(c.getColumnIndex(Params.DOCTOR_ABOUT)),
                    c.getString(c.getColumnIndex(Params.DOCTOR_PHONE))
            ));
        } while (c.moveToNext());
*/

/*
        quest.setText(c.getString(c.getColumnIndex(Params.DOCTOR_PIB)));
        spec.setText(c.getString(c.getColumnIndex(Params.DOCTOR_SPEC)));
        about.setText(c.getString(c.getColumnIndex(Params.DOCTOR_ABOUT)));
        tel.setText(c.getString(c.getColumnIndex(Params.DOCTOR_PHONE)));
*/

        /*if (s[0]!=""){quest.setText(s[0]);}
        if (s.length>1) {spec.setText(s[1]);}
        if (s.length>1) {about.setText(s[2]);}
        if (s.length>2) {tel.setText(s[3]);}
        quest.setText(question);*/
    }

	/////////////////////////////////////////////////////////////////////////////////////
	public void onClick(View view)  // Это - обработчик для кнопок с методом onClick
	{
		MedOrg in1 = new MedOrg();
		AlertDialog.Builder alert;
			switch (view.getId())
			{
				case R.id.clear_answer:
					quest.setText("");
					newAnsw = "";

					break;		
					
				case R.id.saveAnswDoctor:
					Log.d(Params.LOG_TAG, "Click on button");

		      		question = quest.getText().toString();
		      		special = spec.getText().toString();
		      		aboutl = about.getText().toString();
		      		telephone = tel.getText().toString();
		      		/*
		      		String out = question+ "#" + special + "#" + aboutl +"#" + telephone;
		      		writeFile(file_Path, file_Name, out);
		      		*/

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Params.DOCTOR_PIB,question);
                    contentValues.put(Params.DOCTOR_SPEC,special);
                    contentValues.put(Params.DOCTOR_ABOUT,aboutl);
                    contentValues.put(Params.DOCTOR_PHONE,telephone);
                    this.getContentResolver().update(Params.CONTENT_URI_DOCTOR, contentValues, "_id=="+mId, null);

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
						in1.playStart(view, file_Path, file_Name_Audio, EditDoctorActivity.this);
						btnAudio.setText("СТОП");
						btnAudio.setBackgroundResource(R.drawable.btn_stop);
					}
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
						Intent intent1 = new Intent(EditDoctorActivity.this, Your_quest_SHOW_foto.class);
						intent1.putExtra("fotoFile", file_Path + "/" + file_Name_Foto);
						// intent.putExtra("path", MO_io.filePath);
						startActivity(intent1);
					}
					else
						Toast.makeText(EditDoctorActivity.this, "Немає фото", Toast.LENGTH_LONG).show();
					 
					break;
				
				case R.id.Phone:
					telephone = tel.getText().toString();
					Uri uri = Uri.parse("tel:"+telephone);
					Intent intent1 = new Intent(Intent.ACTION_DIAL);
					intent1.setData(uri);
					startActivity(intent1);
					break;
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
		      {Toast.makeText(EditDoctorActivity.this, "Немає запису", Toast.LENGTH_LONG).show(); return; }
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
