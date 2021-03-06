package com.aurriola.daggerlogin;

import com.aurriola.daggerlogin.login.LoginActivityMVP;
import com.aurriola.daggerlogin.login.LoginActivityPresenter;
import com.aurriola.daggerlogin.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
//        when(mockedModel.getUser()).thenReturn(user);


//        when(mockedView.getFirtName()).thenReturn("Nombre");
//        when(mockedView.getLasName()).thenReturn("Apellido");

        //Validacion del codigo, si el presenter esta implementado corectamente.
        //TODO:No se utiliza dagger.
        presenter = new LoginActivityPresenter(mockedModel);
        presenter.setView(mockedView);
    }

    @Test
    public void noExisteInterectionWithView(){
        presenter.getCurrentUser();
//        verifyZeroInteractions(mockedView);//si, la vista no cambia. va a fallar por mockedModel.getUser().
        verify(mockedView,times(1)).showUserNotAvailable();//la vista se llama 1 vez.
//        verify(mockedView, never()).showUserNotAvailable();//
    }

    @Test
    public void loadUserFromTheRepoWhenValidUserIsPresent(){
        when(mockedModel.getUser()).thenReturn(user);//cuando llamen a getUser, retorna user.

        presenter.getCurrentUser();
        verify(mockedModel,times(1)).getUser();//se verifica que se llame 1 vez.


        //Comprobamos la interactuacion con la vista.
        verify(mockedView, times(1)).setFirstName("James007");
        verify(mockedView,times(1)).setLastName("Bond007");
        verify(mockedView, never()).showUserNotAvailable();
    }

    @Test
    public void showErrorMessageWhenUserIsNull()
    {
        when(mockedModel.getUser()).thenReturn(null);//retorna null, cuando se llama al metodo getUser().
        presenter.getCurrentUser();//muestra un mensaje que el usuario es  nulo
        verify(mockedModel,times(1)).getUser();//se verifica que se llame 1 vez.


        verify(mockedView, never()).setFirstName("James007");
        verify(mockedView, never()).setLastName("Bond007");
        verify(mockedView, times(1)).showUserNotAvailable();//se llama una vez.
    }

    @Test
    public void createErrorMessageIfAnyFielIsEmpty(){
        //loginbuttonclick, es donde se realiza la persistencia, para guardar el usuario.

        //primera prueba, colocar firstName vacio.
        when(mockedView.getFirtName()).thenReturn("");//condicion del campo vacio.
        presenter.loginButtonClick();

        //validacion de campos vacion, primero el firstName
        verify(mockedView,times(1)).getFirtName();
        verify(mockedView,never()).getLasName();
        verify(mockedView,times(1)).showInputError();

        //validando el campo firstName con valor y el lastName vacio.
        when(mockedView.getFirtName()).thenReturn("James007");
        when(mockedView.getLasName()).thenReturn("");

        presenter.loginButtonClick();

        verify(mockedView,times(2)).getFirtName();//el metodo es llamados 2 veces, en la prueba anterior
        verify(mockedView,times(1)).getLasName();//primera vez que se llama, no paso el test anterior.(test booleano)
        verify(mockedView,times(2)).showInputError();//metodo llamado en la prueba anterior, por lo que se llama 2 veces.


    }


    @Test
    public void saveValidUser()
    {
        //llamadas a view, firstname
        when(mockedView.getFirtName()).thenReturn("James");
        when(mockedView.getLasName()).thenReturn("Bond");

        presenter.loginButtonClick();

        //Llamadas dobles, primero validacion if y luego cuando se guardan. **se realiza al momento de llamar loginButtonClick();
        verify(mockedView,times(2)).getFirtName();
        verify(mockedView,times(2)).getLasName();

        //Validamos la persistencia en el repositorio.
        verify(mockedModel,times(1)).creatUser("James","Bond");//createUser, se valida que sea llamado 1 vez,


        //Validamos que el mensaje de Toas es mostrado al usuario, cuando se guarda con exito el usuario.
        verify(mockedView,times(1)).showUserSave();

    }
}
