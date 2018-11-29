package io.ayou.lake.d.domain;

import java.time.Instant;

public class User {
    private String id;
    private String name;
    private int age;
    private Instant btd;

    public User() {
    }

    public User(String id, String name, int age, Instant btd) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.btd = btd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", btd=" + btd +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Instant getBtd() {
        return btd;
    }

    public void setBtd(Instant btd) {
        this.btd = btd;
    }
}
