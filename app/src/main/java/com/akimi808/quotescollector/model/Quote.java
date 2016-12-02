package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 02/12/16.
 */

public class Quote {
    private final String text;
    private final String author;
    private final String source;

    public Quote(String text, String author, String source) {
        this.text = text;
        this.author = author;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }
}
