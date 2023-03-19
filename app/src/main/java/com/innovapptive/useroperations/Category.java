package com.innovapptive.useroperations;

public class Category {
    String name;
    String id;
    String url;
    public Category(String name, String id, String url) {
        this.name = name;
        this.id=id;
        this.url=url;
    }
    public Category(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
