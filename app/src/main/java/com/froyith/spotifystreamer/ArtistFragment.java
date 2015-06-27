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
import android.widget.ArrayAdapter;
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
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {
    private ArrayList<ArtistData> mDataList = new ArrayList<ArtistData>();

    //private ArrayList<String> testData = new ArrayList<String>();
    //private ArrayAdapter strArrAdapter;
    private ArtistArrayAdapter mArtistAdapter;
    private  EditText editText;//search for artist



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
            //            .putExtra(Intent.EXTRA_TEXT, "test");//this will be the artistID
            //    intent.putExtra(Intent.EXTRA_REFERRER_NAME ,"name");
                TextView v=(TextView) paramView.findViewById(R.id.list_item_artist_textview);
                intent.putExtra(Intent.EXTRA_TEXT,(String)v.getTag());
                intent.putExtra(Intent.EXTRA_REFERRER_NAME,v.getText());
                startActivity(intent);

            }
        });
        //artist search text box
        editText = (EditText) rootView.findViewById(R.id.edittext_artist);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //lookup artists
                    FetchArtistsTask fetch = new FetchArtistsTask();

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

            ArtistData adata[] = new ArtistData[results.artists.items.size()];

            int i = 0;

            ArtistData d = null;
            ArtistData artistResults[] = null;
            //ArtistData artistResults[] = new ArtistData[20];
            if (results.artists.items.size() > 0) {
                artistResults = new ArtistData[results.artists.items.size()];
                //for (i=0 ; i < 20; i++)
                for (Artist art : results.artists.items) {
                    boolean bFoundImg = false;
                    String strImgUrl = new String();
                    //grab a picture url or null if none, but in for loop, then look for an ideal
                    //image size and take that path, otherwise use what we have here
                    if (art.images.size() > 0) {
                        strImgUrl = art.images.get(0).url;
                    } else {

                        strImgUrl = null;
                    }
                    //find a better image size if available
                    for (Image img : art.images) {
                        if ((img.height <= 200) && (bFoundImg == false)){
                            strImgUrl = img.url;
                            bFoundImg = true;
                        }


                    }


                    String strID = new String("");

                    d = new ArtistData(art.name, strImgUrl, art.id);
                    artistResults[i] = d;
                    //mDataList.add(d);

                    i++;
                }

            }

            return artistResults;
        }

        @Override
        protected void onPostExecute(ArtistData[] result) {
            if (result != null) {

                mArtistAdapter.clear();

                //ArtistData adata = null;

                for (ArtistData artist : result) {
                    //adata = new ArtistData(artist.getArtistName(), artist.getArtistImage(), artist.getArtistID());
                    mArtistAdapter.add(artist);
                }


              } else {
                Toast.makeText(getActivity(), "No search results found, Please refine your search", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }


