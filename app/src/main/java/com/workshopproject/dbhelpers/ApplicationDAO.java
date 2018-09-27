package com.workshopproject.dbhelpers;

/**
 * Created by HP on 26-09-2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class ApplicationDAO {
    public static final String TAG = "ApplicationDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DbHelper.COLUMN_UID,
            DbHelper.COLUMN_WID
    };

    private String[] nAllColumns = { DbHelper.COLUMN_UID,
            DbHelper.COLUMN_WID
    };

    public ApplicationDAO(Context context) {
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

    public boolean createUserApplication(String UId, long WId
    ) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_UID, UId);
        values.put(DbHelper.COLUMN_WID, WId);
        long result = mDatabase.insert(DbHelper.TABLE_APPLICATION, null, values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean Check(String Email, long Id) {

        Cursor c = mDatabase.rawQuery("SELECT 1 FROM "+DbHelper.TABLE_APPLICATION+" WHERE" +
                " TRIM("+DbHelper.COLUMN_UID+") = '"+Email.trim()+"'and "+DbHelper.COLUMN_WID+"='"+Id+"' ", null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }




}
