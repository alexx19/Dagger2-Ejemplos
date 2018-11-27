package com.aurriola.daggerlogin;

import com.aurriola.daggerlogin.login.LoginActivityMVP;
import com.aurriola.daggerlogin.login.LoginActivityPresenter;
import com.aurriola.daggerlogin.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Alexander Urriola.
 */
public class PresenterUnitTest {

    LoginActivityPresenter presenter;
    User user;

    //Se utiliza las interfas, solo sera utilizada para simular que existenten.
    LoginActivityMVP.View mockedView;
    LoginActivityMVP.Model mockedModel;



    //Configurar metodo, indica que cada vez que se ejecute una prueba, se iniciara este metodo con la anotacion @Before
    //proceso de inicializacion.
    @Before
    public void init()
    {
        mockedModel = mock(LoginActivityMVP.Model.class);//configura el objeto modelo, con el metodo mock.
        mockedView = mock(LoginActivityMVP.View.class);


        //DATA para pruebas.
        user = new User("James007","Bond007");

        //Cuando se llama  al metodo getUser, entonces se retorna el usuario(DATA)
        when(mockedModel.getUser()).thenReturn(user);


        when(mockedView.getFirtName()).thenReturn("Nombre");
        when(mockedView.getLasName()).thenReturn("Apellido");

        //Validacion del codigo, si el presenter esta implementado corectamente.
        //TODO:No se utiliza dagger.
        presenter = new LoginActivityPresenter(mockedModel);
        presenter.setView(mockedView);
    }

    @Test
    public void noExisteInterectionWithView(){
        presenter.getCurrentUser();
        verifyZeroInteractions(mockedView);//si, la vista no cambia. va a fallar por mockedModel.getUser().
    }

}
