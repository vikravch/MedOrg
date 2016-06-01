package com.example.medorg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.View;
import android.widget.Toast;

import com.example.MO_io_class;
import com.example.MO_io_class.LogMO;

public class MedOrg {

	MO_io_class MO_io;
	String[] FileNames_List;
	String [] questions;
	private MediaRecorder mediaRecorder;
	private MediaPlayer mediaPlayer;

	public MedOrg() {

	}

	public void getQuestions(MO_io_class MO_io) 
	{

		FileNames_List = MO_io.f_array; // Get_FileNames_List("MO_QO", ".qst");
										// // Массив строк

		questions = new String[FileNames_List.length]; // Массив вопросов
		int pt = 0;

		for (int i = 0; i < FileNames_List.length; i++) {
			questions[i] = "";
			try {
				// открываем поток для чтения
				File f1 = new File(MO_io.filePath + "/" + FileNames_List[i]);
				BufferedReader br = new BufferedReader(new FileReader(f1));
				String str = "";
				pt = i + 1;
				questions[i] = "" + pt + ". ";
				// читаем содержимое
				while ((str = br.readLine()) != null) {
					LogMO.i("Из файла " + FileNames_List[i], str);
					questions[i] = questions[i]
							+ ((questions[i].length() > 0) ? ("" + str) : str);
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 private void releaseRecorder() {
		    if (mediaRecorder != null) {
		      mediaRecorder.release();
		      mediaRecorder = null;
		    }
		  }
	
	
	public void recordStart(View v, String file_Path, String file_Name_Audio) 
	 {
	    try 
	    {
	      /*releaseRecorder();*/
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
	
	public void recordStop(View v) {
		try
		{
			LogMO.i("Audio", "СТОП - запись");
			mediaRecorder.stop(); 
			if (mediaRecorder != null) { mediaRecorder.stop(); LogMO.i("Audio", "СТОП - запись успешно");}
		} catch (Exception e) {
			  //Log.d(LOG_TAG, " Прерывание в mediaRecorder.stop()");
			  LogMO.i("Audio", "Прерывание в mediaRecorder.stop()");
			  e.printStackTrace();
		  }
	  }
	
	private void releasePlayer() {
	    if (mediaPlayer != null) {
	      mediaPlayer.release();
	      mediaPlayer = null;
	    }
	  }
	
	 public void playStart(View v, String file_Path, String file_Name_Audio, Context c1) {
		    try {
		      LogMO.i("Audio"," Воспроизведение ");
		      releasePlayer();
		      File outFile = new File(file_Path, file_Name_Audio);
		      LogMO.d("Audio", "Прерывание при записи звука в файл1");
		      if (outFile.exists() == false) 
		      {Toast.makeText(c1, "Немає запису", Toast.LENGTH_LONG).show(); return; }
		      mediaPlayer = new MediaPlayer();
		      LogMO.d("Audio", "Прерывание при записи звука в файл2");
		      mediaPlayer.setDataSource(file_Path + "/" + file_Name_Audio);
		      LogMO.d("Audio", "Прерывание при записи звука в файл3");
		      mediaPlayer.prepare();
		      LogMO.d("Audio", "Прерывание при записи звука в файл4");
		      mediaPlayer.start();
		    } catch (Exception e) {
			   LogMO.i("Audio", "Прерывание при воспроизведении ");
			   e.printStackTrace();
		    }}

	 public void playStop(View v) {
		    if (mediaPlayer != null) {
		    	LogMO.i("Audio", "СТОП при воспроизведении");
		  	    mediaPlayer.stop();
		    }
		  }
}
