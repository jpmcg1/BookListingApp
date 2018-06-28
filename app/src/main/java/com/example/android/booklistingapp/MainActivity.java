package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    // User inputted search term
    private String mSearchWord;

    // Constant for the BookLoader - only relevant when you have more than one
    private static final int BOOK_lOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // When the search button is pressed on the screen, the inputted text is stored into
        // the mSearchWord variable
        Button button = findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchWord = findViewById(R.id.search_word);
                mSearchWord = searchWord.getText().toString();
                Log.e("Main Activity", "The search word is......" + mSearchWord);
            }
        });

        /*// Start the AsyncTask to fetch the book data with the user inputted word
        BookAsyncTask task = new BookAsyncTask();
        task.execute(mSearchWord);*/

        // Get a reference to the LoaderManager in order to interact with loaders
        LoaderManager loaderManager = getLoaderManager();

        // Initialse the loader
        loaderManager.initLoader(BOOK_lOADER_ID, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for a given search term
        return new BookLoader(this, mSearchWord);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        // TODO: set the view, clear the adapter, add in new adapter data
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // TODO: reset the adapter
    }

    /*// Create an AsyncTask to query the Google Book API and return a list of Book objects
    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        // In the background, if the search term is not null, get the book data
        // from the API
        @Override
        protected List<Book> doInBackground(String... searchedWord) {
            if (searchedWord.length < 1 || searchedWord[0] == null) {
                return null;
            }
            List<Book> bookList = QueryUtils.fetchBookData(searchedWord[0]);

            return bookList;
        }

        // TODO: onPostExecute method with clear adapter and add to adapter methods
    }*/
}
