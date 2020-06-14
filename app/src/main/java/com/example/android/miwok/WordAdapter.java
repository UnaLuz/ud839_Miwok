package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mBgColorResId;

    public WordAdapter(Context context, List<Word> objects, int bgColor) {
        super(context, 0, objects);
        mBgColorResId = bgColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID image_view
        ImageView imageView = listItemView.findViewById(R.id.image_view);
        if(currentWord.getImageResource() != -1){
            // Get the corresponding image resource and set it in the image view of the list
            imageView.setImageResource(currentWord.getImageResource());
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        // Find the TextView in the list_item.xml layout with the ID miwok_word_text_view
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_word_text_view);
        // Get the corresponding miwok word and set it in the text view of the list
        miwokTextView.setText(currentWord.getMiwokWord());

        // Find the TextView in the list_item.xml layout with the ID default_word_text_view
        TextView defaultTextView = listItemView.findViewById(R.id.default_word_text_view);
        // Get the corresponding default translation and set it in the text view of the list
        defaultTextView.setText(currentWord.getDefaultWord());

        // Get the view that contains both text views in the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Set it's background color to mBgColorRes
        textContainer.setBackgroundResource(mBgColorResId);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
