package com.aurriola.daggerlogin.login;

import android.support.annotation.NonNull;

/**
 * Created by Alexander Urriola.
 */
public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    /**
     * Referencia a la vista.
     */
    @NonNull//Valida si una variable es nula.
    private LoginActivityMVP.View view;

    /**
     * Referencia al modelo
     */
    private LoginActivityMVP.Model model;

    /**
     * Contructor
     * @param model
     */
    public LoginActivityPresenter(LoginActivityMVP.Model model)
    {
        this.model = model;
    }

    /**
     * Llamado de la vista al presentador.
     * @param view vista
     */
    @Override
    public void setView(LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClick() {
        //Se comprueba cada vez que el usuario presiona el boton.
        if (view!=null)
        {
            if (view.getFirtName().trim().equals("")||view.getLasName().trim().equals(""))
            {
                view.showInputError();
            }
            else {
                model.creatUser(view.getFirtName().trim(), view.getLasName().trim());
                view.showUserSave();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        User user = model.getUser();
        if (user==null)
        {
            if (view!=null)//para poder mostrar dialog en la vista
            {
                view.showUserNotAvailable();
            }
        }else {
            if (view!=null)
            {
                view.setFirstName(user.getFirstName());
                view.setLastName(user.getLastName());
            }
        }
    }
}
