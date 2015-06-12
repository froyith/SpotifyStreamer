package com.froyith.spotifystreamer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mArtistAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        String[] testData = {

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

        List<String> testDataList = new ArrayList<String>(Arrays.asList(testData));

         mArtistAdapter =
                 new ArrayAdapter<String>(
                        //context
                        getActivity(),
                        R.layout.artist_list_item_layout,
                        R.id.list_item_artist_textview,
                        testDataList);

       //

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


        //inflater.inflate(R.layout.fragment_main, container, false)
        return rootView;


    }



    }


