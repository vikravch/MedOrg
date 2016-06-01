package ua.com.kistudio.medorg_v2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;
import ua.com.kistudio.medorg_v2.model.MO_io_class;

/**
 * Created by Вiталя on 10.03.2016.
 */
public class StandartQuestFromGroupActivity extends Activity {

    String [] questions;
    ListView lvMain;
    int N_grp;

/////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_quest_from_group);

        Intent intent = getIntent();
        int 	size_of_grp = intent.getIntExtra(Params.EXTRA_SIZE_OF_GROUP_STANDART_QUEST,0);
        int 	id = intent.getIntExtra(Params.EXTRA_ID_STANDART_QUEST,0);
        Log.d(Params.LOG_TAG," id - "+id);
        String 	groups_name = intent.getStringExtra(Params.EXTRA_GROUPS_NAME_STANDART_QUEST);
        N_grp = intent.getIntExtra(Params.EXTRA_GROUP_NUMBER_STANDART_QUEST,0);		// Номер группы (используется в именах файлов ответов на вопросы)

        TextView e_txt = (TextView) findViewById(R.id.g_standard_quest_to_doct);
        e_txt.setText(groups_name);

        showMenu(id, size_of_grp);

    }

/////////////////////////////////////////////////////////////////////////////////////

    protected void showMenu(int file_id, int size_of_grp)
    {
        questions = new String[size_of_grp]; // Массив вопросов
        int num;
        for ( num=0; num<questions.length; num++)
            questions[num] = num + ".";

        InputStream iFile = getResources().openRawResource(file_id);
        byte b[] = new byte[3500];
        int s;
        try {
            s = iFile.read(b);
            String str = new String(b,0,s);
            // str = s + " " + str;
            s = str.indexOf('\n');
            num=0;
            while ( s > 0 )
            {
                // str = str + ' ' + s;
                MO_io_class.LogMO.i("Из файла вопросов: ", str.substring(0, s) + ' ' + s + ' ' + str.length() + ' ' + "num=" + num);
                if ( str.length() > s )
                {
                    questions[num++] = str.substring(0,s);
                    str = str.substring(s+1);
                }
                s = str.indexOf('\n');
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // находим список
        MO_io_class.LogMO.i("showMenu() ", "находим список");
        lvMain = (ListView) findViewById(R.id.sqg_listView1);

        // устанавливаем режим выбора пунктов списка
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // создаем адаптер
        MO_io_class.LogMO.i("showMenu() ", "создаем адаптер");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_multiple_choice, questions);

        // присваиваем адаптер списку
        MO_io_class.LogMO.i("showMenu() ", "присваиваем адаптер списку");
        lvMain.setAdapter(adapter);

        //
        //
        for ( num=0; num<questions.length; num++)
            MO_io_class.LogMO.i("showMenu() ", "questions[" + num + "] = " + questions[num]);


        // створюємо прослуховувач натискань на пункти меню
        MO_io_class.LogMO.i("showMenu() ", "створюємо прослуховувач натискань на пункти меню");
        lvMain.setOnItemClickListener
                (
                        new AdapterView.OnItemClickListener()
                        {
                            public void onItemClick( AdapterView<?> parent, View itemClicked, int position, long id )
                            {
                                TextView textView = (TextView) itemClicked;
                                String strText = textView.getText().toString();

                                MO_io_class.LogMO.i("Выбрано: id, position", id + ", " + position + " -> " + strText);

                            }
                        }
                ); // кінець прослуховувача натискань на пункти меню


        MO_io_class.LogMO.i("showMenu() ", "  Конец showMenu()");

    }
/////////////////////////////////////////////////////////////////////////////////////

    public void onClick(View view)  // Обработчик
    {
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();  //  Все вопросы, выбор которых изменялся
        int checked_number = 0;
        try{
            switch (view.getId())	// Кнопка одна: switch можно убрать
            {
                case R.id.btnGoToEdit:
                    MO_io_class.LogMO.i("Кнопка нажата", "Выбранные элементы: ");
                    for (int i = 0; i < sbArray.size(); i++)
                    {
                        int key = sbArray.keyAt(i);
                        if (sbArray.get(key))		// Оставшиеся выбранными вопросы
                        {
                            MO_io_class.LogMO.i("Вопрос: i=" + i, "key=" + key + ' ' + questions[key]);
                            checked_number++;
                        }
                    }
                    break;
                case 0:
                    break;
            }
        }	catch ( Exception e)
        {
            e.printStackTrace();
        }
        if ( checked_number == 0 )
        {
            Toast.makeText(getApplicationContext(), "Оберіть запитання!", Toast.LENGTH_LONG).show();
            return;
        }

        String  Checked_quests[];
        Checked_quests = new String[checked_number]; // Массив выбранных вопросов
        int Checked_quest_num[];	 // Массив номеров выбранных вопросов
        Checked_quest_num = new int[checked_number];
        int n = 0;
        for (int i = 0; i < sbArray.size(); i++)
        {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))		// Оставшиеся выбранными вопросы
            {
                if( n < checked_number )
                {
                    Checked_quests[n] = questions[key];
                    Checked_quest_num[n] = key;
                }
                n++;
            }
        }

        Intent intent = new Intent(this, StandardQuestEditActivity.class);
        intent.putExtra("What", "Standard");	  	// Тип вопросов
        intent.putExtra("Checked_quests", Checked_quests);		// Выбранные вопросы
        intent.putExtra("N_grp", N_grp);  						// Номер группы
        intent.putExtra("Checked_quest_num", Checked_quest_num);	 // Массив номеров выбранных вопросов
        startActivity(intent);

    }

}
