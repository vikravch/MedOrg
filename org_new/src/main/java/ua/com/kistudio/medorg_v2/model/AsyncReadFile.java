package ua.com.kistudio.medorg_v2.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 11.03.2016.
 */
public class AsyncReadFile extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {

        File f1 = new File( params[0] + "/" + params[1]);
        String str = "";
        String res = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(f1));

            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(Params.LOG_TAG, str);
                res+=str;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
