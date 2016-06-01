package ditsystems.com.zinger.util;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Android on 26.04.2016.
 */
public class Const {

    // name of const - <type>_<name>_<parent>

    // util constant
    public static final String TAG_LOG_PROJECT = "ZingerApps";

    // const of essence trainings
    public static final String TABLE_NAME_TRAININGS = "trainings";
    public static final String FIELD_ID_TRAININGS = "_id";
    public static final String FIELD_NAME_TRAININGS = "name";
    public static final String FIELD_ABOUT_TRAININGS = "about";
    public static final String FIELD_DESCRIPTION_TRAININGS = "description";


    // const of essence diets
    public static final String TABLE_NAME_DIETS = "diets";
    public static final String FIELD_ID_DIETS = "_id";
    public static final String FIELD_NAME_DIETS = "name";
    public static final String FIELD_ABOUT_DIETS = "about";
    public static final String FIELD_DESCRIPTION_DIETS = "description";

    // const of essence water
    public static final String TABLE_NAME_WATER = "water";
    public static final String FIELD_ID_WATER = "_id";
    public static final String FIELD_VOLUME_WATER = "volume";
    public static final String FIELD_DAY_WATER = "day";
    public static final String FIELD_TIME_WATER ="time";

    // content provider
    public static final String PROVIDER_NAME_PROJECT = "ditsystems.com.zinger.provider";

    public static final Uri URI_VALUE_TRAININGS = Uri.parse("content://"+PROVIDER_NAME_PROJECT+"/"+TABLE_NAME_TRAININGS);
    public static final int URI_CODE_TRAININGS = 0;

    public static final Uri URI_VALUE_DIETS = Uri.parse("content://"+PROVIDER_NAME_PROJECT+"/"+TABLE_NAME_DIETS);
    public static final int URI_CODE_DIETS = 1;

    public static final Uri URI_VALUE_WATER = Uri.parse("content://"+PROVIDER_NAME_PROJECT+"/"+TABLE_NAME_WATER);
    public static final int URI_CODE_WATER = 2;

    public static final String KEY_GENDER_SHARED = "gender";
    public static final String KEY_AGE_SHARED = "age";
    public static final String KEY_WEIGHT_SHARED = "weight";
    public static final String KEY_HEIGHT_SHARED = "height";


    public static final int DATABASE_VERSION = 6;

}
