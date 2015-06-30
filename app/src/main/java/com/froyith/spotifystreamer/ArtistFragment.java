package com.froyith.spotifystreamer;

import com.froyith.spotifystreamer.data.ArtistData;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;


/**
 * Frank Smith
 * 6/28/15
 * Udacity project 1A
 * Artist Fragment
 * Contains list view of artist search data
 * Artist thumbnails using picaso, list of artist from spotify api
 */
public class ArtistFragment extends Fragment {
    private ArrayList<ArtistData> mDataList = new ArrayList<ArtistData>();
    private ArtistArrayAdapter mArtistAdapter;
    private  EditText txtSearchArtist;//search for artist

    public ArtistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist,container,false);


        if (savedInstanceState != null && savedInstanceState.containsKey("KEY")){
             mDataList =  savedInstanceState.getParcelableArrayList("KEY");

        }

        mArtistAdapter =
                new ArtistArrayAdapter(
                        //context
                        getActivity(),
                        R.layout.artist_list_item_layout,
                        R.id.list_item_artist_textview,
                        R.id.artist_imageview,
                        mDataList);


        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_artist);


        listView.setAdapter(mArtistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                Intent intent = new Intent(getActivity(), SongsActivity.class);
                TextView v=(TextView) paramView.findViewById(R.id.list_item_artist_textview);
                intent.putExtra(Intent.EXTRA_TEXT,(String)v.getTag());
                intent.putExtra(Intent.EXTRA_REFERRER_NAME,v.getText());
                startActivity(intent);

            }
        });
        //artist search text box
        txtSearchArtist = (EditText) rootView.findViewById(R.id.edittext_artist);
        txtSearchArtist.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //lookup artists
                    FetchArtistsTask fetch = new FetchArtistsTask();

                    fetch.execute(txtSearchArtist.getText().toString());
                }

                return false;
            }
        });




        return rootView;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<ArtistData> test = new ArrayList<ArtistData>();
        outState.putParcelableArrayList("KEY",mDataList);
    }


    public class FetchArtistsTask extends AsyncTask<String,Void,ArtistData[]> {
        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();


        @Override
        protected ArtistData[] doInBackground(String... searchStr) {
            //given code example
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(searchStr[0]);
            boolean bFoundImg = false;
            String strImgUrl = null;
            int i = 0;

            ArtistData ad = null;
            ArtistData artistResults[] = null;
            //process the results, filling data into custom arraylist of artist search results
            if (results.artists.items.size() > 0) {
                artistResults = new ArtistData[results.artists.items.size()];

                for (Artist art : results.artists.items) {

                    //grab a picture url or null if none, but in for loop, then look for an ideal
                    //image size and take that path, otherwise use what we have here

                    //find suitable image size if available
                    bFoundImg = false;
                    for (Image img : art.images) {
                        if (img.height > 180 && img.height < 220 && bFoundImg == false){
                            strImgUrl = img.url;
                            bFoundImg = true;
                        }

                    }
                    //just grab last image from end of list if suitable size one isn't available
                    if (bFoundImg == false && art.images.size() > 0){
                        strImgUrl = art.images.get(art.images.size()-1).url;
                    }

                    ad = new ArtistData(art.name, strImgUrl, art.id);
                    artistResults[i] = ad;
                    i++;
                }

            }

            return artistResults;
        }

        @Override
        protected void onPostExecute(ArtistData[] result) {
            if (result != null) {

                mArtistAdapter.clear();


                for (ArtistData artist : result) {
                       mArtistAdapter.add(artist);
                }


              } else {
                Toast.makeText(getActivity(), "No search results found, Please refine your search", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }


