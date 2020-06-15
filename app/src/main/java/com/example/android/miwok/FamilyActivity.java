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

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.father), "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word(getString(R.string.mother), "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(getString(R.string.son), "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word(getString(R.string.daughter), "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(getString(R.string.older_brother), "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word(getString(R.string.younger_brother), "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(getString(R.string.older_sister), "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(getString(R.string.younger_sister), "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(getString(R.string.grandmother), "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(getString(R.string.grandfather), "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        final WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = findViewById(R.id.root_list_view);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Create the mediaPlayer with the corresponding audio file
             * that will play when the item view is clicked
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaPlayer mediaPlayer = MediaPlayer.create(FamilyActivity.this,
                        itemsAdapter.getItem(position).getAudioResource());
                mediaPlayer.start();
            }
        });
    }
}
