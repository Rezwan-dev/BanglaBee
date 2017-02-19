package com.banglabee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.banglabee.mashroor.databasemanagement.WordModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rezwan on 2/19/17.
 */

public class bbDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bbdb";
    private static final String TABLE_HISTORY = "history_tb";
    private static final String TABLE_WRONG = "wrong_tb";


    private static final String KEY_ID = "id";
    private static final String HISTORY_ENTRY  = "history_entry";

    public bbDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HISTORY_ENTRY + " TEXT" + ")";
        String CREATE_WRONG_TABLE = "CREATE TABLE " + TABLE_WRONG + "("
                + KEY_ID + " INTEGER PRIMARY KEY"
                + ")";
        db.execSQL(CREATE_HISTORY_TABLE);
        db.execSQL(CREATE_WRONG_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WRONG);
        // Create tables again
        onCreate(db);
    }

    public void addHistory(ArrayList<WordModel> items) {
        JSONArray jsonArray = new JSONArray();
        for(WordModel wordModel:items){
            jsonArray.put(wordModel.toJson());
            if(wordModel.getStatus() == WordModel.Wrong){
                String id =  ""+wordModel.getId()+ ""+wordModel.getWeight();
                int convertedID  =  Integer.parseInt(id);
                Log.e("TAGGY", ""+convertedID);
                addWorng(convertedID);
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HISTORY_ENTRY, jsonArray.toString());
        db.insertWithOnConflict(TABLE_HISTORY, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<WordModel> getHistory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[] { KEY_ID,
                        HISTORY_ENTRY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        try {
            ArrayList<WordModel> wordModels  = jsonToArray(new JSONArray( cursor.getString(1)));
            return wordModels;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<WordModel> jsonToArray(JSONArray jsonArray){
        ArrayList<WordModel> wordModels  =  new ArrayList<>();
        try {

            for(int i  = 0; i < jsonArray.length(); i++){
                JSONObject object  =  new JSONObject(jsonArray.get(i).toString());
                WordModel wordModel  = new WordModel();
                wordModel.JsontoObject(object);
                wordModels.add(wordModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wordModels;
    }

    public  ArrayList<HistoryDTO> getAllHistory() {
        ArrayList<HistoryDTO> histories = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HistoryDTO history = new HistoryDTO();
                history.key = (Integer.parseInt(cursor.getString(0)));
                try {
                    history.wordModels = jsonToArray(new JSONArray(cursor.getString(1)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                histories.add(history);
            } while (cursor.moveToNext());
        }
        return histories;
    }

    public void addWorng(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, i);
        db.insert(TABLE_WRONG, null, values);
        db.close();
    }
    public ArrayList<Integer> getAllWrong(){
        ArrayList<Integer> integers = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_WRONG;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                integers.add(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        return integers;
    }
}
