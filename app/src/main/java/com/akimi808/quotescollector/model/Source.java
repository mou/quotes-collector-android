package com.akimi808.quotescollector.model;

/**
 * Created by akimi808 on 23/12/2016.
 */

public class Source {
    private Long id;
    private String title;
    private String type;
    private String application;
    private String externalId;

    public Source(Long id, String title, String type, String application, String externalId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.application = application;
        this.externalId = externalId;
    }

    public Long getId() {
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

    public String getExternalId() {
        return externalId;
    }
}
