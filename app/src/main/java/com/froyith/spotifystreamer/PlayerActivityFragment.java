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


    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
        //return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        String strArtist = null;

        seekBar = (SeekBar) rootView.findViewById(R.id.mediaSeekBar);
        if (this.getArguments() != null) {
            mSongData = this.getArguments().getParcelable("KEY");
            strArtist = this.getArguments().getString("ARTIST");
        }else if (getActivity().getIntent() != null ) {
            mSongData = (SongData) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER);
            strArtist = (String) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER_NAME);
        }
        ImageView imgView = (ImageView) rootView.findViewById(R.id.imgAlbumDetail);
        playButton = (ImageButton) rootView.findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
                intent.setAction("com.froyith.spotifystreamingservice.action.PLAY");

                intent.putExtra(Intent.EXTRA_TEXT, PlayerActivityFragment.mSongData.getmTrackURL());
                getActivity().startService(intent);
                Toast.makeText(v.getContext(), PlayerActivityFragment.mSongData.getmTrackURL(), Toast.LENGTH_SHORT).show();
                // do something
            }
        });
        TextView txtAlbum = (TextView) rootView.findViewById(R.id.txtAlbumDetail);

        TextView txtSong = (TextView) rootView.findViewById(R.id.txtSongDetail);
        TextView txtArtist = (TextView) rootView.findViewById(R.id.txtArtistDetail);
        txtAlbum.setText(mSongData.getmAlbumName());
        txtSong.setText(mSongData.getmSongName());
        txtArtist.setText(strArtist);
        Picasso.with(getActivity()).load(mSongData.getmLargeImage()).into(imgView);



        return rootView;
    }

}
