package com.discord.soundboard.database.model;
import org.springframework.data.annotation.Id;

public class User {

    @Id
    public String id;

    public String name;
    public String pwd;
    public String token;

    public User(String name, String pwd, String token) {
        this.name = name;
        this.pwd = pwd;
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, name='%s', pwd='%s', token='%s']",
                id, name, pwd, token);
    }

}
