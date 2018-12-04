package com.aurriola.daggerlogin.root;

import com.aurriola.daggerlogin.http.TwitchModule;
import com.aurriola.daggerlogin.login.LoginActivity;
import com.aurriola.daggerlogin.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alexander Urriola.
 * Componentes(LoginActivity) que dependeran del modulo.
 */
@Singleton
@Component(modules ={ ApplicationModule.class, LoginModule.class, TwitchModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity target);

}
