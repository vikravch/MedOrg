package com.example.medorg;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Sub_Vidchuttja extends Activity {

  ArrayList<Product> products = new ArrayList<Product>();
  BoxAdapter boxAdapter;
  String DATA_STOR	= "data/data/com.example.medorg"; // Если нет SD, то хранить во внутренней памяти
  String DATA_DIR		= "MedOrg"; 
  public String filePath;
  

  /** Called when the activity is first created. */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.vidchuttja);
    String[] quest={"one","two","three"};
    quest = this.getResources().getStringArray(R.array.question);
    String[] title={"Дайте відповідь на всі наступні запитання:\n","","","","",
    				"Протягом минулого тижня: \n","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","У наступних запитаннях просимо обвести кружечком той номер від 1 до 7, який найбільше відповідає Вашій ситуації. ","",
    				"Протягом наступного тижня: \n","","","","",
    				"","","","","",
    				"","","","Протягом останніх чотирьох тижнів:\n","",
    				"","Протягом останнього тижня:\n","","","",
    				"","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","",""
    					
    };

    // создаем адаптер
    fillData(quest, title);
    boxAdapter = new BoxAdapter(this, quest, title, products);

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
    String result = "Результати опитування:";
    int i = 1;
    for (Product p : boxAdapter.getBox()) {
    	
      /*if (p.box)
        result += "\n" + p.name;*/
      result+="\n номер запитання: " + i +" відповідь= "+ p.number;
      i++;
    }
    //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    filePath = DATA_STOR + "/" + DATA_DIR;
    filePath = DATA_DIR;
    writeFile(filePath,"2.txt",result);
    Toast.makeText(this, "Збережено", Toast.LENGTH_LONG).show();
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
