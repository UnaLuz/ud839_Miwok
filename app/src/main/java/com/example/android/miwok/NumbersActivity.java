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

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(getString(R.string.one), "lutti", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.two), "otiiko", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.three), "tolookosu", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.four), "oyyisa", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.five), "massokka", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.six), "temmokka", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.seven), "kenekaku", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.eight), "kawinta", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.nine), "wo'e", R.mipmap.ic_launcher));
        words.add(new Word(getString(R.string.ten), "na'aacha", R.mipmap.ic_launcher));

        WordAdapter itemsAdapter = new WordAdapter(this, words);

        ListView listView = findViewById(R.id.root_numbers_list_view);

        listView.setAdapter(itemsAdapter);
    }

}
