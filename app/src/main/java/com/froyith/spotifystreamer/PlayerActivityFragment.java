package com.froyith.spotifystreamer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.froyith.spotifystreamer.R;
import com.froyith.spotifystreamer.data.SongData;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends DialogFragment {
    public static SongData mSongData = null;

    public static ImageButton playButton;

    public static SeekBar seekBar;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static PlayerActivityFragment newInstance(int num) {
        PlayerActivityFragment f = new PlayerActivityFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_player, container, false);


        seekBar = (SeekBar) rootView.findViewById(R.id.mediaSeekBar);


        mSongData = (SongData) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER);
        String strArtist = (String) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER_NAME);
        ImageView imgView = (ImageView) rootView.findViewById(R.id.imgAlbumDetail);
        playButton = (ImageButton) rootView.findViewById(R.id.play);

        TextView txtAlbum = (TextView) rootView.findViewById(R.id.txtAlbumDetail);

        TextView txtSong = (TextView) rootView.findViewById(R.id.txtSongDetail);
        TextView txtArtist = (TextView) rootView.findViewById(R.id.txtArtistDetail);
        txtAlbum.setText(mSongData.getmAlbumName());
        txtSong.setText(mSongData.getmSongName());
        txtArtist.setText(strArtist);
        Picasso.with(getActivity()).load(mSongData.getmLargeImage()).into(imgView);



        //show();
        //return null;
        return rootView;
    }

}
