package ditsystems.com.zinger.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 26.04.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_TRAININGS = "create table "+Const.TABLE_NAME_TRAININGS +" ( "+
            Const.FIELD_ID_TRAININGS+" integer primary key autoincrement, "+
            Const.FIELD_NAME_TRAININGS+" text, "+
            Const.FIELD_ABOUT_TRAININGS+" text, "+
            Const.FIELD_DESCRIPTION_TRAININGS+" text );";

    private static final String CREATE_TABLE_DIETS = "create table "+Const.TABLE_NAME_DIETS +" ( "+
            Const.FIELD_ID_DIETS+" integer primary key autoincrement, "+
            Const.FIELD_NAME_DIETS+" text, "+
            Const.FIELD_ABOUT_DIETS+" text, "+
            Const.FIELD_DESCRIPTION_DIETS+" text );";

    private static final String CREATE_TABLE_WATER = "create table "+Const.TABLE_NAME_WATER+" ("+
            Const.FIELD_ID_WATER+" integer primary key autoincrement, "+
            Const.FIELD_VOLUME_WATER+" integer, "+
            Const.FIELD_DAY_WATER+" text, " +
            Const.FIELD_TIME_WATER+" text );";

    public DBHelper(Context context, int version) {
        super(context, "zinger", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(Const.TAG_LOG_PROJECT,"create "+db.getVersion());
        db.execSQL(CREATE_TABLE_DIETS);
        createDefaultDiets(db);

        db.execSQL(CREATE_TABLE_TRAININGS);
        createDefaultTrainings(db);
        db.execSQL(CREATE_TABLE_WATER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(Const.TAG_LOG_PROJECT,"upgrate "+db.getVersion());

        db.execSQL("DROP TABLE IF EXISTS " + Const.TABLE_NAME_WATER);
        db.execSQL(CREATE_TABLE_WATER);
        createDefaultDiets(db);
        createDefaultTrainings(db);
    }

    private void createDefaultDiets(SQLiteDatabase db) {

        ContentValues cv = new ContentValues();
        cv.put(Const.FIELD_NAME_DIETS,"Name - 1");
        cv.put(Const.FIELD_ABOUT_DIETS," About - 1 ");
        cv.put(Const.FIELD_DESCRIPTION_DIETS,"Description - 1");
        db.insert(Const.TABLE_NAME_DIETS,null,cv);

        Log.d(Const.TAG_LOG_PROJECT,"Inserted");
    }

    private void createDefaultTrainings(SQLiteDatabase db) {

        ContentValues cv = new ContentValues();
        cv.put(Const.FIELD_NAME_TRAININGS,"Name train - 1");
        cv.put(Const.FIELD_ABOUT_TRAININGS," About - 1 ");
        cv.put(Const.FIELD_DESCRIPTION_TRAININGS,"Description - 1");
        db.insert(Const.TABLE_NAME_TRAININGS,null,cv);

        Log.d(Const.TAG_LOG_PROJECT,"Inserted");
    }
}
