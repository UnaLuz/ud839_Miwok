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

public class FamilyActivity extends AppCompatActivity {

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
        mWords.add(new Word(getString(R.string.father), "әpә", R.drawable.family_father, R.raw.family_father));
        mWords.add(new Word(getString(R.string.mother), "әṭa", R.drawable.family_mother, R.raw.family_mother));
        mWords.add(new Word(getString(R.string.son), "angsi", R.drawable.family_son, R.raw.family_son));
        mWords.add(new Word(getString(R.string.daughter), "tune", R.drawable.family_daughter, R.raw.family_daughter));
        mWords.add(new Word(getString(R.string.older_brother), "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        mWords.add(new Word(getString(R.string.younger_brother), "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        mWords.add(new Word(getString(R.string.older_sister), "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        mWords.add(new Word(getString(R.string.younger_sister), "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        mWords.add(new Word(getString(R.string.grandmother), "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        mWords.add(new Word(getString(R.string.grandfather), "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        // Make the adapter for the word items
        WordAdapter adapter = new WordAdapter(this, mWords, R.color.category_family);
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
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, mWords.get(position).getAudioResource());

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
