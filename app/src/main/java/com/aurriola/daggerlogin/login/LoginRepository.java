package com.aurriola.daggerlogin.login;

/**
 * Created by Alexander Urriola.
 * se podra utilizar con bd. facilitara la implementacion con diferentes bd.
 */
public interface LoginRepository {
    void saveUser(User user);
    User getUser();
}
