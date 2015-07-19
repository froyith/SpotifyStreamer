package com.froyith.spotifystreamer;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.froyith.spotifystreamer.R;

public class PlayerActivity extends AppCompatActivity {
    private int mStackLevel;
    public void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PlayerActivityFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //this shows the dialog, but the controls currently crash,
        //also has a player underneith that plays if you hit the back button to clear the dialog
        //hopefully figure it out soon...
        showDialog();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
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
       //// //v.setImageResource(android.R.drawable.ic_media_pause);
        Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PLAY");

        intent.putExtra(Intent.EXTRA_TEXT, PlayerActivityFragment.mSongData.getmTrackURL());
        this.startService(intent);
        Toast.makeText(v.getContext(), PlayerActivityFragment.mSongData.getmTrackURL(), Toast.LENGTH_SHORT).show();

    }
    public void pauseHandler(View v){
        //
        //v.setImageResource(android.R.drawable.ic_media_pause);
        Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PAUSE");

        intent.putExtra(Intent.EXTRA_TEXT, PlayerActivityFragment.mSongData.getmTrackURL());
        this.startService(intent);
        Toast.makeText(v.getContext(), "pause", Toast.LENGTH_SHORT).show();

    }

}
