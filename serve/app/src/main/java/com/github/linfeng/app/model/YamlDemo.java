package com.github.linfeng.app.model;

public class YamlDemo {

    private String username;

    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.print("username:");
        System.out.println(username);
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.print("name:");
        System.out.println(name);
        this.name = name;
    }
}
