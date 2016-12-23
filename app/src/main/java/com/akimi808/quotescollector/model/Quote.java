package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 02/12/16.
 */

public class Quote {
    private final String text;
    private final Author author;
    private final Source source;

    public Quote(String text, Author author, Source source) {
        this.text = text;
        this.author = author;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public Author getAuthor() {
        return author;
    }

    public Source getSource() {
        return source;
    }
}
