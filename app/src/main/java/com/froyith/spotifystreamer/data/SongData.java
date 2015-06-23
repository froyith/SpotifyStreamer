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

    public SongData()  {
        super();

    }
    public SongData(String songName,String albumImage,String albumName){
        super();
        mSongName = songName;
        mAlbumImage = albumImage;
        mAlbumName = albumName;
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

    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SongData createFromParcel(Parcel in) { return new SongData(in); }
        public SongData[] newArray(int size) { return new SongData[size]; }
    };

    private SongData(Parcel in) {

        this.mAlbumImage = in.readString() ;
        this.mAlbumName= in.readString() ;
        this.mSongName = in.readString() ;


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

