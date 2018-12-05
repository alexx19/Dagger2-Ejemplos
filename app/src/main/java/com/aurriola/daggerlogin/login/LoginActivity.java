package com.aurriola.daggerlogin.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aurriola.daggerlogin.R;
import com.aurriola.daggerlogin.http.TwitchAPI;
import com.aurriola.daggerlogin.http.twitch.Game;
import com.aurriola.daggerlogin.http.twitch.Twitch;
import com.aurriola.daggerlogin.root.App;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {


    //Listados de editext
    @BindViews({R.id.edtxt_firt_name,R.id.edtxt_firt_lastname})
    List<EditText> inputs;

    @BindView(R.id.edtxt_firt_name)
    EditText edtxtFirtName;
    @BindView(R.id.edtxt_firt_lastname)
    EditText edtxtLastName;
    @BindView(R.id.btnAccept)
    Button btnLogin;

    @BindString(R.string.main_activity_main)
    String title;
    
    @BindDrawable(R.drawable.ic_launcher_background)
    Drawable drawable_background;
    
    @BindColor(R.color.colorPrimary)
    ColorStateList colorAccent;
    //int colorAccent;

    @BindDimen(R.dimen.spacing)
    float spacing;

    @Inject
    TwitchAPI twitchAPI;

    //Uso de Disable y Enabled con la libreria ButterKnife
    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };
    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };


    /**
     * Sera gestionada por dagger con @Inject, para injectar el presentador a la vista.
     */
    @Inject
    LoginActivityMVP.Presenter presenter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.setTitle(title);

        //Con el listado de editext 'inputs', se puede cambiar el color de fondo, ocultar los elementos en la vista.
        ButterKnife.apply(inputs,DISABLE);
        ButterKnife.apply(inputs,ENABLED, true);
        ButterKnife.apply(inputs,View.ALPHA, 0.1f);//casi invisible.
        /**
         * Se injecta la actividad a dagger. Se injecta como parte
         */
        ((App)getApplication()).getComponent().inject(this);

//        btnLogin.setOnClickListener(this);

        //Se establece un ejemplo para utilizar la API de twitch con retrofit
        //Call<Twitch> call = twitchAPI.getTopGames("......");//ID de cliente generado con la api de Twitch.
        //enqueue: encolar o esperar hasta que llegue la respuesta
        //bloques async, se encolan las respuestas, en un hilo en segundo plano.
       /* call.enqueue(new Callback<Twitch>() {
            //respuesta.
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> topGame = response.body().getGame();
                for (Game game: topGame)
                {
                    Log.d("TOP GAME", game.getName());
                }
            }

            //
            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
       //Con flatMap(), ingresara un objeto de tipo Twitch y lo transformara a un objeto de tipo esperado(resultado), por ejemplo
        //el objeto Game, solo sera pasado de un  objeto Twitch a Game.
        //link:http://reactivex.io/documentation/operators/flatmap.html
        //se realiza la lista de juegos a partir del objeto Twitch.
       twitchAPI.getTopGameObservable("").flatMap(new Function<Twitch, Observable<Game>>() {
           @Override
           public Observable<Game> apply(Twitch twitch) {
               return Observable.fromIterable(twitch.getGame());
           }
       }).flatMap(new Function<Game, Observable<String>>() {
           @Override
           public Observable<String> apply(Game game)  {
               return Observable.just(game.getName());
           }
       }).filter(new Predicate<String>() {

           @Override
           public boolean test(String s)  {
               return s.contains("W")||s.contains("w");
           }
       }).subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(String s) {
               Log.d("onNext",s);
           }

           @Override
           public void onError(Throwable e) {

           }

           @Override
           public void onComplete() {

           }
       });//Hilo de ejecucion de entrada y salida de datos de rxjava, cuando se utilizar ws
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

    /*@Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAccept:
                //Se notifica al presente la accion de click
                presenter.loginButtonClick();
                break;
        }
    }*/


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

    @OnClick(R.id.btnAccept)//uso del click con butterknife
    public void loginClick(Button btnClick)
    {
        btnClick.setText("Enviado!!");
        presenter.loginButtonClick();
    }
}
