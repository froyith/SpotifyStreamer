package com.froyith.spotifystreamer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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




public class SongsFragment extends Fragment {

    private ArrayAdapter<String> mSongsAdapter;






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
                "test10"
        };
        List<String> testDataList = new ArrayList<String>(Arrays.asList(testData));;
        //if (savedInstanceState != null ){
            //testData = savedInstanceState.getStringArray("myKey");
            //if (testData != null) {
            //    testDataList = new ArrayList<String>(Arrays.asList(testData));

            //}

        //}
        mSongsAdapter =
                new ArrayAdapter<String>(
                        //context
                        getActivity(),
                        R.layout.songs_list_item_layout,
                        R.id.list_item_songs_textview,
                        testDataList);

        //
//recover stored data if there is any

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_songs);

        listView.setAdapter(mSongsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt,
                                    long paramLong) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                        //.putExtra(Intent.EXTRA_TEXT, "test");



                startActivity(intent);

                // TODO whatever you want to do to this item
            }
        });


        return rootView;
    }





}
