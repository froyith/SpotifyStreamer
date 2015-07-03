package com.froyith.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fsmith on 6/15/2015.
 */
public class SongData implements Parcelable {


    private String mSongName = "";
    private String mAlbumName = "";
    private String mAlbumImage = "";
    //new members
    private String mLargeImage = "";

    public String getmLargeImage() {
        return mLargeImage;
    }

    public void setmLargeImage(String mLargeImage) {
        this.mLargeImage = mLargeImage;
    }

    public String getmTrackURL() {
        return mTrackURL;
    }

    public void setmTrackURL(String mTrackURL) {
        this.mTrackURL = mTrackURL;
    }

    private String mTrackURL = "";

    public SongData()  {
        super();

    }


    public SongData(String songName,String albumImage,String albumName, String largeImage, String trackURL){
        super();
        mSongName = songName;
        mAlbumImage = albumImage;
        mAlbumName = albumName;
        mLargeImage = largeImage;
        mTrackURL = trackURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAlbumImage);
        parcel.writeString(this.mAlbumName) ;
        parcel.writeString(this.mSongName);
        parcel.writeString(this.mLargeImage) ;
        parcel.writeString(this.mTrackURL);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SongData createFromParcel(Parcel in) { return new SongData(in); }
        public SongData[] newArray(int size) { return new SongData[size]; }
    };

    private SongData(Parcel in) {

        this.mAlbumImage = in.readString() ;
        this.mAlbumName= in.readString() ;
        this.mSongName = in.readString() ;
        this.mLargeImage = in.readString();
        this.mTrackURL = in.readString();

    }

    public String getmSongName() {
        return mSongName;
    }

    public void setmSongName(String mSongName) {
        this.mSongName = mSongName;
    }

    public String getmAlbumName() {
        return mAlbumName;
    }

    public void setmAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public String getmAlbumImage() {
        return mAlbumImage;
    }

    public void setmAlbumImage(String mAlbumImage) {
        this.mAlbumImage = mAlbumImage;
    }
}

