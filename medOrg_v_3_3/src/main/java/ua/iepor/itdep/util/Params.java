package ua.iepor.itdep.util;

import android.net.Uri;

/**
 * Created by Вiталя on 09.03.2016.
 */
public class Params {
    // System general settings
    public static final String LOG_TAG = "MedicalOrganizer";
    public static final String PREFERENCE_NAME = "shPref";

    // Content provider defines
    public static final String AUTORITY = "ua.com.kistudio.ContentProvider";

    public static final String TYPE_DOCTOR = "doctor";
    public static final String TYPE_QUESTIONS = "questions";
    public static final String TYPE_PRUZN = "pruzn";

    public static final int CODE_DOCTOR = 1;
    public static final int CODE_QUESTIONS = 2;
    public static final int CODE_PRUZN = 3;

    public static final Uri CONTENT_URI_DOCTOR = Uri.parse("content://"+AUTORITY+"/"+TYPE_DOCTOR);
    public static final Uri CONTENT_URI_QUESTIONS = Uri.parse("content://"+AUTORITY+"/"+TYPE_QUESTIONS);
    public static final Uri CONTENT_URI_PRUZN = Uri.parse("content://"+AUTORITY+"/"+TYPE_PRUZN);

    // Sizes
    public static final int STANDART_QUEST_FILES_NUM = 13;

    // Extras constants
    public static final String EXTRA_GROUPS_NAME_STANDART_QUEST = "groups_name";
    public static final String EXTRA_ID_STANDART_QUEST = "id";
    public static final String EXTRA_SIZE_OF_GROUP_STANDART_QUEST = "groups_size";
    public static final String EXTRA_GROUP_NUMBER_STANDART_QUEST = "group_number";

    public static final String EXTRA_POSITION_NUMBER_FROM_MENU = "N_qst";
    public static final String EXTRA_QUESTION_TYPE = "What";
    public static final String EXTRA_FILE_NAME_FOR_LIST = "file_name";
    public static final String DOCTOR_PREFIX = "mdc";
    public static final String DOCTOR_SUFFIX = ".dct";
    public static final int DB_VERSION = 1;

    // table doctor in DB
    public static final String DOCTOR_TABLE_NAME = "doctor";
    public static final String DOCTOR_PIB = "pib";
    public static final String DOCTOR_SPEC = "spec";
    public static final String DOCTOR_ABOUT = "about";
    public static final String DOCTOR_PHONE = "telephone";
    public static final String DOCTOR_PHOTO = "photo";
    public static final String DOCTOR_AUDIO = "audio";

    // table questions in DB
    public static final String QUESTION_TABLE_NAME = "questions";
    public static final String QUESTION_TEXT = "quest_text";
    public static final String QUESTION_ANSWER = "answer";

    // table pruznachennja in DB
    public static final String PRUZN_TABLE_NAME = "pruznachennja";
    public static final String PRUZN_TEXT = "pruzn_text";

    public static final String STANDART_QUESTION_TEXT = "text";


    public static final int NUM_OF_QUESTIONS_DIAGNOZ = 10;
}
