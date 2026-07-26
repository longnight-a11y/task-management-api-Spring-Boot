package com.example.taskapi.fixture;

import com.example.taskapi.Entity.User;

import java.util.UUID;

public final class UserFixture {

    private UserFixture(){}  // ここのmethodsはどれもstaticなので、インスタンス化しないでねの意味

    public static User user(){
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Mikasa");
        user.setPassword("testpass");
        return user;
    }

    public static User user(String username){
        User user = user();
        user.setUsername(username);
        return user;
    }
}
