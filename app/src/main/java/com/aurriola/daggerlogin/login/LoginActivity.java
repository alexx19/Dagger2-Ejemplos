package com.aurriola.daggerlogin.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aurriola.daggerlogin.R;
import com.aurriola.daggerlogin.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginActivityMVP.View {
    EditText edtxtFirtName, edtxtLastName;
    Button btnLogin;

    /**
     * Sera gestionada por dagger con @Inject, para injectar el presentador a la vista.
     */
    @Inject
    LoginActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Se injecta la actividad a dagger. Se injecta como parte
         */
        ((App)getApplication()).getComponent().inject(this);

        edtxtFirtName = findViewById(R.id.edtxt_firt_name);
        edtxtLastName = findViewById(R.id.edtxt_firt_lastname);
        btnLogin = findViewById(R.id.btnAccept);
        btnLogin.setOnClickListener(this);


    }

    /**
     * Utilizado para  cada vez que aparesca la vista, sea notificado el presentador.
     */
    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

        //recuperar o notifica al presenter, una sesion activa
        presenter.getCurrentUser();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAccept:
                //Se notifica al presente la accion de click
                presenter.loginButtonClick();
                break;
        }
    }


    /**
     * Se inicia a hacer uso de los metodos, para conectar la vista con el presentador.
     */


    @Override
    public String getFirtName() {
        return this.edtxtFirtName.getText().toString();
    }

    @Override
    public String getLasName() {
        return this.edtxtLastName.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this,"Usuario no disponible",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this,"Error, elementos nulos",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showUserSave() {
        Toast.makeText(this,"Usuario guardado correctamente",Toast.LENGTH_LONG).show();

    }

    @Override
    public void setFirstName(String firstName) {
        this.edtxtFirtName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.edtxtLastName.setText(lastName);
    }
}
