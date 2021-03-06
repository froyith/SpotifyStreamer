package com.froyith.spotifystreamer;

/**
 * Created by fsmith on 6/25/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.froyith.spotifystreamer.data.SongData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by fsmith on 6/16/2015.
 * custom arrayadapter for song data
 * contains subclass to hold references to the bound Views
 */
public class SongsArrayAdapter extends ArrayAdapter<SongData> {
    private ArrayList<SongData> songDataArrList = new ArrayList<SongData>();

    private int intImageView;
    private int intTextViewSong;
    private int intTextViewAlbum;
    private int intListItemResourceId;
    Context context;


    public SongsArrayAdapter(Context context,int listItemResourceId, int textViewSongResourceId, int textViewAlbumResourceId,int imgViewResourceId,ArrayList<SongData> sdata){
        super(context,listItemResourceId,textViewAlbumResourceId,sdata);

        songDataArrList = sdata;
        this.context = context;
        this.intImageView = imgViewResourceId;
        this.intTextViewSong = textViewSongResourceId;
        this.intTextViewAlbum = textViewAlbumResourceId;
        this.intListItemResourceId = listItemResourceId;
    }


    @Override
    public void add(SongData sdata) {
        songDataArrList.add(sdata);
    }



    @Override
    public void clear() {
        super.clear();
        songDataArrList.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SongHolder shold;
        if (v == null) {

            v = LayoutInflater.from(getContext()).inflate(intListItemResourceId, parent, false);
            shold = new SongHolder();
            shold.imgSong = (ImageView)v.findViewById(intImageView);
            shold.txtAlbumName= (TextView)v.findViewById(intTextViewAlbum);
            shold.txtSongName= (TextView)v.findViewById(intTextViewSong);
            v.setTag(shold);
        }

        else{
            shold= (SongHolder)v.getTag();
        }


        SongData sdata = songDataArrList.get(position);

        shold.txtAlbumName.setText(sdata.getmAlbumName());
        shold.txtSongName.setText(sdata.getmSongName());

        if (sdata.getmAlbumImage() != null) {
            Picasso.with(context).load(sdata.getmAlbumImage()).into(shold.imgSong);
        }
        return v;
    }
    static class SongHolder
    {
        ImageView imgSong;
        TextView txtSongName;
        TextView txtAlbumName;
    }
}

