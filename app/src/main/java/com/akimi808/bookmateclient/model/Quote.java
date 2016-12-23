package com.akimi808.bookmateclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andrey Larionov
 */
public class Quote {
    private String uuid;
    private User user;
    @SerializedName("document_uuid")
    private String documentUuid;
    private String content;
    private String comment;

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public String getDocumentUuid() {
        return documentUuid;
    }

    public String getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "uuid='" + uuid + '\'' +
                ", user=" + user +
                ", documentUuid='" + documentUuid + '\'' +
                ", content='" + content + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
