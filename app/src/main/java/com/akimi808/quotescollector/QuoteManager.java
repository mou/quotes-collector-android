package com.akimi808.quotescollector;

import com.akimi808.quotescollector.model.Quote;
import com.akimi808.quotescollector.model.Source;

/**
 * Created by akimi808 on 02/12/16.
 */
public interface QuoteManager {
    int getQuoteCount();

    Quote getQuoteByIndex(int index);

    boolean isSourceStored(String externalId, String application);

    void storeSource(Source source);
}
