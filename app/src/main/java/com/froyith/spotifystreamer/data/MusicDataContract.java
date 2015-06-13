package com.froyith.spotifystreamer.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fsmith on 6/13/2015.
 */
public class MusicDataContract {
    public static final String CONTENT_AUTHORITY = "com.froyith.spotifystreamer.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTIST = "artist";
    public static final String PATH_SONGS = "songs";
    public static final int ARTIST = 100; //CONTENT://COM.FROYITH.SPOTIFYSTREAMER.APP/ARTIST
    public static final int ARTIST_WITH_NAME = 101; //CONTENT://COM.FROYITH.SPOTIFYSTREAMER.APP/ARTIST


    //Inner class that defins the table contents of the artis
    public static final class artistEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTIST).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTIST;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTIST;
        public static final String TABLE_NAME = "artist";
        public static final String COLUMN_ARTIST_SETTINGS = "artist_settings";
        public static Uri buildArtistUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
        public static Uri buildArtistName(String artistName){
            return CONTENT_URI.buildUpon().appendPath(artistName).build();
        }
    }

}
