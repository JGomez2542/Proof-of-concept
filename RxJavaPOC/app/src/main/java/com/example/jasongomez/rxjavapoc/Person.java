package com.example.jasongomez.rxjavapoc;

import java.util.List;

public class Person {

    private List<String> friends;
    private String name;


    public Person(List<String> friends, String name) {
        this.friends = friends;
        this.name = name;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
