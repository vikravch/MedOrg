package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.util.Product;
import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.model.BoxAdapter;

public class SubVidchuttjaActivity extends AppCompatActivity {

  Intent intent;
  int type;
  ArrayList<Product> products = new ArrayList<Product>();
  BoxAdapter boxAdapter;
  String DATA_STOR	= "data/data/com.example.medorg"; // Если нет SD, то хранить во внутренней памяти
  String DATA_DIR		= "MedOrg"; 
  public String filePath;
  

  /** Called when the activity is first created. */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.vidchuttja);

    intent = getIntent();
    type = intent.getIntExtra("type", 0);

    String[] questC30 = this.getResources().getStringArray(R.array.qs);
    String[] questBR23 = this.getResources().getStringArray(R.array.qs_s);

    String[] title1={"Дайте відповідь на всі наступні запитання:\n","","","","",
    				"Протягом минулого тижня: \n","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","У наступних запитаннях просимо вибрати ту позицію, яка найбільше відповідає Вашій ситуації. ",""
    };
    String[] title2 = {
            "Дайте відповідь на всі наступні запитання:\n \n Протягом наступного тижня: \n","","","","",
            "","","","","",
            "","","","Протягом останніх чотирьох тижнів:\n","",
            "","Протягом останнього тижня:\n","","","",
            "","","","","",
            "","","","","",
            "","","","","",
            "","","","","",
            "","","","",""

    };

    switch (type){
      case 0:
        fillData(questC30, title1);
        boxAdapter = new BoxAdapter(this, questC30, title1, products);
        break;
      case 1:
        fillData(questBR23, title2);
        boxAdapter = new BoxAdapter(this, questBR23, title1, products);
        break;

    }
    // настраиваем список
    ListView lvMain = (ListView) findViewById(R.id.lvMain);
    lvMain.setAdapter(boxAdapter);
    
   
    	  
  }

  // генерируем данные для адаптера
  void fillData(String[] str1, String[] str2) {
    for (int i = 0; i < str1.length; i++) {
      products.add(new Product("Product " + i, i * 1000,
          R.drawable.ic_launcher, false, str1[i], str2[i]));
    }
  }

  // выводим информацию о корзине
  public void showResult(View v) {
      String jsonRes = "";
    String result = "Результати опитування:";
    int i = 1;
    for (Product p : boxAdapter.getBox()) {
    	
      /*if (p.box)
        result += "\n" + p.name;*/
        jsonRes+=p.number+";";
      result+="\n номер запитання: " + i +" відповідь= "+ p.number;
      i++;
    }
    //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    /*filePath = DATA_STOR + "/" + DATA_DIR;
    filePath = DATA_DIR;*/
      Log.d(Params.LOG_TAG,"res - "+jsonRes);

      ContentValues cv = new ContentValues();
      cv.put(Params.OPROS_TYPE,"v");



      Calendar calendar = new GregorianCalendar();
      calendar = Calendar.getInstance();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
      String day = simpleDateFormat.format(calendar.getTime());

      cv.put(Params.OPROS_DATE,day);



      SharedPreferences sp = getSharedPreferences(Params.PREFERENCE_NAME, Context.MODE_PRIVATE);
      String name=sp.getString(ProfileActivity.PIB, "");
      cv.put(Params.OPROS_WHO,name);


      cv.put(Params.OPROS_RESULT, jsonRes);
      Log.d(Params.LOG_TAG,cv.toString());
      getContentResolver().insert(Params.OPROS_URI, cv);

    //writeFile(filePath,"2.txt",result);
    Toast.makeText(this, "Збережено", Toast.LENGTH_LONG).show();
  }

    public void showList(View v) {
        startActivity(new Intent(this,ListSubVidchuttiaActivity.class));

    }
  
  public void writeFile(String Path, String File_name, String Content) {
		//File f1 = new File( Path + "/" + File_name + ".txt" );
		File f1 = new File("12.txt");
	    try {
	      // отрываем поток для записи
	      BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
	      // пишем данные
	      bw.write(Content);
	      // закрываем поток
	      bw.close();
	      Log.d("LogStr", "Файл записан");
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}
