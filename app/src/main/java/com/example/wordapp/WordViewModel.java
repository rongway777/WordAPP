package com.example.wordapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
    }


    LiveData<List<WordEntity>> getLiveDataAllWords(){
        return repository.getLiveDataAllWords();
    }
    List<WordEntity> findWordWithParttern(String parttern){return repository.findWordWithPattern(parttern);}

    void insertWords(WordEntity...wordEntities){
        repository.insertWords(wordEntities);
    }
    void updateWords(WordEntity...wordEntities){
        repository.updateWords(wordEntities);
    }
    void deleteWords(WordEntity...wordEntities){
        repository.deleteWords(wordEntities);
    }
    void clearWords(){
        repository.clearWords();
    }




}
