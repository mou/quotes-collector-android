package com.akimi808.bookmateclient.model;

/**
 * @author Andrey Larionov
 */
public class Book {
    private String uuid;
    private String title;
    private String annotation;
    private String authors;

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", annotation='" + annotation + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }
}
