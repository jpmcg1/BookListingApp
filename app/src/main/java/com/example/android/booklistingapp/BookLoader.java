package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    // User inputted word
    private String mSearchWord;

    public BookLoader(Context context, String searchWord) {
        super(context);
        mSearchWord = searchWord;
    }

    public void onStartLoading() {
        forceLoad();
    }

    // This is run on the background thread
    @Override
    public List<Book> loadInBackground() {
        if (mSearchWord == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract the list of books
        List<Book> list = QueryUtils.fetchBookData(mSearchWord);

        return list;
    }
}
