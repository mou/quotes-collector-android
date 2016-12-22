package com.akimi808.bookmateclient.model;

/**
 * @author Andrey Larionov
 */
public class User {
    private Integer id;
    private String login;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
