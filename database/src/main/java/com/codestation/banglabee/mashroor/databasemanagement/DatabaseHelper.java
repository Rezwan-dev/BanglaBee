package com.codestation.banglabee.mashroor.databasemanagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by mashroor on 10-Feb-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "";

    private static final String DATABASE_VERSION_KEY =  "db_version";

    private static final int CURRENT_DATABASE_VERSION  = 1;

//    private static String DB_NAME = "sbtemp.sqlite";
    private static String DB_NAME = "spellingbee.sqlite";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH= myContext.getDatabasePath(DB_NAME).toString();
        initialize();
    }

    private void initialize() {
        if (checkDataBase()) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(myContext);
            int dbVersion = prefs.getInt(DATABASE_VERSION_KEY, 0);
            //
            if (CURRENT_DATABASE_VERSION != dbVersion) {
                File dbFile = myContext.getDatabasePath(DB_NAME);
                // delete the old databse
                if (!dbFile.delete()) {
                    Log.e("GAYY", "Unable to update database");
                }
            }
        }
        // create database if needed
        if (!checkDataBase()) {
            createDataBase();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    private void createDataBase() {
        if (!checkDataBase()) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();

            try {
                copyDataBase();

                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(myContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(DATABASE_VERSION_KEY, CURRENT_DATABASE_VERSION);
                editor.apply();
            } catch (IOException e) {
                Log.e("GAYY", "Unable to create database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH ;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH ;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.


    //add your public methods for insert, get, delete and update data in database.

}


