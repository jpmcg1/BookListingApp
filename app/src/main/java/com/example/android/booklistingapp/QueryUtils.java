package com.example.android.booklistingapp;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class QueryUtils {

    // Using the appropriate URL, get the book data from the Google Books API
    public static List<Book> fetchBookData(String searchWord) {
        // Create URL object
        URL url = createUrlObject(searchWord);

        // Perform a HTTP request and receive a JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException exception) {
            Log.e("QueryUtils", "Problem making HTTP request", exception);
        }

        // Parse and extract the relevant fields from the JSON response and create a list of
        // Book objects
        List<Book> books = extractFeatureFromJson(jsonResponse);
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

    // Make a HTTP response to the given URL and return a String.
    // The string is the entire JSON response which is generated using the readFromStream()
    // method
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is empty, return early
        if (url == null) {
            return jsonResponse;
        }

        // Create a HttpURLConnection object and InputStream object ready for assignment
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            // If the request was successful the response code is 200, read the input stream
            // and put the response in a String
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem resolving the JSON results, e");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        return null;
    }

    public static List<Book> extractFeatureFromJson(String bookJsonResponse) {
        return null;
    }
}
