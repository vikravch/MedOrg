package ua.com.kistudio.medorg_v2.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ua.com.kistudio.medorg_v2.util.Params;


public class MedOrgContentProvider extends ContentProvider {

    private static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sMatcher.addURI(Params.AUTORITY,Params.TYPE_DOCTOR,Params.CODE_DOCTOR);
        sMatcher.addURI(Params.AUTORITY,Params.TYPE_QUESTIONS,Params.CODE_QUESTIONS);
        sMatcher.addURI(Params.AUTORITY,Params.TYPE_PRUZN, Params.CODE_PRUZN);
        sMatcher.addURI(Params.AUTORITY,Params.TYPE_OPROS, Params.CODE_OPROS);
    }

    private MedOrgDBHelper medOrgDBHelper;
    SQLiteDatabase db;

    public MedOrgContentProvider() {

    }


    @Override
    public boolean onCreate() {
        medOrgDBHelper = new MedOrgDBHelper(getContext(),Params.DB_VERSION);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        db = medOrgDBHelper.getWritableDatabase();
        long rowID = 0;
        switch (sMatcher.match(uri)) {
            case Params.CODE_DOCTOR:
                rowID = db.insert(Params.DOCTOR_TABLE_NAME, null, values);
                break;
            case Params.CODE_QUESTIONS:
                rowID = db.insert(Params.QUESTION_TABLE_NAME, null, values);
                break;
            case Params.CODE_PRUZN:
                rowID = db.insert(Params.PRUZN_TABLE_NAME, null, values);
                break;
            case Params.CODE_OPROS:
                rowID = db.insert(Params.OPROS_TABLE_NAME, null, values);
                break;
        }
        Uri resURI = ContentUris.withAppendedId(uri, rowID);
        Log.d(Params.LOG_TAG,"Inserted - "+rowID);
        return resURI;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = medOrgDBHelper.getWritableDatabase();
        Cursor c = null;
        switch (sMatcher.match(uri)) {
            case Params.CODE_DOCTOR:
                c = db.query(Params.DOCTOR_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case Params.CODE_QUESTIONS:
                c = db.query(Params.QUESTION_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Params.CODE_PRUZN:
                c = db.query(Params.PRUZN_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Params.CODE_OPROS:
                c = db.query(Params.OPROS_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
        }
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        db = medOrgDBHelper.getWritableDatabase();
        switch (sMatcher.match(uri)){
            case Params.CODE_DOCTOR:
                return db.update(Params.DOCTOR_TABLE_NAME,values,selection,selectionArgs);
            case Params.CODE_QUESTIONS:
                return db.update(Params.QUESTION_TABLE_NAME,values,selection,selectionArgs);
            case Params.CODE_PRUZN:
                return db.update(Params.PRUZN_TABLE_NAME,values,selection,selectionArgs);
            default:
                return 0;
        }
    }

}