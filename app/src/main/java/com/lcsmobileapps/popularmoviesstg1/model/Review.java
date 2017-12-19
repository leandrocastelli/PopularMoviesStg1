package com.lcsmobileapps.popularmoviesstg1.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract;
import com.lcsmobileapps.popularmoviesstg1.database.MoviesContract.ReviewsEntry;
/**
 * Created by Leandro on 12/16/2017.
 */

public class Review {

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    String author;
    String content;
    String id;

    public ContentValues toContentValues(String movieID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReviewsEntry._ID, id);
        contentValues.put(ReviewsEntry.COLUMN_AUTHOR, author);
        contentValues.put(ReviewsEntry.COLUMN_CONTENT, content);
        contentValues.put(ReviewsEntry.COLUMN_MOVIE_ID, movieID);
        return contentValues;

    }

    public Review (Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ReviewsEntry._ID);
        int authorIndex = cursor.getColumnIndex(ReviewsEntry.COLUMN_AUTHOR);
        int contentIndex = cursor.getColumnIndex(ReviewsEntry.COLUMN_CONTENT);

        this.id = String.valueOf(cursor.getInt(idIndex));
        this.author = cursor.getString(authorIndex);
        this.content = cursor.getString(contentIndex);
    }
}
