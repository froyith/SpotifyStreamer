package com.froyith.spotifystreamer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.froyith.spotifystreamer.data.SongData;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
private SongData mSongData = null;
    public static SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //strTest = (String)this.getIntent().getExtras().get(Intent.EXTRA_TEXT);
        //intent.putExtra(Intent.EXTRA_REFERRER_NAME,sd.getmSongName());
        //intent.putExtra(Intent.EXTRA_REFERRER,sd.getmAlbumName());
        seekBar = (SeekBar) findViewById(R.id.mediaSeekBar);

        mSongData = (SongData) this.getIntent().getExtras().get(Intent.EXTRA_REFERRER);
        String strArtist = (String) this.getIntent().getExtras().get(Intent.EXTRA_REFERRER_NAME);
        ImageView imgView = (ImageView) findViewById(R.id.imgAlbumDetail);

        TextView txtAlbum =(TextView) findViewById(R.id.txtAlbumDetail);

        TextView txtSong =(TextView) findViewById(R.id.txtSongDetail);
        TextView txtArtist =(TextView) findViewById(R.id.txtArtistDetail);
        txtAlbum.setText(mSongData.getmAlbumName());
        txtSong.setText(mSongData.getmSongName());
        txtArtist.setText(strArtist);
        Picasso.with(getApplicationContext()).load(mSongData.getmLargeImage()).into(imgView);

    }

    @Override
    protected void onDestroy() {
        //super.onDestroy();
        //stopService(playIntent);
        //musicSrv=null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void playerHandler(View v){
        //
        //v.setImageResource(android.R.drawable.ic_media_pause);
       Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PLAY");

        intent.putExtra(Intent.EXTRA_TEXT, mSongData.getmTrackURL());
        startService(intent);
        Toast.makeText(v.getContext(), "playing", Toast.LENGTH_SHORT).show();

    }
    public void pauseHandler(View v){
        //
        //v.setImageResource(android.R.drawable.ic_media_pause);
        Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PAUSE");

        intent.putExtra(Intent.EXTRA_TEXT,mSongData.getmTrackURL());
        startService(intent);
        Toast.makeText(v.getContext(), "pause", Toast.LENGTH_SHORT).show();

    }
}
