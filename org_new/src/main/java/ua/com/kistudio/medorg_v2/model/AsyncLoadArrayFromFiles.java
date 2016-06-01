package ua.com.kistudio.medorg_v2.model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ua.com.kistudio.medorg_v2.util.Params;
import ua.com.kistudio.medorg_v2.R;

/**
 * Created by Вiталя on 11.03.2016.

 Input params:
 0 - prefix (ex. "MO_QO")
 1 - sufix (ex. ".qst")
 2 - ListView where put the list

 */
public class AsyncLoadArrayFromFiles extends AsyncTask<Object,Void,String[]> implements AdapterView.OnItemClickListener {

    MO_io_class MO_io;
    String [] FileNames_List;
    String [] questions;
    private Context mContext;
    private String mPrefix;
    private String mSuffix;
    private ListView mListResult;
    private Intent mResIntent;

    public AsyncLoadArrayFromFiles(Context mContext, Intent resIntent) {
        this.mContext = mContext;
        this.mResIntent = resIntent;
    }

    @Override
    protected String[] doInBackground(Object... params) {

        mPrefix = (String) params[0];
        mSuffix = (String) params[1];
        mListResult = (ListView) params[2];

        MO_io =  new MO_io_class(mPrefix,mSuffix);

        FileNames_List =  MO_io.f_array;   //Get_FileNames_List("MO_QO", ".qst"); // Массив строк
        questions = new String[FileNames_List.length]; // Массив вопросов

        for( int i=0; i < FileNames_List.length; i++ )
        {
            questions[i] = "";
            try {
                // открываем поток для чтения
                File f1 = new File( MO_io.filePath + "/" + FileNames_List[i] );
                BufferedReader br = new BufferedReader(new FileReader(f1));
                String str = "";
                // читаем содержимое
                while ((str = br.readLine()) != null) {
                    MO_io_class.LogMO.i("Из файла " + FileNames_List[i], str);
                    questions[i] = questions[i] +  ( (questions[i].length()>0) ? ("\n" + str) : str);
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(questions.length == 0)
        {
            questions = new String[1]; // Массив вопросов
            questions[0] = "Місце для першого запитання";
            FileNames_List = new String[1]; // Массив имен файлов
            FileNames_List[0] = mPrefix + String.valueOf(System.currentTimeMillis()) + mSuffix;
        }
        return questions;
    }


    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.menu3_item, questions);
        mListResult.setAdapter(adapter);
        mListResult.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        String strText = textView.getText().toString();
        mResIntent.putExtra(Params.EXTRA_POSITION_NUMBER_FROM_MENU, position);

        mResIntent.putExtra("file_name", FileNames_List[position]);
        mResIntent.putExtra("quest", questions[position]);
        mResIntent.putExtra("path", MO_io.filePath);

        mContext.startActivity(mResIntent);
    }
}
