package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 23/12/2016.
 */

public class Source {
    private Integer id;
    private String title;
    private String type;
    private String application;

    public Source(Integer id, String title, String type, String application) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.application = application;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getApplication() {
        return application;
    }
}
