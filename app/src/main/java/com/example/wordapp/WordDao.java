package com.example.wordapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insetWords(WordEntity...wordEntities);

    @Update
    void updateWords(WordEntity...wordEntities);

    @Delete
    void deleteWords(WordEntity...wordEntities);

    @Query("delete from wordentity")
    void clearAllWords();

    @Query("select * from WordEntity order by id desc")
    LiveData<List<WordEntity>> selectAllWrods();

    @Query("select * from WordEntity where english_word like:pattern order by id desc")
    List<WordEntity> findWrodWithPattern(String pattern);
    //LiveData<List<WordEntity>> findWrodWithPattern(String pattern);
}
