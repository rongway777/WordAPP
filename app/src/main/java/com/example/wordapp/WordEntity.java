package com.example.wordapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "english_word")
    private String Word;

    @ColumnInfo(name = "chinese_mean")
    private String ChineseMeaning;

    WordEntity(String word, String chineseMeaning) {
        this.Word = word;
        this.ChineseMeaning = chineseMeaning;
    }

    WordEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getChineseMeaning() {
        return ChineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        ChineseMeaning = chineseMeaning;
    }
}
