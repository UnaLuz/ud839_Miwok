/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private ArrayList<Word> mWords;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioFocusRequestCompat mAudioFocusRequest;

    // Made a global variable so that I don't create a new object every single time I want
    //to release resources
    /**
     * This method is called when the audio finished
     */
    private MediaPlayer.OnCompletionListener mOnCompletionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Set a listener so that when the audio finishes we release resources
        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        };

        // Create an instance of the audio manager
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /* This method will be called when there is a change in the audio focus
         * If we gain audio focus back, resume the audio
         * If we completely loose the audio focus, release resources
         * If we are requested to stop for a short amount of time, pause the audio */
        AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mMediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                        break;
                    default:
                }
            }
        };

        AudioAttributesCompat audioAttributes = new AudioAttributesCompat.Builder()
                .setContentType(AudioAttributesCompat.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributesCompat.USAGE_MEDIA)
                .build();

        //The app needs to play a short audio file, so we will request audio focus
        // with a short amount of time with AUDIOFOCUS_GAIN_TRANSIENT.
        mAudioFocusRequest = new AudioFocusRequestCompat
                .Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build();

        mWords = new ArrayList<>();
        mWords.add(new Word(getString(R.string.red), "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        mWords.add(new Word(getString(R.string.green), "chokokki", R.drawable.color_green, R.raw.color_green));
        mWords.add(new Word(getString(R.string.brown), "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        mWords.add(new Word(getString(R.string.gray), "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        mWords.add(new Word(getString(R.string.black), "kululli", R.drawable.color_black, R.raw.color_black));
        mWords.add(new Word(getString(R.string.white), "kelelli", R.drawable.color_white, R.raw.color_white));
        mWords.add(new Word(getString(R.string.dusty_yellow), "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        mWords.add(new Word(getString(R.string.mustard_yellow), "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // Make the adapter for the word items
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_colors);
        // Find the root view of the list
        ListView listView = findViewById(R.id.root_list_view);
        // Add adapter to the root list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Create the mediaPlayer with the corresponding audio file
             * that will play when the item view is clicked
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player in case it wasn't before creating a new one
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file and store the result
                // of the request
                int result = AudioManagerCompat.requestAudioFocus(
                        mAudioManager,
                        mAudioFocusRequest);


                // Checking we were granted audio focus successfully
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, mWords.get(position).getAudioResource());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }

        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
        // Regardless of whether or not we were granted audio focus, abandon it. This also
        // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
        AudioManagerCompat.abandonAudioFocusRequest(
                mAudioManager,
                mAudioFocusRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // When the activity is stopped, release the media resources cause
        // I don't want to keep playing sounds
        releaseMediaPlayer();
    }
}
