package ua.com.kistudio.medorg_v2.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ua.com.kistudio.medorg_v2.util.Params;

/**
 * Created by Вiталя on 12.03.2016.
 */
public class MedOrgDBHelper extends SQLiteOpenHelper {
    /*
    DB Structure:
        Table doctor:
            id - person id
            pib - first, last names
            spec - type of job
            about - about the doctor
            phone - phone number
            foto - path to photo of doctor
            audio - path to audio
        Table questions:
            id - question id
            value - text of question
            answer - aswer on question
        Table pruznachennja:
            id - pruznachennja id
            pruzn - text of pr
        Table opros:
            id - opros id
            type - type of opros
            date - date of opros
            who - user first and second name
            result - result of opros (csv - ";")
     */

    private static final String CREATE_TABLE_DOCTOR = "CREATE TABLE doctor (_id integer primary key autoincrement," +
            String.format(" %s text, %s text, %s text, %s text, %s text, %s text);"
                    , Params.DOCTOR_PIB, Params.DOCTOR_SPEC, Params.DOCTOR_ABOUT,
                    Params.DOCTOR_PHONE, Params.DOCTOR_PHOTO, Params.DOCTOR_AUDIO);

    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE questions (_id integer primary key autoincrement,"+
            String.format("%s text, %s text);",Params.QUESTION_TEXT,Params.QUESTION_ANSWER);

    private static final String CREATE_TABLE_PRUZNACHENNJA = "CREATE TABLE pruznachennja (_id integer primary key autoincrement, "+
            String.format("%s text);",Params.PRUZN_TEXT);
    public static final String CREATE_TABLE_OPROS = "CREATE TABLE opros (_id integer primary key autoincrement, "+
            String.format("%s text, %s text, %s text, %s text);", Params.OPROS_TYPE, Params.OPROS_DATE, Params.OPROS_WHO, Params.OPROS_RESULT);

    public MedOrgDBHelper(Context context, int version) {
        super(context, "MedOrgEntity", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DOCTOR);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_PRUZNACHENNJA);
        db.execSQL(CREATE_TABLE_OPROS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

