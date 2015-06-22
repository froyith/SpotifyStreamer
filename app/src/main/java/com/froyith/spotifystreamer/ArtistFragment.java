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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtistFragment extends Fragment {
    private ArrayList<ArtistData> mDataList = new ArrayList<ArtistData>();

    private ArrayList<String> testData = new ArrayList<String>();
    private ArrayAdapter strArrAdapter;
    private ArtistArrayAdapter mArtistAdapter;
    private  EditText editText;

    ArtistData adataArr[] = new ArtistData[]
            {
                new ArtistData("testdata1","",""),
                new ArtistData("testdata2","","")
        };

String strTestArr[] = new String[]
        {
                new String("test1"),
                new String("test2")
        };

    public ArtistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist,container,false);





        //List<String> testDataList = new ArrayList<String>(Arrays.asList(testData));;
        if (savedInstanceState != null && savedInstanceState.containsKey("KEY")){
             mDataList =  savedInstanceState.getParcelableArrayList("KEY");
            //if (testData != null) {

                //ArrayList<User> newUsers = User.fromJson(jsonArray)
                //adapter.addAll(newUsers);
                //ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
                //        android.R.layout.simple_list_item_1, items);

                //listView1.setAdapter(adapter);
            //}
//ArtistData adata = new ArtistData("test","","");
//mDataList.add(adata);
        }


        strArrAdapter = new ArrayAdapter( getActivity(),
                R.layout.artist_list_item_layout,
                R.id.list_item_artist_textview,
                strTestArr);

        mArtistAdapter =
                new ArtistArrayAdapter(
                        //context
                        getActivity(),
                        R.layout.artist_list_item_layout,
                        R.id.list_item_artist_textview,
                        R.id.artist_imageview,
                        mDataList);

       //
//recover stored data if there is any

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_artist);

        View header = (View)inflater.inflate(R.layout.artist_list_item_layout, null);
        //listView.setAdapter(strArrAdapter);
        listView.addHeaderView(header);
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
        /*
        String[] artistNames = new String[mArtistAdapter.getCount()];
        String[] artistPath = new String[mArtistAdapter.getCount()];
        String[] artistID = new String[mArtistAdapter.getCount()];
        for (int i = 0; i < mArtistAdapter.getCount(); i++)
        {
            artistNames[i] = mArtistAdapter.getItem(i).getArtistName();
        }


        outState.putStringArray("artistNames",artistNames);

onSaveInstanceState(outState);
*/
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

            //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            int i = 0;
            //String strResults[] = new  String[results.artists.items.size()] ;
            ArtistData d = null;
            //ArtistData artistResults[] = new ArtistData[20];

            ArtistData artistResults[] = new ArtistData[results.artists.items.size()];
            //for (i=0 ; i < 20; i++)
            for (Artist art : results.artists.items) {
                boolean bFoundImg = false;
                //adata[i].ArtistName = new String(art.name);
                String strImgUrl = new String();
                if (art.images.size() > 0) {
                    strImgUrl = art.images.get(0).url;
                } else {
                    //crashes without this...
                    strImgUrl = "http://i.imgur.com/DvpvklR.png";
                }
                for (Image img : art.images) {
                    if (img.height <= 200)
                        strImgUrl = img.url;
                    bFoundImg = true;
                }
                if (bFoundImg = false) {
                    strImgUrl = art.images.get(art.images.size() - 1).url; //get last if cant find right size
                }

                //d = new ArtistData(art.name,art.images.get(0).url,art.id);
                String strID = new String("");
                //if art.id != null then
                //   strId = art.id
                d = new ArtistData(art.name, strImgUrl, art.id);
                artistResults[i] = d;
                mDataList.add(d);
                //adata[i].ArtistID = art.id;

                //strResults[i] = art.name; //+ art.id
                //
                //artistResults[i].setArtistName( art.name);
                //artistResults[i].setArtistImage("http://i.imgur.com/DvpvklR.png") ;
                //artistResults[i].setArtistImage(art.images.get(1).url);
                //artistResults[i].setArtistID(art.id);
                //artistResults[i] = d;
                i++;

            }

            // mDataList.add(d);
            //return adata;
            return artistResults;
        }

        @Override
        protected void onPostExecute(ArtistData[] result) {
            if (result != null) {
                //mArtistAdapter.add(mDataList.get(1));
                mArtistAdapter.clear();
                // mArtistAdapter.addAll(Arrays.asList(result));

                ArtistData adata = null;

                for (ArtistData artist : result) {
                    adata = new ArtistData(artist.getArtistName(), artist.getArtistImage(), artist.getArtistID());
                    mArtistAdapter.add(artist);
                }


              } else {
                Toast.makeText(getActivity(), "No search results found, Please refine your search", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }


