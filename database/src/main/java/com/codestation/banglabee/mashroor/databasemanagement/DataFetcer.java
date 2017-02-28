package com.codestation.banglabee.mashroor.databasemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mashroor on 15-Feb-17.
 */

public class DataFetcer {

    private Context context;

    public DataFetcer(Context context)
    {
        this.context = context;
    }

    public ArrayList<WordModel> fetchData(int noOfWords, int difficulty) {

        Cursor cursor;

        ArrayList<WordModel> wordList = new ArrayList<WordModel>();

        String difficultyStr = "easy";

        if (difficulty == Constants.easy) {
            difficultyStr = "easy";
        }
        else if (difficulty == Constants.medium) {
            difficultyStr = "medium";
        }
        else if (difficulty == Constants.hard) {
            difficultyStr = "hard";
        }

        Set<Integer> set = new HashSet<Integer>();

        while (set.size() < noOfWords) {
            set.add((int) (Math.round(Math.random() * (Constants.max - Constants.min)) + Constants.min));
        }

        DatabaseHelper db;

        db = new DatabaseHelper(context);
        try {

           // db.createDataBase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = new String();
        SQLiteDatabase sd = db.getReadableDatabase();

        for(Integer i : set)
        {
            Log.e("SB", "index:"+i);
            //long time1 = System.currentTimeMillis();
            cursor = sd.query(difficultyStr, null, "SL = " + i, null, null, null, null);
            //long time2 = System.currentTimeMillis();

            if(cursor != null) {
                cursor.moveToFirst();
                //Log.e("SB", "col count : " + cursor.getColumnCount() + " names: " + cursor.getColumnNames() + "           ------ " + cursor.getString(cursor.getColumnIndex("BN_WORD")));

                WordModel wordModel = new WordModel(i,
                        cursor.getString(cursor.getColumnIndex("BN_WORD")).trim(),
                        cursor.getString(cursor.getColumnIndex("BN_POS")),
                        cursor.getString(cursor.getColumnIndex("BN_SYN")),
                        cursor.getString(cursor.getColumnIndex("EN_POS")),
                        cursor.getString(cursor.getColumnIndex("EN_SYN")),
                        cursor.getString(cursor.getColumnIndex("AUDIO")),
                        cursor.getInt(cursor.getColumnIndex("WEIGHT")));

                wordList.add(wordModel);

                cursor.close();
            }
            else
            {
                Log.e("SB", "Cursor NULL for table:" + difficultyStr + " SI." + i);
            }
            //long time3 = System.currentTimeMillis();

           //Log.e("SB", "query time:" + (time2-time1) + " word model populate time:" + (time3-time2));
        }

        db.close();
        return wordList;
    }


    public WordModel fetchSingleData(int id, int difficulty) {
        Cursor cursor;

        String difficultyStr = "easy";

        if (difficulty == Constants.easy) {
            difficultyStr = "easy";
        }
        else if (difficulty == Constants.medium) {
            difficultyStr = "medium";
        }
        else if (difficulty == Constants.hard) {
            difficultyStr = "hard";
        }

        DatabaseHelper db;

        db = new DatabaseHelper(context);
        try {
            //db.createDataBase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase sd = db.getReadableDatabase();

        cursor = sd.query(difficultyStr, null, "SL = " + id, null, null, null, null);
        cursor.moveToFirst();

        WordModel wordModel = new WordModel(id,
                cursor.getString(cursor.getColumnIndex("BN_WORD")).trim(),
                cursor.getString(cursor.getColumnIndex("BN_POS")),
                cursor.getString(cursor.getColumnIndex("BN_SYN")),
                cursor.getString(cursor.getColumnIndex("EN_POS")),
                cursor.getString(cursor.getColumnIndex("EN_SYN")),
                cursor.getString(cursor.getColumnIndex("AUDIO")),
                cursor.getInt(cursor.getColumnIndex("WEIGHT")));

        db.close();
        return wordModel;

    }

    public ArrayList<WordModel> fetchDataByParrern(String pattern)
    {
        //Log.e("SB", "fetchDataByParrern called pattern: " + pattern);

        Cursor cursor;
        ArrayList<WordModel> wordList = new ArrayList<WordModel>();

        DatabaseHelper db;
        db = new DatabaseHelper(context);
        try {
           // db.createDataBase();
            db.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = new String();
        SQLiteDatabase sd = db.getReadableDatabase();

        cursor = sd.query("archive", null, "BN_WORD LIKE '%" + pattern.trim() +"%'", null, null, null, null, "30");

        //Log.e("SB", "dump of cursor: "+ DatabaseUtils.dumpCursorToString(cursor));

        while (cursor.moveToNext()){

            //Log.e("SB", "col count : " + cursor.getColumnCount() + " names: " + cursor.getColumnNames() + "           ------ " + cursor.getCount() + " Query: " + "BN_WORD LIKE '%" + pattern +"%'");

            WordModel wordModel = new WordModel(cursor.getInt(cursor.getColumnIndex("SL")),
                    cursor.getString(cursor.getColumnIndex("BN_WORD")).trim(),
                    cursor.getString(cursor.getColumnIndex("BN_POS")),
                    cursor.getString(cursor.getColumnIndex("BN_SYN")),
                    cursor.getString(cursor.getColumnIndex("EN_POS")),
                    cursor.getString(cursor.getColumnIndex("EN_SYN")),
                    "",
                    0);

            wordList.add(wordModel);
        }
        cursor.close();
        db.close();

        return wordList;
    }
}
