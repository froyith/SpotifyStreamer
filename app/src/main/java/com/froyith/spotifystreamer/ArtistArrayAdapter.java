package com.froyith.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.froyith.spotifystreamer.data.ArtistData;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


/**
 * Created by fsmith on 6/16/2015.
 * custom arrayadapter for artist data
 * contains subclass to hold references to the bound Views
 *
 */
public class ArtistArrayAdapter extends ArrayAdapter<ArtistData> {
    private ArrayList<ArtistData> artistDataArrList = new ArrayList<ArtistData>();

    private int intImageView;
    private int intTextView;
    private int intListItemResourceId;
    Context context;


    public ArtistArrayAdapter(Context context,int listItemResourceId, int textViewResourceId,int imgViewResourceId,ArrayList<ArtistData> adata){
        super(context,textViewResourceId,adata);

        artistDataArrList = adata;

        this.context = context;
        this.intImageView = imgViewResourceId;
        this.intTextView = textViewResourceId;
        this.intListItemResourceId = listItemResourceId;
    }


    @Override
    public void add(ArtistData adata) {

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

        ahold.txtArtistName.setText(adata.getArtistName());
        ahold.txtArtistName.setTag(adata.getArtistID());

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
