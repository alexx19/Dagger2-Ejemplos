package com.aurriola.daggerlogin.root;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Urriola.
 * Utilizada por dagger para llevar un control de cada funcionalidad(login), con la anotacion @Module.
 */
@Module
public class ApplicationModule {
    private Application application;

    /**
     * Contructor
     * @param application
     */
    public ApplicationModule(Application application)
    {
        this.application = application;
    }

    @Provides
    @Singleton//se crea una instancia dentro de la app
    public Context provideContext(){
        return application;
    }

}
