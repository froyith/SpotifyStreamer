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
        boolean bNeedSongData = false; //flags that a song lookup for the artist needs to be performed
        //Recover stored data if any, othewise flag that a song search needs to be performed
        if (savedInstanceState != null && savedInstanceState.containsKey("KEY")){//try to grab our paraceable custom arraylist from savestate

            mDataList =  savedInstanceState.getParcelableArrayList("KEY");

        }else{

            bNeedSongData = true; //need to fetch data from spotify api
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

        if (bNeedSongData == true){

            FetchSongsTask fetch = new FetchSongsTask();
            fetch.execute((String)this.getActivity().getIntent().getExtras().get(Intent.EXTRA_TEXT));

        }


        ListView listView = (ListView) rootView.findViewById(R.id.listview_songs);

        listView.setAdapter(mSongsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt,
                                    long paramLong) {

                //not implemented yet, just opens hello world activity
                Intent intent = new Intent(getActivity(), DetailActivity.class);
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

            Tracks results = null;
            try {   //just incase server changes and get bad results, log exceptions
                //must add give country code with getartisttoptracks
                Map<String, Object> options = new HashMap<>();
                options.put("country", COUNTRY_CODE);

                results = spotify.getArtistTopTrack(searchStr[0],options);

            }catch (Exception e){
                Log.d(this.LOG_TAG, e.toString());

            }

            int i = 0;//result array index
            SongData sd = null;
            SongData songResults[] = null;
            String strImgUrl = null;
            boolean bFoundImg = false;

            if (results != null){
            if (results.tracks.size() > 0) {
                songResults = new SongData[results.tracks.size()];

                for (Track t : results.tracks) {

                    strImgUrl = null;
                    //grab a picture url or null if none, but in for loop, then look for an ideal
                    //image size and take that path, otherwise use what we have here

                    //find a better image size if available
                    for (Image img : t.album.images) {
                        if (img.height <= 200 && img.width <=200 && bFoundImg == false) {
                            strImgUrl = img.url;
                            bFoundImg = true;
                        }
                    }
                    //set it to something
                    if (bFoundImg == false && t.album.images.size() > 0) {
                        strImgUrl = t.album.images.get(0).url;
                    }
                    sd = new SongData( t.name, strImgUrl,t.album.name);
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
