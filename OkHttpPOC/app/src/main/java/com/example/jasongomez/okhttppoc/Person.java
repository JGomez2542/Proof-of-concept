package com.example.jasongomez.okhttppoc;

public class Person {

    private String name;
    private int age;
    private String favoriteAnimal;

    public Person(String name, int age, String favoriteAnimal) {
        this.name = name;
        this.age = age;
        this.favoriteAnimal = favoriteAnimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavoriteAnimal() {
        return favoriteAnimal;
    }

    public void setFavoriteAnimal(String favoriteAnimal) {
        this.favoriteAnimal = favoriteAnimal;
    }
}
