package com.example.android.booklistingapp;

public class Book {
    // The author of the book
    private String mAuthor;

    // The title of the book
    private String mTitle;

    // The year of publication
    private int mYear;

    public Book(String author, String title, int year) {
        mAuthor = author;
        mTitle = title;
        mYear = year;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getYear() {
        return mYear;
    }
}
