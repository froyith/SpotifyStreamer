package com.froyith.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.froyith.spotifystreamer.data.ArtistData;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by fsmith on 6/16/2015.
 */
public class ArtistArrayAdapter extends ArrayAdapter<ArtistData> {
    private ArrayList<ArtistData> artistDataArrList = new ArrayList<ArtistData>();
    //private ArtistData[] artistData;
    private int intImageView;
    private int intTextView;
    private int intListItemResourceId;
    Context context;


    public ArtistArrayAdapter(Context context,int listItemResourceId, int textViewResourceId,int imgViewResourceId,ArrayList<ArtistData> adata){
        super(context,textViewResourceId,adata);
        //this.artistData = adata;
        artistDataArrList = adata;

        this.context = context;
        this.intImageView = imgViewResourceId;
        this.intTextView = textViewResourceId;
        this.intListItemResourceId = listItemResourceId;
    }


    @Override
    public void add(ArtistData adata) {
//        super.add(adata);
        artistDataArrList.add(adata);
    }



    @Override
    public void clear() {
        super.clear();
        artistDataArrList.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ArtistHolder ahold;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(intListItemResourceId, parent, false);
            ahold = new ArtistHolder();
            ahold.imgArtist = (ImageView)v.findViewById(intImageView);
            ahold.txtArtistName= (TextView)v.findViewById(intTextView);

            v.setTag(ahold);
        }

        else{
            ahold= (ArtistHolder)v.getTag();
        }


        ArtistData adata = artistDataArrList.get(position);
        //TextView txtArtistName = (TextView) v.findViewById(R.id.list_item_artist_textview);
        //ImageView iArtistImage = (ImageView) v.findViewById(R.id.artist_imageview);
        ahold.txtArtistName.setText(adata.getArtistName());
        ahold.txtArtistName.setTag(adata.getArtistID());
        ////Picasso.with(context).load(adata.getArtistImage()).into(ahold.imgArtist);
        if (adata.getArtistImage() != null) {
            Picasso.with(context).load(adata.getArtistImage()).into(ahold.imgArtist);
        }
        return v;
    }
    static class ArtistHolder
    {
        ImageView imgArtist;
        TextView txtArtistName;
    }
}
