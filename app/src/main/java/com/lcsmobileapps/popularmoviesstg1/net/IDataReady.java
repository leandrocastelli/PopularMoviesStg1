package com.lcsmobileapps.popularmoviesstg1.net;

import android.database.Cursor;

import com.lcsmobileapps.popularmoviesstg1.model.Movie;

import java.util.List;

/**
 * Created by leandro.silverio on 30/08/2017.
 */

public interface IDataReady {
    void swapCursor(Cursor cursor);
}
