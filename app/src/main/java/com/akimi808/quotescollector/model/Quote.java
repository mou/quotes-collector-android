package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 02/12/16.
 */

public class Quote {
    private final Long id; //уникальный идентификатор в нашем приложении
    private final String text;
    private final String externalId; //уникальный идентификатор в стороннем сервисе
    private final Author author;
    private final Source source;
    private final String application;


    public Quote(Long id, String text, String externalId, Author author, Source source, String application) {
        this.id = id;
        this.text = text;
        this.externalId = externalId;
        this.author = author;
        this.source = source;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getExternalId() {
        return externalId;
    }

    public Author getAuthor() {
        return author;
    }

    public Source getSource() {
        return source;
    }

    public String getApplication() {
        return application;
    }

    @Override
    public String toString() {
        return "BookmateQuote{" +
                "text='" + text + '\'' +
                ", author=" + author +
                ", source=" + source +
                '}';
    }
}
