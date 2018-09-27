package com.workshopproject.dbhelpers;

/**
 * Created by HP on 27-09-2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class LoginDAO {
    public static final String TAG = "LoginDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DbHelper.COLUMN_ID,
            DbHelper.COLUMN_USER_PWD
    };


    public LoginDAO(Context context) {
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

    public boolean CheckUser(String Id, String Password
    ) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ID, Id);
        values.put(DbHelper.COLUMN_USER_PWD, Password);

        Cursor c = mDatabase.rawQuery("SELECT 1 FROM " + DbHelper.TABLE_LOGIN + " WHERE" +
                " TRIM(" + DbHelper.COLUMN_ID + ") = '" + Id.trim() + "'and " + DbHelper.COLUMN_USER_PWD + "='" + Password + "' ", null);
        if (c.getCount() <= 0) {
            c.close();
            return false;
        }
        c.close();
        return true;


    }
}