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

public class ColorsActivity extends AppCompatActivity {

    protected MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.red), "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word(getString(R.string.green), "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word(getString(R.string.brown), "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(getString(R.string.gray), "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(getString(R.string.black), "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word(getString(R.string.white), "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word(getString(R.string.dusty_yellow), "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word(getString(R.string.mustard_yellow), "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        final WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_colors);

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

                // Create and start a new media player with the corresponding audio
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this,
                        itemsAdapter.getItem(position).getAudioResource());

                // Checking if getting the audio resource was successful
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();

                    // Call the release method once the audio stopped
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.stop();
                            releaseMediaPlayer();
                        }
                    });
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
    }
}
