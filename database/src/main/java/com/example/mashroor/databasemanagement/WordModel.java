package com.example.mashroor.databasemanagement;

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

    WordModel()
    {

    }

    @Override
    public String toString() {
        return "WordModel{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", banglaPOS='" + banglaPOS + '\'' +
                ", banglaDefination='" + banglaDefination + '\'' +
                ", englishPOS='" + englishPOS + '\'' +
                ", englishDefination='" + englishDefination + '\'' +
                ", weight=" + weight +
                ", audioFileName='" + audioFileName + '\'' +
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



}
