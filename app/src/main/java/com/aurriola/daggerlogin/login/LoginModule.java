package com.aurriola.daggerlogin.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Urriola.
 * Se conecta con dagger, @Module nos dara los metodo necesarios para que funcione MVP.
 */
@Module
public class LoginModule  {
    //Se crean metodo provide(configurar o retornar)

    @Provides
    public LoginActivityMVP.Presenter provideLoginActivityPresenter(LoginActivityMVP.Model model)
    {
        return new LoginActivityPresenter(model);
    }

    @Provides
    public LoginActivityMVP.Model provideLoginActiviModel(LoginRepository repository)
    {
        return new LoginActivityModel(repository);
    }


    @Provides
    public LoginRepository provideLoginRepository(){
        return new MemoryRepository(); //Se cambiara si se desea tener otro tipo de almacenamiento, bd, realm, firebase, etc...
    }
}
