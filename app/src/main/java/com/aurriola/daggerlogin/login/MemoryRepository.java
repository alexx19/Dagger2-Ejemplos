package com.aurriola.daggerlogin.login;

/**
 * Created by Alexander Urriola.
 * Uso en memoria. Se escribe en bd.
 */
public class MemoryRepository implements LoginRepository {

    /**
     * CAPA DE PERSISTENCIA.
     */
    private User user;

    @Override
    public void saveUser(User user) {
        if (user==null)
        {
            user = getUser();
        }
        this.user = user;
    }

    @Override
    public User getUser() {
        if (user==null)
        {
            user = new User("James", "Bond");
            user.setId(0);
            return user;
        }else {
            return user;
        }
    }
}
