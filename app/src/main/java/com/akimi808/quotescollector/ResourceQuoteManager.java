package com.akimi808.quotescollector;

import android.content.res.Resources;

/**
 * Created by akimi808 on 02/12/16.
 */
public class ResourceQuoteManager implements QuoteManager {
    //класс в Android для доступа к ресурсам приложения
    private Resources resources;
    @Override
    public int getQuoteCount() {
        String[] quoteTexts = resources.getStringArray(R.array.quote_texts);
        return quoteTexts.length;
    }
}