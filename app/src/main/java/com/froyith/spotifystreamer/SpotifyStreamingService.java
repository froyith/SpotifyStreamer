package com.froyith.spotifystreamer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.SeekBar;

public class SpotifyStreamingService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,Runnable{

    private MediaPlayer mediaPlayer = null;
    private final String LOG_TAG = SpotifyStreamer.class.getSimpleName();

    //maybe create an onProgresschanged listener here to seek to position in the stream
    private SeekBar seekBar;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        final String strURL = (String) intent.getExtras().get(Intent.EXTRA_TEXT);
        handleActionPlay(strURL);
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.LOG_TAG, "fs: " + "stop killing my service!");
    }

    public SpotifyStreamingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }
public void init(){
    mediaPlayer = new MediaPlayer();
    mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnErrorListener(this);
    seekBar =  DetailActivity.seekBar;
}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void handleActionPlay(String url) {
        //from developer.android.com - mediaplayer
        Log.d(this.LOG_TAG, "fs:playing: " + url);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.release();
                init();
            }


                try {

                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync(); //using asic version since it might take long! (for buffering, etc)

                } catch (Exception e) {
                    Log.d(this.LOG_TAG, "fs:err " + e.toString());
                }
            }


    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.d(this.LOG_TAG, "fs:mediaerr: ");
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        //seekBar.setMax(30);
        Log.d(this.LOG_TAG, "fs:" + mediaPlayer.getDuration());
        //seekBar.setProgress(15000);
        new Thread(this).start();
        //seekBar.setProgress(mediaPlayer.getCurrentPosition());
    }

    @Override
    public void run() {
        int currentPosition= 0;
        String s;
        int total = mediaPlayer.getDuration();
        while (mediaPlayer!=null && currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= mediaPlayer.getCurrentPosition();

            } catch (Exception e) {
                return;
            }

            seekBar.setProgress(currentPosition);


        }


    }
/*
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        Log.d(this.LOG_TAG, "fs:updatebar: " +mediaPlayer.isPlaying() + (seekBar == null));
        if (mediaPlayer.isPlaying() && seekBar!=null) {

            seekBar.setProgress(mediaPlayer.getCurrentPosition());

        }
    }
*/


}

