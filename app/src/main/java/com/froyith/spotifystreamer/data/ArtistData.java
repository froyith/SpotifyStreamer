package com.froyith.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fsmith on 6/15/2015.
 */
public class ArtistData  implements Parcelable {
    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public String getArtistImage() {
        return mArtistImage;
    }

    public void setArtistImage(String mArtistImage) {
        this.mArtistImage = mArtistImage;
    }

    public String getArtistID() {
        return mArtistID;
    }

    public void setArtistID(String mArtistID) {
        this.mArtistID = mArtistID;
    }

    private String mArtistName = "";
    private String mArtistImage = "";
    private String mArtistID = "";

    public ArtistData()  {
        super();

    }
    public ArtistData(String artistName,String artistImage,String artistID){
        super();
        mArtistID = artistID;
        mArtistImage = artistImage;
        mArtistName = artistName;
    }


    @Override
    public String toString() {
        return mArtistName; //make the object list behave like the original string array

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getArtistID());
        parcel.writeString(this.getArtistImage()) ;
        parcel.writeString(this.getArtistName());

    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ArtistData createFromParcel(Parcel in) { return new ArtistData(in); }
        public ArtistData[] newArray(int size) { return new ArtistData[size]; }
    };

    private ArtistData(Parcel in) {

        this.mArtistID = in.readString() ;
        this.mArtistImage= in.readString() ;
        this.mArtistName = in.readString() ;


    }
}
