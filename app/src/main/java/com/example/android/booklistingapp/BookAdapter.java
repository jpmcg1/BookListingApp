package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> bookList) {
        // Initialise the ArrayAdapter's internal storage for the context and the list
        // The second parameter is only used when dealing with one TextView, but here we are
        // dealing with three so its not required.
        super(context, 0, bookList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is a spare view to reuse. If not, then inflate one
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the book object in the given list as defined in the method parameter
        Book currentBook = getItem(position);

        // Find the desired TextView from the xml file, and set the text in the view
        // to the book's title
        TextView bookTitleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        bookTitleTextView.setText(currentBook.getTitle());

        // Find the desired TextView from the xml file, and set the text in the view to the
        // book's author
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(currentBook.getAuthor());

        // Find te desired TextView from the xml file, and set the text n the view
        // to the published date
        TextView publishedDateTextView = (TextView) listItemView.findViewById(
                R.id.publication_year);
        publishedDateTextView.setText(currentBook.getYear());

        return listItemView;
    }
}
