package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 23/12/2016.
 */

public class Author {
    private Integer id;
    private String name;

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
