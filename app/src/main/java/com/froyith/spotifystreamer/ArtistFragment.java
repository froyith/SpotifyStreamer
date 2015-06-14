package com.froyith.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {

    private ArrayAdapter<String> mArtistAdapter;
    private  EditText editText;
    public ArtistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist,container,false);



        String[] testData;

                testData =new String[] {

                "test1",
                "test2",
                "test3",
                "test4",
                "test5",
                "test6",
                "test7",
                "test8",
                "test9",
                "test10",
                "test11",
                "test12",
                "test13",
                "test14",
                "test15",
                "test16",
                "test17",
                "test18",
                "test19",
                "test20",

        };

        List<String> testDataList = new ArrayList<String>(Arrays.asList(testData));;
        if (savedInstanceState != null ){
             testData = savedInstanceState.getStringArray("myKey");
            if (testData != null) {
                 testDataList = new ArrayList<String>(Arrays.asList(testData));

            }

        }
         mArtistAdapter =
                 new ArrayAdapter<String>(
                        //context
                        getActivity(),
                        R.layout.artist_list_item_layout,
                        R.id.list_item_artist_textview,
                        testDataList);

       //
//recover stored data if there is any

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_artist);

        listView.setAdapter(mArtistAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt,
                                    long paramLong) {
                Intent intent = new Intent(getActivity(), SongsActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, "test");



                startActivity(intent);

                // TODO whatever you want to do to this item
            }
        });

        editText = (EditText) rootView.findViewById(R.id.edittext_artist);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //lookup artists
                    Toast.makeText(getActivity(),"done pressed",Toast.LENGTH_SHORT ).show();
                    FetchArtistsTask fetch = new FetchArtistsTask();
                    //pass params
                    fetch.execute(editText.getText().toString());
                }

                return false;
            }
        });

        return rootView;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String[] values = new String[mArtistAdapter.getCount()];
        for (int i = 0; i < mArtistAdapter.getCount(); i++)
        {
            values[i] = mArtistAdapter.getItem(i);
        }
        outState.putStringArray("myKey",values);
    }


    public class FetchArtistsTask extends AsyncTask<String,Void,String[]> {
        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();




        @Override
        protected String[] doInBackground(String... searchStr) {
            //given code example
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(searchStr[0]);
            //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            int i = 0;
            String strResults[] = new  String[results.artists.items.size()] ;
            for (Artist art : results.artists.items)
            {
                strResults[i] = art.name;
                i++;

            }


            return strResults;
        }

        @Override
        protected void onPostExecute(String[] result) {
          if (result != null) {
              mArtistAdapter.clear();
              for (String artistStr : result) {
                mArtistAdapter.add(artistStr);
              }

          }

        }
    }

    }


