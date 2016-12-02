package com.akimi808.quotescollector;

import android.content.res.Resources;

import com.akimi808.quotescollector.model.Quote;

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

    @Override
    public Quote getQuoteByIndex(int index) {
        String text = resources.getStringArray(R.array.quote_texts)[index];
        String author = resources.getStringArray(R.array.quote_authors)[index];
        String source = resources.getStringArray(R.array.quote_sources)[index];
        return new Quote(text, author, source);
    }
}
