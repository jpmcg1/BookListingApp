package com.example.android.booklistingapp;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class QueryUtils {

    // Using the appropriate URL, get the book data from the Google Books API
    public static List<Book> fetchBookData(String searchWord) {

        return null;
    }

    // Create a URL object from the searched word
    private static URL createUrlObject(String searchWord) {
        URL url = null;

        try {
            url = new URL("http://www.googleapis.com/books/v1/volumes?" + searchWord + "=search+terms");
        } catch (MalformedURLException exception) {
            Log.e("QueryUtils", "Error with creating URL", exception);
            return null;
        }

        return url;
    }
}
