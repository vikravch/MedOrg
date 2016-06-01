package ua.iepor.itdep.util;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import com.example.MO_io_class;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Вiталя on 11.03.2016.
 */

public class AsyncCreateNewFile extends AsyncTask<String,Void,String> {

    private EditText mEt;
    private String mText;

    public AsyncCreateNewFile(EditText editText) {
        this.mEt = editText;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mText = mEt.getText().toString();
    }

    @Override
    protected String doInBackground(String... params) {
        MO_io_class io = new MO_io_class("]_", "]_");
        //MO_io_class.LogMO.i("Путь к файлам", io.filePath);
        String f_Name = params[0] + String.valueOf(System.currentTimeMillis()) + params[1];
        //MO_io_class.LogMO.i("Новое имя", f_Name);

        File qstFile = new File(io.filePath, f_Name);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(qstFile));
            // пишем данные
            bw.write(mText);
            // закрываем поток
            bw.close();
     //       MO_io_class.LogMO.i("Файл записан: ", qstFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
