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

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    /**
     * Handles playback for all the audio files
     */
    protected MediaPlayer mMediaPlayer;

    /**
     * This method is called when the audio finished
     */
    // Made a global variable so that I don't create a new object every single time I want
    //to release resources
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.stop();
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.where_are_you_going), "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word(getString(R.string.what_is_your_name), "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word(getString(R.string.my_name_is), "oyaaset…", R.raw.phrase_my_name_is));
        words.add(new Word(getString(R.string.how_are_you_feeling), "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word(getString(R.string.im_feeling_good), "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word(getString(R.string.are_you_coming), "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word(getString(R.string.yes_im_coming), "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word(getString(R.string.im_coming), "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word(getString(R.string.lets_go), "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word(getString(R.string.come_here), "әnni'nem", R.raw.phrase_come_here));

        final WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = findViewById(R.id.root_list_view);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Create the mediaPlayer with the corresponding audio file
             * that will play when the item view is clicked
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player in case it wasn't before creating a new one
                releaseMediaPlayer();

                Word item = itemsAdapter.getItem(position);
                if (item != null) {
                    // Create and start a new media player with the corresponding audio
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, item.getAudioResource());
                }

                // Checking if getting the audio resource was successful
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();

                    // Call the global method to release the resources
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

    /** Clean up the media player by releasing its resources. */
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
