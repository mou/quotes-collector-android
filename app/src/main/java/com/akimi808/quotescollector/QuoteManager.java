package com.akimi808.quotescollector;

import com.akimi808.quotescollector.model.Quote;

/**
 * Created by akimi808 on 02/12/16.
 */
public interface QuoteManager {
    int getQuoteCount();

    Quote getQuoteByIndex(int index);
}
