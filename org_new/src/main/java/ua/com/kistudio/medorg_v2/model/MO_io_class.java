/**
 * 
 */
package ua.com.kistudio.medorg_v2.model;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @author Василий
 *
 */
public class MO_io_class {

	/**
	 * 
	 */
public final static String LOG_TAG	= "MO_io_class";
	
	String DATA_STOR	= "data/data/com.example.medorg"; // Если нет SD, то хранить во внутренней памяти
	String DATA_DIR		= "MedOrg";  // Каталог для хранения данных приложения
	
//	String DATA_FILE	= "MO_"; // file"; // Имя файла
//	String DATA_EXT		= ""; //".3gpp";  // Тип аудиофайла
//	String NAME_FILT	= DATA_FILE.substring(0, 1); // "MO";  // Фильтр
	
//	public String fileName;  // Полное имя файла
	public String filePath;  // Полный путь (безимени)
	
	public String [] f_array = new String[0];;

//  !!!!!!!!!!!!!!!!!!! Конструктор MO_io_class (НАЧАЛО) !!!!!!!!!!!!!!!!!!!!!!!!!!!!   		
	public MO_io_class(String filtr /* Фильтр-начало имени */, String filtr2 /* Фильтр-расширение имени (с точкой)*/)
	{	super();
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // Проверка доступности SD: Нет SD
		{
			LogMO.d(LOG_TAG, "SD-карта НЕдоступна!!!!: " + Environment.getExternalStorageState());
			LogMO.d(LOG_TAG, " Пишем во внутр. память: data/data/<имя пакета>/" + DATA_DIR);
//			fileName = DATA_STOR + "/" + DATA_DIR + "/" + DATA_FILE + DATA_EXT;
			filePath = DATA_STOR + "/" + DATA_DIR;
			File d = new File(DATA_STOR + "/" + DATA_DIR);
			if (d.exists() && d.isDirectory())  // Проверка существования каталога
			{
				LogMO.i(LOG_TAG, "Директория " + DATA_STOR + "/" + DATA_DIR + " существует");
		    	f_array = d.list();	//  Список файлов - в массив
		    	LogMO.i(LOG_TAG, "В директории " + f_array.length + " файла(ов)");
		    	
		    	f_array = Get_FileNames_List(filtr, filtr2);
			}
			else
			{
				d.mkdir();
				LogMO.i(LOG_TAG, "Директория " + DATA_STOR + "/" + DATA_DIR + " создана!!");
			}
		}
		else	// Проверка доступности SD: карта SD доступна
		{
			// получаем путь к SD
			File sdPath = Environment.getExternalStorageDirectory();
			// добавляем свой каталог к пути
			sdPath = new File(sdPath.getAbsolutePath() + "/" + DATA_DIR);
			filePath = sdPath.getAbsolutePath();
//		    fileName = sdPath.getAbsolutePath() + "/" + DATA_FILE + DATA_EXT;
		    // File sdPath = new File(Environment.getExternalStorageDirectory() + "/" + DATA_DIR);
		    if (sdPath.exists() && sdPath.isDirectory())  // Проверка существования подкаталога
		    {
				LogMO.i(LOG_TAG, "Директория " + sdPath.getAbsolutePath() + "/" + DATA_DIR + " существует");
		    	f_array = sdPath.list();	//  Список файлов - в массив
		    	LogMO.i(LOG_TAG, "В директории " + f_array.length + " файла(ов)");
		    	
		    	if( !filtr.equals("0") ) 
		    		f_array = Get_FileNames_List(filtr /* MO */, filtr2 /* ".qst" */);		    	
		    }
		    else
		    {
		    	sdPath.mkdirs();
		    	LogMO.i(LOG_TAG, "Директория " + Environment.getExternalStorageDirectory() + "/" + DATA_DIR + " создана!!");
		    }
		}
	}
//  ............. Конструктор MO_io_class (КОНЕЦ) .........................
	
//  !!!!!!!!!!!!!!!!!!! Метод Get_FileNames_List (НАЧАЛО) !!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public String [] Get_FileNames_List(String filtr /* Фильтр */, String filtr2)
	{
		String [] f_array2;  // Для результата
		int j = 0;
		int i;
    	for (i = 0; i < f_array.length; i++)
    	{
    		if(f_array[i].indexOf(filtr)==0 && f_array[i].indexOf(filtr2) > 0)
    		{
    			j++;
    		}
    		else
    		{
    			//f_array1[i] = new String("Nо___");
    		}
    		LogMO.i(String.valueOf(i), f_array[i]);
    	}
    	LogMO.i(LOG_TAG, "После фильтра " + j + " файлов:");
    	f_array2 = new String[j];
    	j=0;
    	for ( i = 0; i < f_array.length; i++)
    	{
    		if( f_array[i].indexOf(filtr) == 0  && f_array[i].indexOf(filtr2) > 0 )
    		{
    			f_array2[j] = f_array[i]; 
    			LogMO.i(String.valueOf(j), f_array2[j]);
    			j++;
    		}
    	}
    	BubbleSort(f_array2);
		return f_array2;
	}
//  ............. Get_FileNames_List (КОНЕЦ) .........................

	
//  !!!!!!!!!!!!!!!!!!! (Метод) BubbleSort (НАЧАЛО) !!!!!!!!!!!!!!!!!!!!!!!!!!!!

  void BubbleSort( String array[] )
{    for( int i = 1; i < array.length; i++ )
        for( int j = array.length-1; j>=i; j-- )
            if( array[ j ].compareTo(array[j-1]) < 0 )
            { 	String t = array[j];
            	array[j] = array[j-1];
            	array[j-1] = t;
}            }

//  ............. (Метод) BubbleSort (КОНЕЦ) .........................


  
  
/**/
	static public class LogMO
	{
		static final boolean  if_print = true;
		
		public static void i(String Tag, String text)
		{
			if(if_print) Log.i(Tag,text);
		}
		public static void d(String Tag, String text)
		{
			if(if_print) Log.d(Tag,text);
		}
	}


}
