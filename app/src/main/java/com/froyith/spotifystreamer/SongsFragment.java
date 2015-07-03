package com.froyith.spotifystreamer;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.froyith.spotifystreamer.data.SongData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


public class SongsFragment extends Fragment {


    private static final String COUNTRY_CODE = "US";
    private ArrayList<SongData> mDataList = new ArrayList<SongData>();
    private SongsArrayAdapter mSongsAdapter;
    private String strArtist;
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

        mDataList = new ArrayList<SongData>();//datasource to bound to our custom array adapter to list view controls
        strArtist = (String) this.getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER_NAME);

        //Recover stored data if any, othewise flag that a song search needs to be performed
        if (savedInstanceState != null && savedInstanceState.containsKey("KEY")){//try to grab our paraceable custom arraylist from savestate

            mDataList =  savedInstanceState.getParcelableArrayList("KEY");

        }else{

            FetchSongsTask fetch = new FetchSongsTask();
            fetch.execute((String)this.getActivity().getIntent().getExtras().get(Intent.EXTRA_TEXT));
        }

        mSongsAdapter = //custom adapter for song meta data
        new SongsArrayAdapter(
                //context
                getActivity(),
                R.layout.songs_list_item_layout,
                R.id.list_item_songs_textview,
                R.id.list_item_album_textview,
                R.id.song_imageview,
                mDataList);


        ListView listView = (ListView) rootView.findViewById(R.id.listview_songs);

        listView.setAdapter(mSongsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt,
                                    long paramLong) {

                SongData sd = null;


                Intent intent = new Intent(getActivity(), DetailActivity.class);

                sd = (SongData)paramAdapterView.getItemAtPosition(paramInt);
                intent.putExtra(Intent.EXTRA_REFERRER_NAME,strArtist);
                intent.putExtra(Intent.EXTRA_REFERRER,sd);

                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the list so it can be retrieved without requerying
        outState.putParcelableArrayList("KEY",mDataList);
    }


    public class FetchSongsTask extends AsyncTask<String,Void,SongData[]> {
        private final String LOG_TAG = SongsFragment.FetchSongsTask.class.getSimpleName();


        @Override
        protected SongData[] doInBackground(String... searchStr) {
            //spotify api is used to perform artist and track searches, and later streaming music
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            int i = 0;//result array index
            SongData sd = null;
            SongData songResults[] = null;
            String strImgUrl;
            boolean bFoundImg = false;

            Tracks results = null;
            try {   //just incase server changes and get bad results, log exceptions
                //must add give country code with getartisttoptracks
                Map<String, Object> options = new HashMap<>();
                options.put("country", COUNTRY_CODE);

                results = spotify.getArtistTopTrack(searchStr[0],options);

            }catch (Exception e){
                //catch RetrofitError
                Log.d(this.LOG_TAG, e.toString());

            }


            if (results != null){
            if (results.tracks.size() > 0) {
                songResults = new SongData[results.tracks.size()];

                for (Track t : results.tracks) {

                    strImgUrl = null;
                    //grab a picture url or null if none, but in for loop, then look for an ideal
                    //image size and take that path, otherwise use what we have here

                    //find a reasonably sized image if available
                    bFoundImg = false;

                    //fs: need to add song url and large image url
                    //t.preview_url; //this will be for streaming

                    for (Image img : t.album.images) {
                        if (img.height > 180 && img.height < 220 && bFoundImg == false) {
                            strImgUrl = img.url;
                            bFoundImg = true;
                        }
                    }
                    //just grab last image from end of list if suitable size one isn't available
                    if (bFoundImg == false && t.album.images.size() > 0) {
                        strImgUrl = t.album.images.get(t.album.images.size()-1).url;
                    }
                    sd = new SongData( t.name, strImgUrl,t.album.name,t.album.images.get(0).url,t.preview_url);
                    songResults[i] = sd;
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


                for (SongData song : result) {
                    mSongsAdapter.add(song);
                }


            } else {
                Toast.makeText(getActivity(), "Error finding tracks for this artist", Toast.LENGTH_SHORT).show();

            }
        }

    }


}
