package com.example.bsiproject;

public class Provider {
    public String age;
    public String phoneNumber;
    public String name;
    public String id;
    public Float rating;

    public Provider() {
    }
    public Provider(String age, String phoneNumber, String name, String id, Float rating) {
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.id = id;
        this.rating = rating;
    }
    public Provider(String age, String phoneNumber, String name, String id) {
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.id = id;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
