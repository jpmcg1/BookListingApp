package com.example.android.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String mSearchWord;

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
    }
    
    /*// Create an AsyncTask to query the Google Book API and return a list of Book objects
    private class BookAsyncTask extends AsyncTask<String, Void, List<E>> {

    }*/
}
