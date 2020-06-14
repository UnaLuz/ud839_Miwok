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

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(getString(R.string.where_are_you_going), "minto wuksus"));
        words.add(new Word(getString(R.string.what_is_your_name), "tinnә oyaase'nә"));
        words.add(new Word(getString(R.string.my_name_is), "oyaaset…"));
        words.add(new Word(getString(R.string.how_are_you_feeling), "michәksәs?"));
        words.add(new Word(getString(R.string.im_feeling_good), "kuchi achit"));
        words.add(new Word(getString(R.string.are_you_coming), "әәnәs'aa?"));
        words.add(new Word(getString(R.string.yes_im_coming), "hәә’ әәnәm"));
        words.add(new Word(getString(R.string.im_coming), "әәnәm"));
        words.add(new Word(getString(R.string.lets_go), "yoowutis"));
        words.add(new Word(getString(R.string.come_here), "әnni'nem"));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = findViewById(R.id.root_list_view);

        listView.setAdapter(itemsAdapter);
    }
}
