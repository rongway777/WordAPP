package com.example.wordapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class WordRepository {
    private WordDao wordDao;
    private LiveData<List<WordEntity>> liveDataAllWords;

    WordRepository(Context context) {
        EnglishDataBase dataBase = EnglishDataBase.getEnglishDataBase(context.getApplicationContext());
        wordDao = dataBase.getWordDao();
        liveDataAllWords = wordDao.selectAllWrods();
    }



    //待定。
    List<WordEntity> findWordWithPattern(String pattern){
        //return wordDao.findWrodWithPattern("%"+pattern+"%");
        try {
            return new FindWordsWithPattern(wordDao).execute("%"+pattern+"%").get();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<WordEntity>();
        }
    }

    LiveData<List<WordEntity>> getLiveDataAllWords() {
        return liveDataAllWords;
    }


    void insertWords(WordEntity... wordEntities) {
        new InsertAsyncTask(wordDao).execute(wordEntities);
    }

    void updateWords(WordEntity... wordEntities) {
        new UpdateAsyncTask(wordDao).execute(wordEntities);
    }

    void deleteWords(WordEntity... wordEntities) {
        new DeleteAsyncTask(wordDao).execute(wordEntities);
    }

    void clearWords() {
        new ClearAsyncTask(wordDao).execute();
    }


    static class InsertAsyncTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.insetWords(wordEntities);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.updateWords(wordEntities);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.deleteWords(wordEntities);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        ClearAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.clearAllWords();
            return null;
        }
    }

    static class FindWordsWithPattern extends AsyncTask<String,Void,List<WordEntity>>{
        private WordDao wordDao;

        public FindWordsWithPattern(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected List<WordEntity> doInBackground(String... strings) {

            return wordDao.findWrodWithPattern(strings[0]);
        }
    }

}
