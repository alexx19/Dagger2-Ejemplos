package com.aurriola.daggerlogin.login;

/**
 * Created by Alexander Urriola.
 * obtiene los datos de donde esten almacenados; firebase, realm, etc...
 */
public class LoginActivityModel implements LoginActivityMVP.Model {
    /**
     * Referencia al repositorio
     */
    private LoginRepository repository;

    /**
     * Injecta la dependencia cuando detecte que el present necesita un modelo entonces ira al LoginActivityModel.
     * para luego detectara que el modelo necesitar ser injectado con un repositorio, entonces creara repositorio.
     * @param repository
     */
    public LoginActivityModel(LoginRepository repository)
    {
        this.repository = repository;
    }


    @Override
    public void creatUser(String firtName, String lastName) {
        //TODO:Logica de negocio, se realizan todas las validaciones de datos.
        //no se envia nada al repositorio nada que no sea parte de la logica del negocio
        repository.saveUser(new User(firtName,lastName));
    }

    @Override
    public User getUser() {
        //Validaciones de logica del negocio, antes de retornar el respositorio. Cualquier transformacion del datos.
        return repository.getUser();
    }
}
