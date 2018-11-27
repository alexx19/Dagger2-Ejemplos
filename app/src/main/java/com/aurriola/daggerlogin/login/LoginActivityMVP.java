package com.aurriola.daggerlogin.login;

/**
 * Created by Alexander Urriola.
 * Estructura de las interfaces modelo, vista, presentador. solo para Login
 */
public interface LoginActivityMVP {

    interface Model{
        void creatUser(String firtName, String lastName);
        User getUser();
    }

    interface View{
        String getFirtName();//obtiene el nombre
        String getLasName();//obtiene el apellido

        void showUserNotAvailable();
        void showInputError();
        void showUserSave();

        void setFirstName(String firtName);
        void setLastName(String lastName);


    }


    interface Presenter{
        void setView(LoginActivityMVP.View view);
        void loginButtonClick();
        void getCurrentUser();

    }
}
