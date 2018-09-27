package com.workshopproject.dbhelpers;

/**
 * Created by HP on 25-09-2018.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.workshopproject.db_models.Workshop;


public class WorkshopDAO {
    public static final String TAG = "WorkshopDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DbHelper.COLUMN_WORKSHOP_ID,
            DbHelper.COLUMN_WORKSHOP_NAME, DbHelper.COLUMN_WORKSHOP_INTRODUCTION,
            DbHelper.COLUMN_WORKSHOP_TIMINGS,
            DbHelper.COLUMN_WORKSHOP_FEE };

    public WorkshopDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DbHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void createWorkshop(String name, String intro, String timing,
                                 String fee) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_WORKSHOP_NAME, name);
        values.put(DbHelper.COLUMN_WORKSHOP_INTRODUCTION, intro);
        values.put(DbHelper.COLUMN_WORKSHOP_TIMINGS, timing);
        values.put(DbHelper.COLUMN_WORKSHOP_FEE, fee);
        long insertId = mDatabase
                .insert(DbHelper.TABLE_WORKSHOP, null, values);
        Cursor cursor = mDatabase.query(DbHelper.TABLE_WORKSHOP, mAllColumns,
                DbHelper.COLUMN_WORKSHOP_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        cursor.close();
    }



    public List<Workshop> getAllWorkshop() {
        List<Workshop> listWorkshops = new ArrayList<Workshop>();

        Cursor cursor = mDatabase.query(DbHelper.TABLE_WORKSHOP, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Workshop workshop = cursorToWorkshop(cursor);
                listWorkshops.add(workshop);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listWorkshops;
    }

    public List<Workshop> getAllWorkshopById(String Id) {
        List<Workshop> listWorkshops = new ArrayList<Workshop>();

        Cursor cursor = mDatabase.rawQuery("SELECT w.workshop_name, w.workshop_intro, w.workshop_timings" +
                "  FROM "+DbHelper.TABLE_WORKSHOP+" w INNER JOIN "+DbHelper.TABLE_APPLICATION+" a" +
                " on w._id=a._id WHERE" +
                " a._email = '"+Id.trim()+"' ", null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Workshop workshop = cursorToWorkshopById(cursor);
                listWorkshops.add(workshop);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listWorkshops;
    }



    protected Workshop cursorToWorkshop(Cursor cursor) {
        Workshop workshop = new Workshop();
        workshop.setId(cursor.getInt(0));
        workshop.setName(cursor.getString(1));
        workshop.setmIntro(cursor.getString(2));
        workshop.setmTimings(cursor.getString(3));
        workshop.setmFee(cursor.getString(4));
        return workshop;
    }

    protected Workshop cursorToWorkshopById(Cursor cursor) {
        Workshop workshop = new Workshop();

        workshop.setName(cursor.getString(0));
        workshop.setmIntro(cursor.getString(1));
        workshop.setmTimings(cursor.getString(2));

        return workshop;
    }

}