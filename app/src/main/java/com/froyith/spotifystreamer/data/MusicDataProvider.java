package com.froyith.spotifystreamer.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by fsmith on 6/13/2015.
 */
public class MusicDataProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //final String auhority =
        final String authority = MusicDataContract.CONTENT_AUTHORITY;

        //match each type of URI
        matcher.addURI(authority,MusicDataContract.PATH_ARTIST, MusicDataContract.ARTIST);

        return matcher;

    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
