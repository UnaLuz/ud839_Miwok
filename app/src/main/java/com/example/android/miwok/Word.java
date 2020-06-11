package com.example.android.miwok;

/**
 * Stores a miwok word and it's default translation
 */
public class Word{
    protected String defaultWord;
    protected String miwokWord;

    /**
     * Receives the default translation and the miwok word
     */
    public Word(String defWord, String miwWord){
        defaultWord = defWord;
        miwokWord = miwWord;
    }

    /**
     *
     * @return the default translation of the miwok word
     */
     public String getDefaultWord(){
        return defaultWord;
     }

    /**
     *
     * @return the miwok word
     */
    public String getMiwokWord(){
        return miwokWord;
    }

}
