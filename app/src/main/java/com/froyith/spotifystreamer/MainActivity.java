package com.froyith.spotifystreamer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.froyith.spotifystreamer.data.ArtistData;

public class MainActivity extends AppCompatActivity implements ArtistFragment.Callback {
    public static boolean mTwoPane = false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this is for tablet

        if (findViewById(R.id.song_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            if (savedInstanceState == null) {

                getFragmentManager().beginTransaction()
                        .replace(R.id.song_detail_container, new SongsFragment(),DETAILFRAGMENT_TAG )
                        .commit();

            }

        } else {
            mTwoPane = false;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //since not using menu, commenting out the following:
        //getMenuInflater().inflate(R.layout.song_menu_layout, menu);

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


    @Override
    public void onItemSelected(ArtistData adata) {
        if (mTwoPane == true){
            Bundle args = new Bundle();
            args.putParcelable("KEY"  ,adata);
            SongsFragment fragment = new SongsFragment();
            fragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.song_detail_container,fragment,DETAILFRAGMENT_TAG)
                    .commit();

        }else{
            Intent intent = new Intent(this,SongsActivity.class)
                    .putExtra("KEY",adata);

            startActivity(intent);

        }
    }
}
