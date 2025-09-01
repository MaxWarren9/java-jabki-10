package org.example.login;

import org.example.customException.LoginFailedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login {
    private String username;
    private String password;
    private final Map<String, String> users = new HashMap<>();

    public void registerUser(String username, String password) {
        users.put(username, password);
    }
    public void login(String username, String password) throws LoginFailedException {

        if (!users.containsKey(username)) {
            throw new LoginFailedException("Данного пользователя не существует");
        }

        if (!password.equals(users.get(username))) {
            throw new LoginFailedException("Пароль для пользователя введен неверно");
        }

        this.username = username;
        this.password = password;
        System.out.println("Вы вошли");
    }

    public Map<String, String> getUsers() {
        return users;
    }
}
