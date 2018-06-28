package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    // User inputted search term
    private String mSearchWord;

    // Constant for the BookLoader - only relevant when you have more than one
    private static final int BOOK_LOADER_ID = 1;

    // TextView that is displayed whe the list is empty
    private TextView mEmptyTextView;

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

        // Find a reference to the ListView in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Set the empty state TextView onto the ListView
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyTextView);

        // TODO: create a new adapter for Book objects that takes an empty list as input
        // TODO: and set the adapter to the listview

        /*// Start the AsyncTask to fetch the book data with the user inputted word
        BookAsyncTask task = new BookAsyncTask();
        task.execute(mSearchWord);*/

        // Get a reference to the ConnectivityManager to check state of network connectivity
        // i.e. check if connected to the internet
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data netwok
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();

            // Initialse the loader
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // First, hide loading indicator so error message will be visible as they both
            // overlap in the centre of the screen
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
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
    }*/
}
