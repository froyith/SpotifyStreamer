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
        MediaPlayer.OnCompletionListener,Runnable, SeekBar.OnSeekBarChangeListener{

    private MediaPlayer mediaPlayer = null;
    private final String LOG_TAG = SpotifyStreamingService.class.getSimpleName();
    private Thread thread;
    //maybe create an onProgresschanged listener here to seek to position in the stream
    private SeekBar seekBar;

    private boolean running = false;
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
        Log.d(this.LOG_TAG, "fs:destroyed");
    }

    public SpotifyStreamingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        seekBar =  PlayerActivityFragment.seekBar;
        seekBar.setOnSeekBarChangeListener(this);
        initMediaPlayer();
    }

public void initMediaPlayer(){

    mediaPlayer = new MediaPlayer();
    mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnErrorListener(this);


}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

        return null;
    }

    private void handleActionPlay(String url) {
        //from developer.android.com - mediaplayer
        Boolean bPlaying = false;
        Log.d(this.LOG_TAG, "fs:playing: " + url);

        if (mediaPlayer == null)
            initMediaPlayer();
        else {
            //

            try{
                bPlaying = mediaPlayer.isPlaying();

            } catch (Exception e){
                //get java.lang.IllegalStateException when song finishes and its not playing
                //maybe have to do something on onCompletion to get it's state right?

                bPlaying = false;
            }
            if (bPlaying == true) {
                //mediaPlayer.release();
                //init();
                PlayerActivityFragment.playButton.setImageResource(android.R.drawable.ic_media_play);//R.drawable.ic_media_play);

                mediaPlayer.pause();
            }
            else{
                initMediaPlayer();
            }
        }

                try {

                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync(); //using asic version since it might take long! (for buffering, etc)

                } catch (Exception e) {
                    Log.d(this.LOG_TAG, "fs:err " + e.toString());
                }
            }




    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        //thread.interrupt();
        //thread = null;
        PlayerActivityFragment.playButton.setImageResource(android.R.drawable.ic_media_play);
        mediaPlayer.release();
        //once I have a collection of songs to play, should probably start the next one
        //after finishing...maybe even looping flag to loop the 10 tracks would be good idea...
        //mp.stop();
        //mp.reset();
        //mp.setDataSource([nextElement]);
        //mp.prepare();
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
        PlayerActivityFragment.playButton.setImageResource(android.R.drawable.ic_media_pause);//R.drawable.ic_media_play);

        //seekBar.setMax(30);
        Log.d(this.LOG_TAG, "fs:" + mediaPlayer.getDuration());
        //seekBar.setProgress(15000);
        thread = new Thread(this);
        thread.start();
        //seekBar.setProgress(mediaPlayer.getCurrentPosition());
    }

    @Override
    public void run() {
        int currentPosition= 0;
        String s;
        boolean bPlaying = true;
        int total = 0;
        if (mediaPlayer != null) {
            total = mediaPlayer.getDuration();


        }
        while (mediaPlayer!=null && currentPosition<total && bPlaying==true ) {
            try {
                Thread.sleep(1000);
                currentPosition= mediaPlayer.getCurrentPosition();
                bPlaying = mediaPlayer.isPlaying();

            } catch (Exception e) {

                return;
            }

            seekBar.setProgress(currentPosition);


        }

        Log.d(this.LOG_TAG, "fs:runeneded");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b==true)
        {

            //crashes if not playing
            Log.d(this.LOG_TAG, "fs: " + "progresschangedmanually!" + i );
           //unfortunatly this is always null since it's a different object that was created
            //for the SeekBar listener
            if (mediaPlayer != null){
                //mediaPlayer.stop();
                //mediaPlayer.reset();
                mediaPlayer.seekTo(i);
                //mediaPlayer.start();
                Log.d(this.LOG_TAG, "fs: " + "seekingmedaplayer!" + i );
            }
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(this.LOG_TAG, "fs: " + "starttouch!");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(this.LOG_TAG, "fs: " + "stoptouch!");
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

