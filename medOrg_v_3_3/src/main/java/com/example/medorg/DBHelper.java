package com.example.medorg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	final String LOG_TAG = "myLogs";
	
	//стандартний шлях до БД
	private static String DB_PATH = "/data/data/com.example.medorg/databases/";
		
	private static String DB_NAME = "myDBName";
		
	private SQLiteDatabase myDataBase;
		
	private final Context myContext;
	
	public DBHelper(Context context) {
		// конструктор суперкласа
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if(dbExist) {
			//нічого не робити, якщо база вжєе існує. Виводимо в лог.
			Log.d(LOG_TAG, "--- DB already exists ---");
		} else {
			//викликаючи цей метод, створюємо пусту базу, пізніше вона буде перезаписана
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}
	
	/**
	* Перевіряє чи існує вже ця БД, щоб не копіювати кожен раз при запуску програми
	* @return true якщо існує, false якщо не існує
	*/
	private boolean checkDataBase(){
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}
	
	/**
	* Копіює БД із папки assets замість сствореної локальної БД
	* Виконується шляхом копіювання потоку байтів.
	* */
	private void copyDataBase() throws IOException{
		
		//Відкриваємо локальну БД, як вхідний потік
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		Log.d(LOG_TAG, "--- Open local DB --- " + myContext.getAssets().open(DB_NAME));
		
		//Шлях до створеної БД
		String outFileName = DB_PATH + DB_NAME;
		Log.d(LOG_TAG, "--- DB path is:  --- " + outFileName);
				
		//Відкриваємо пусту БД, як вихідний потік
		OutputStream myOutput = new FileOutputStream(outFileName);
		
		//Переміщуємо байти із вхідного файла в вихідний
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
			Log.d(LOG_TAG, "--- Data is transfering ---");
		}
	Log.d(LOG_TAG, "--- DB created ---");
	//закриваємо потоки
	myOutput.flush();
	myOutput.close();
	myInput.close();
	}
	
	public SQLiteDatabase openDataBase() throws SQLException{
		//відкриваємо БД
		String myPath = DB_PATH + DB_NAME;
		Log.d(LOG_TAG, "--- DB opened ---");
		return myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public synchronized void close() {
		
		Log.d(LOG_TAG, "--- onClose database ---");
		
		if(myDataBase != null)
			myDataBase.close();
		super.close();
	}
}