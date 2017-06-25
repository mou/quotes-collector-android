package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 23/12/2016.
 */

public class Author {
    private Long id;
    private String name;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Author withId(long authorId) {
        return new Author(authorId, name);
    }
}
