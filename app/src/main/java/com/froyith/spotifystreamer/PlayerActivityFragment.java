package com.froyith.spotifystreamer;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.froyith.spotifystreamer.R;
import com.froyith.spotifystreamer.data.SongData;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends DialogFragment {
    private SongData mSongData = null;


    public static ImageButton playButton;
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
        mSongData = (SongData) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER);
        String strArtist = (String) getActivity().getIntent().getExtras().get(Intent.EXTRA_REFERRER_NAME);
        ImageView imgView = (ImageView) rootView.findViewById(R.id.imgAlbumDetail);
        playButton = (ImageButton) rootView.findViewById(R.id.play);

        TextView txtAlbum =(TextView) rootView.findViewById(R.id.txtAlbumDetail);

        TextView txtSong =(TextView) rootView.findViewById(R.id.txtSongDetail);
        TextView txtArtist =(TextView) rootView.findViewById(R.id.txtArtistDetail);
        txtAlbum.setText(mSongData.getmAlbumName());
        txtSong.setText(mSongData.getmSongName());
        txtArtist.setText(strArtist);
        Picasso.with(getActivity()).load(mSongData.getmLargeImage()).into(imgView);

        return rootView;
    }
    public void playerHandler(View v){
        //
        //v.setImageResource(android.R.drawable.ic_media_pause);
        Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PLAY");

        intent.putExtra(Intent.EXTRA_TEXT, mSongData.getmTrackURL());
        getActivity().startService(intent);
        Toast.makeText(v.getContext(), "playing", Toast.LENGTH_SHORT).show();

    }
    public void pauseHandler(View v){
        //
        //v.setImageResource(android.R.drawable.ic_media_pause);
        Intent intent = new Intent(v.getContext(), SpotifyStreamingService.class);
        intent.setAction("com.froyith.spotifystreamingservice.action.PAUSE");

        intent.putExtra(Intent.EXTRA_TEXT,mSongData.getmTrackURL());
        getActivity().startService(intent);
        Toast.makeText(v.getContext(), "pause", Toast.LENGTH_SHORT).show();

    }
}
