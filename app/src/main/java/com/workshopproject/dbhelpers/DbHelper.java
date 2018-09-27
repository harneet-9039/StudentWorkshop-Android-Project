package com.workshopproject.dbhelpers;

/**
 * Created by HP on 25-09-2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // columns of the register table
    public static final String TABLE_USER = "register";
    public static final String COLUMN_USER_ID = "_email";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_CONTACT = "mobile";


    // columns of the workshop table
    public static final String TABLE_WORKSHOP = "workshop";
    public static final String COLUMN_WORKSHOP_ID = "_id";
    public static final String COLUMN_WORKSHOP_NAME = "workshop_name";
    public static final String COLUMN_WORKSHOP_INTRODUCTION = "workshop_intro";
    public static final String COLUMN_WORKSHOP_TIMINGS = "workshop_timings";
    public static final String COLUMN_WORKSHOP_FEE = "workshop_fee";

    // columns of the login table
    public static final String TABLE_LOGIN = "login";
    public static final String COLUMN_ID = COLUMN_USER_ID;
    public static final String COLUMN_USER_PWD = "password";

    // columns of the applications table
    public static final String TABLE_APPLICATION = "applications";
    public static final String COLUMN_UID = COLUMN_USER_ID;
    public static final String COLUMN_WID = COLUMN_WORKSHOP_ID;


    private static final String DATABASE_NAME = "workshop.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the register table creation
    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " TEXT PRIMARY KEY, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_CONTACT + " TEXT NOT NULL "
            +");";

    // SQL statement of the workshops table creation
    private static final String SQL_CREATE_TABLE_WORKSHOP = "CREATE TABLE " + TABLE_WORKSHOP + "("
            + COLUMN_WORKSHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_WORKSHOP_NAME + " TEXT NOT NULL, "
            + COLUMN_WORKSHOP_INTRODUCTION + " TEXT NOT NULL, "
            + COLUMN_WORKSHOP_TIMINGS + " TEXT NOT NULL, "
            + COLUMN_WORKSHOP_FEE + " TEXT NOT NULL "
            +");";

    // SQL statement of the login table creation
    private static final String SQL_CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
            + COLUMN_ID + " TEXT NOT NULL, "
            + COLUMN_USER_PWD + " TEXT NOT NULL "
            +");";
    // SQL statement of the application table creation
    private static final String SQL_CREATE_TABLE_APPLICATION = "CREATE TABLE " + TABLE_APPLICATION + "("
            + COLUMN_UID + " TEXT NOT NULL, "
            + COLUMN_WID + " INTEGER NOT NULL "
            +");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_USER);
        database.execSQL(SQL_CREATE_TABLE_WORKSHOP);
        database.execSQL(SQL_CREATE_TABLE_LOGIN);
        database.execSQL(SQL_CREATE_TABLE_APPLICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKSHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATION);


        // recreate the tables
        onCreate(db);
    }

    public DbHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
