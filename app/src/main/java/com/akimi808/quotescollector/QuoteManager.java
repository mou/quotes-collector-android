package com.akimi808.quotescollector;

import com.akimi808.quotescollector.model.Author;
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

    boolean isAuthorStored(String authors);

    void storeAuthor(Author author);

    boolean isQuoteStored(String uuid, String bookmate);

    Author qetAuthor(String authors);

    Source qetSource(String externalId, String application);

    void storeQuote(Quote quote);

    void dataChanged();

    void registerForDataChanged(DataChangedListener listener);

    /**
     * Created by akimi808 on 05/03/2017.
     */

    interface DataChangedListener {
        void onDataChanged();
    }
}
