package com.froyith.spotifystreamer;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SpotifyStreamer extends IntentService implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_PLAY = "com.froyith.spotifystreamer.action.PLAY";
    public static final String ACTION_PAUSE = "com.froyith.spotifystreamer.action.PAUSE";
    private MediaPlayer mediaPlayer = null;
    private final String LOG_TAG = SpotifyStreamer.class.getSimpleName();


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.LOG_TAG, "fs: " + "stop killing me!");

    }

    public SpotifyStreamer() {
        super("SpotifyStreamer");

    }

    @Override
    public void onCreate() {
        super.onCreate();

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                final String strURL = (String) intent.getExtras().get(Intent.EXTRA_TEXT);
                handleActionPlay(strURL);
            } else if (ACTION_PAUSE.equals(action)) {

                handleActionPause();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPlay(String url) {
        //from developer.android.com - mediaplayer
        Log.d(this.LOG_TAG, "playing: " + url);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }else{
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            } else {

                try {

                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                    //mediaPlayer.start();
                } catch (Exception e) {
                    Log.d(this.LOG_TAG, "err: " + e.toString());
                }
            }

        }
    }
    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPause() {
            //
            mediaPlayer.stop();
        mediaPlayer.release();


    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(this.LOG_TAG, "fs: " + mediaPlayer.isPlaying()+ mediaPlayer.hashCode());
       mediaPlayer.start();
    }
}
