package com.froyith.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.froyith.spotifystreamer.data.ArtistData;
import com.froyith.spotifystreamer.data.SongData;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;

public class SongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.song_menu_layout,null);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_songs);

        //set action bar Artist name
        TextView textView = (TextView) v.findViewById(R.id.artistNameBar);
        if (this.getIntent() == null || this.getIntent().getData() == null){
            ArtistData adata;
            adata = this.getIntent().getExtras().getParcelable("KEY");
            textView.setText(adata.getArtistName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Since not using a menu, commenting out the following:
        //getMenuInflater().inflate(R.menu.menu_songs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //{case android.R.id.home: onBackPressed();break;}

        switch (item.getItemId()) {

            case android.R.id.home:
                //not sure, the behavior of the back button seems how I'd like it, finishing current activity and resuming previous
                this.finish();
                //tried this from the back button activity bar article, and it starts a new activity
                //NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}



