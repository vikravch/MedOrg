package ua.iepor.itdep.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.medorg.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ua.iepor.itdep.util.BoxAdapter;
import ua.iepor.itdep.util.Product;

public class SubVidchuttjaActivity extends Activity {

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

    String[] questC30 = this.getResources().getStringArray(R.array.question);
    String[] questBR23 = this.getResources().getStringArray(R.array.questions_second);

    String[] title1={"Дайте відповідь на всі наступні запитання:\n","","","","",
    				"Протягом минулого тижня: \n","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","","",
    				"","","","У наступних запитаннях просимо обвести кружечком той номер від 1 до 7, який найбільше відповідає Вашій ситуації. ",""
    };
    String[] title2 = {
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
