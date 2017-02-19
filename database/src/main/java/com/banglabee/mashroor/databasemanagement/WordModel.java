package com.banglabee.mashroor.databasemanagement;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mashroor on 10-Feb-17.
 */

public class WordModel {

    public static final int Wrong = 2;
    public static final int Right = 1;

    int id = 0;
    String word = "";

    public String getWordInput() {
        return wordInput;
    }

    public void setWordInput(String wordInput) {
        this.wordInput = wordInput;
    }

    String wordInput = "";

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getBanglaPOS() {
        return banglaPOS;
    }

    public String getBanglaDefination() {
        return banglaDefination;
    }

    public String getEnglishPOS() {
        return englishPOS;
    }

    public String getEnglishDefination() {
        return englishDefination;
    }

    public int getWeight() {
        return weight;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    String banglaPOS = "";
    String banglaDefination = "";
    String englishPOS = "";
    String englishDefination = "";
    int weight = 0;
    String audioFileName = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int status = -1;

    public WordModel()
    {

    }

    @Override
    public String toString() {
        return "WordModel{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", wordInput='" + wordInput + '\'' +
                ", banglaPOS='" + banglaPOS + '\'' +
                ", banglaDefination='" + banglaDefination + '\'' +
                ", englishPOS='" + englishPOS + '\'' +
                ", englishDefination='" + englishDefination + '\'' +
                ", weight=" + weight +
                ", audioFileName='" + audioFileName + '\'' +
                ", status=" + status +
                '}';
    }

    WordModel(int id,
              String word,
              String banglaPOS,
              String banglaDefination,
              String englishPOS,
              String englishDefination,
              String audioFileName,
              int weight)
    {
        this.id = id;
        this.word = word;
        this.banglaPOS = banglaPOS;
        this.banglaDefination = banglaDefination;
        this.englishPOS = englishPOS;
        this.englishDefination = englishDefination;
        this.audioFileName = audioFileName;
        this.weight = weight;
    }

    public String toJson(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("word", getWord());
            jsonObject.put("banglaPOS", getBanglaPOS());
            jsonObject.put("banglaDefination", getBanglaDefination());
            jsonObject.put("englishPOS", getEnglishPOS());
            jsonObject.put("englishDefination", getEnglishDefination());
            jsonObject.put("audioFileName", getAudioFileName());
            jsonObject.put("weight", getWeight());
            jsonObject.put("wordInput", getWordInput());
            jsonObject.put("status", getStatus());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public void JsontoObject(JSONObject object){
        try {
            id = object.getInt("id");
            word = object.getString("word");
            banglaPOS  = object.getString("banglaPOS");
            banglaDefination = object.getString("banglaDefination");
            englishPOS = object.getString("englishPOS");
            englishDefination = object.getString("englishDefination");
            audioFileName = object.getString("audioFileName");
            weight = object.getInt("weight");
            wordInput = object.getString("wordInput");
            status = object.getInt("status");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
