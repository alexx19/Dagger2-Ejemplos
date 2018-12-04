package com.aurriola.daggerlogin.http;

import com.aurriola.daggerlogin.http.twitch.Twitch;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexander Urriola.
 */
@Module//Agregar este  modulo al componente "ApplicationComponent"
public class TwitchModule {
    public final String BASE_URL = "https://api.twitch.tv/helix/";

    @Provides//Utilizado para injectar en dagger.
    public OkHttpClient provideHttpClient()
    {
        //Creando un objeto para logger los resultados de la consulta.
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //Registra todas las solicitudes.
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//recupera de la llamada el objeto json.
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    /**
     * creacion del objeto retrofit
     * @param base_URL url base, de cualquier API a utilizar
     * @param client cliente de conexion.
     * @return objeto retrofit
     */
    @Provides
    public Retrofit provideRetrofit(String base_URL, OkHttpClient client)
    {
        //Definicion de conexion a la url, conversion a objetos java.
        //sera la intancia
        return new Retrofit.Builder()
                .baseUrl(base_URL)
                .client(client)
                //responsable de parcear el objeto de respuesta
                .addConverterFactory(GsonConverterFactory.create())//convertidor de objetos json a clase java, del modulo.
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Llamadas  a objetos rxjava.
                .build();
    }

    @Provides
    public TwitchAPI provideTwitchService(){
        //Utilizado para crear un conjunto de llamadas a las API establecidas en la interfas de TwitchAPI.
        return provideRetrofit(BASE_URL, provideHttpClient()).create(TwitchAPI.class);
    }
}
