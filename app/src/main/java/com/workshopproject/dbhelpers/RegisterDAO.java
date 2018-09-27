package com.workshopproject.dbhelpers;

/**
 * Created by HP on 26-09-2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class RegisterDAO {
    public static final String TAG = "RegisterDAO";

    // Database fields
    private SQLiteDatabase mDatabase, nDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DbHelper.COLUMN_USER_ID,
            DbHelper.COLUMN_USER_NAME, DbHelper.COLUMN_USER_CONTACT
            };

    private String[] nAllColumns = { DbHelper.COLUMN_ID,
            DbHelper.COLUMN_USER_PWD
    };

    public RegisterDAO(Context context) {
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
        nDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public boolean createUser(String Id, String name, String contact, String Password
                               ) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_USER_ID, Id);
        values.put(DbHelper.COLUMN_USER_NAME, name);
        values.put(DbHelper.COLUMN_USER_CONTACT, contact);



        long result = mDatabase.insert(DbHelper.TABLE_USER, null, values);
        if (result == -1)
            return false;
        else {
            if(createLogin(Id,Password))
            return true;
            else
                return false;
        }
    }


    public boolean createLogin(String Id, String Password)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_USER_ID, Id);
        contentValues.put(DbHelper.COLUMN_USER_PWD, Password);


        long result = nDatabase.insert(DbHelper.TABLE_LOGIN, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


}