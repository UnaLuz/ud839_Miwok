package com.example.android.miwok;

/**
 * Stores a miwok word and it's default translation
 */
public class Word {
    private int NO_IMAGE = -1;
    private String mDefaultWord;
    private String mMiwokWord;
    private int mImageResourceId = NO_IMAGE;

    /**
     * Receives the default translation and the miwok word
     */
    public Word(String defWord, String miwWord) {
        mDefaultWord = defWord;
        mMiwokWord = miwWord;
    }

    /**
     * Receives the default translation and the miwok word
     */
    public Word(String defWord, String miwWord, int resId) {
        mDefaultWord = defWord;
        mMiwokWord = miwWord;
        mImageResourceId = resId;
    }

    /**
     * @return the default translation of the miwok word
     */
    public String getDefaultWord() {
        return mDefaultWord;
    }

    /**
     * @return the miwok word
     */
    public String getMiwokWord() {
        return mMiwokWord;
    }

    /**
     * @return the image resource of the miwok word
     */
    public int getImageResource() {
        return mImageResourceId;
    }

}
