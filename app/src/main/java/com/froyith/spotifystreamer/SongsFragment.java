package com.froyith.spotifystreamer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.froyith.spotifystreamer.data.ArtistData;
import com.froyith.spotifystreamer.data.SongData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


public class SongsFragment extends Fragment {


    private ArrayList<SongData> mDataList = new ArrayList<SongData>();

    private SongsArrayAdapter mSongsAdapter;






    public SongsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_songs,container,false);





        mDataList = new ArrayList<SongData>();
        boolean bNeedSongData = false;

        if (savedInstanceState != null && savedInstanceState.containsKey("KEY")){
            mDataList =  savedInstanceState.getParcelableArrayList("KEY");

        }else{

            bNeedSongData = true;
        }

        mSongsAdapter =
        new SongsArrayAdapter(
                //context
                getActivity(),
                R.layout.songs_list_item_layout,
                R.id.list_item_songs_textview,
                R.id.list_item_album_textview,
                R.id.song_imageview,
                mDataList);

        if (bNeedSongData == true){

            FetchSongsTask fetch = new FetchSongsTask();
            fetch.execute((String)this.getActivity().getIntent().getExtras().get(Intent.EXTRA_TEXT));

        }
//recover stored data if there is any

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_songs);

        listView.setAdapter(mSongsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt,
                                    long paramLong) {


                Intent intent = new Intent(getActivity(), DetailActivity.class);
                       // .putExtra(Intent.EXTRA_TEXT, "test");

                startActivity(intent);

            }
        });



        return rootView;
    }


    public class FetchSongsTask extends AsyncTask<String,Void,SongData[]> {
        private final String LOG_TAG = SongsFragment.FetchSongsTask.class.getSimpleName();


        @Override
        protected SongData[] doInBackground(String... searchStr) {
            //given code example
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            String str = null;
            //Toast.makeText(getActivity(), "34343", Toast.LENGTH_SHORT).show();
            Tracks results = null;
            try {
                //must add give country code with getartisttoptracks
                Map<String, Object> options = new HashMap<>();
                options.put("country", "US");


                results = spotify.getArtistTopTrack(searchStr[0],options);
                //results = (Tracks)test;
            }catch (Exception e){
                Log.d("me", "doInBackground error : " + e.toString());

            }


            int i = 0;

            SongData d = null;
            SongData songResults[] = null;
            String strImgUrl = null;

            if (results != null){
            if (results.tracks.size() > 0) {
                songResults = new SongData[results.tracks.size()];

                for (Track t : results.tracks) {
                    boolean bFoundImg = false;
                    strImgUrl = new String();
                    //grab a picture url or null if none, but in for loop, then look for an ideal
                    //image size and take that path, otherwise use what we have here
                    if (t.album.images.size() > 0) {
                        strImgUrl = t.album.images.get(0).url;
                    } else {

                        strImgUrl = null;
                    }
                    //find a better image size if available
                    for (Image img : t.album.images) {
                        if (img.height <= 200 && img.width <=200 && bFoundImg == false) {
                            strImgUrl = img.url;
                            bFoundImg = true;
                        }
                    }

                    d = new SongData( t.name, strImgUrl,t.album.name);
                    songResults[i] = d;


                    i++;
                }
            }
            }

            return songResults;
        }

        @Override
        protected void onPostExecute(SongData[] result) {
            if (result != null) {

                mSongsAdapter.clear();

                //SongData sdata = null;

                for (SongData song : result) {
                    //sdata = new SongData(artist.getArtistName(), artist.getArtistImage(), artist.getArtistID());
                    mSongsAdapter.add(song);
                }


            } else {
                Toast.makeText(getActivity(), "Error finding tracks for this artist", Toast.LENGTH_SHORT).show();

            }
        }

    }


}
