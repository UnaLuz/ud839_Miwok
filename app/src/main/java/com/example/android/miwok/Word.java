package com.example.android.miwok;

/**
 * Stores a miwok word and it's default translation
 */
public class Word {
    private int NO_IMAGE = -1;
    private String mDefaultWord;
    private String mMiwokWord;
    private int mImageResourceId = NO_IMAGE;
    private int mAudioResourceId;

    /**
     * Receives the default translation and the miwok word
     */
    public Word(String defWord, String miwWord, int audioResId) {
        mDefaultWord = defWord;
        mMiwokWord = miwWord;
        mAudioResourceId = audioResId;
    }

    /**
     * Receives the default translation and the miwok word
     */
    public Word(String defWord, String miwWord, int imgResId, int audioResId) {
        mDefaultWord = defWord;
        mMiwokWord = miwWord;
        mImageResourceId = imgResId;
        mAudioResourceId = audioResId;
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

    /**
     * @return the audio resource of the miwok word
     */
    public int getAudioResource() {
        return mAudioResourceId;
    }

}
