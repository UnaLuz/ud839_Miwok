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

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.father), "әpә", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.mother), "әṭa", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.son), "angsi", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.daughter), "tune", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.older_brother), "taachi", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.younger_brother), "chalitti", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.older_sister), "teṭe", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.younger_sister), "kolliti", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.grandmother), "ama", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.grandfather), "paapa", R.mipmap.ic_launcher));

        WordAdapter itemsAdapter = new WordAdapter(this, words);

        ListView listView = findViewById(R.id.root_list_view);

        listView.setAdapter(itemsAdapter);
    }
}
