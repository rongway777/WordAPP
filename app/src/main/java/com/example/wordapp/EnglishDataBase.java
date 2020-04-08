package com.example.wordapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WordEntity.class},version = 1,exportSchema = false)
public abstract  class EnglishDataBase extends RoomDatabase {

    private static EnglishDataBase DATABASENSTANCE;

    static synchronized EnglishDataBase getEnglishDataBase(Context context){
        if(DATABASENSTANCE == null){
            DATABASENSTANCE = Room.databaseBuilder(context.getApplicationContext(),EnglishDataBase.class,"english_database")
                    //.allowMainThreadQueries()
                    .build();
        }
        return DATABASENSTANCE;
    }
    public abstract WordDao getWordDao();
}
