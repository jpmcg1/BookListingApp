package com.example.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

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
        return books;
    }

    // Create a URL object from the searched word
    private static URL createUrlObject(String searchWord) {
        URL url = null;

        try {
            url = new URL("http://www.googleapis.com/books/v1/volumes?"
                    + searchWord + "=search+terms");
            Log.e("QueryUtils", "url created successfully");
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
                Log.e("QueryUtils", "URL Connection succesful");
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

    // Convert the inputstream into a String which contains the whole JSON response
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Book> extractFeatureFromJson(String bookJsonResponse) {
        // If the JSON string is null, return
        if (TextUtils.isEmpty(bookJsonResponse)) {
            return null;
        }

        // Create an empty list of books
        List<Book> books = new ArrayList<>();

        // Try to parse the JSON response.
        // If there is a problem and exception is thron.
        // Catch the exception so that the app doesn't crash andprint the error message to the log
        try {
            // Parse the JSON string and build a list of Book data.
            JSONObject root = new JSONObject(bookJsonResponse);
            JSONArray booksArray = root.getJSONArray("items");
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject book = booksArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String year = volumeInfo.getString("publishedDate");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String author = authors.getString(0);

                books.add(new Book(author, title, year));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return books;
    }
}
