package ditsystems.com.zinger.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import ditsystems.com.zinger.db.DBHelper;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 26.04.2016.
 */
public class Provider extends ContentProvider{

    private DBHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(Const.PROVIDER_NAME_PROJECT,Const.TABLE_NAME_DIETS,Const.URI_CODE_DIETS);
        sUriMatcher.addURI(Const.PROVIDER_NAME_PROJECT,Const.TABLE_NAME_TRAININGS,Const.URI_CODE_TRAININGS);
        sUriMatcher.addURI(Const.PROVIDER_NAME_PROJECT,Const.TABLE_NAME_WATER,Const.URI_CODE_WATER);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DBHelper(getContext(),Const.DATABASE_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mDb = mDbHelper.getWritableDatabase();
        String tableName="";
        switch (sUriMatcher.match(uri)){
            case Const.URI_CODE_DIETS:
                tableName = Const.TABLE_NAME_DIETS;
                Log.d(Const.TAG_LOG_PROJECT,"Query");
                break;
            case Const.URI_CODE_TRAININGS:
                tableName = Const.TABLE_NAME_TRAININGS;
                break;
            case Const.URI_CODE_WATER:
                tableName = Const.TABLE_NAME_WATER;
                break;
        }
        Cursor cursor = mDb.query(tableName,null,selection,selectionArgs,null,null,null);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mDb = mDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri,null);
        String tableName="";
        switch (sUriMatcher.match(uri)){
            case Const.URI_CODE_DIETS:
                tableName = Const.TABLE_NAME_DIETS;
                break;
            case Const.URI_CODE_TRAININGS:
                tableName = Const.TABLE_NAME_TRAININGS;
                break;
            case Const.URI_CODE_WATER:
                tableName = Const.TABLE_NAME_WATER;
                break;
        }
        long id =mDb.insert(tableName,"",values);

        getContext().getContentResolver().notifyChange(uri,null);
        Uri resUri = ContentUris.withAppendedId(uri,id);

        return resUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
