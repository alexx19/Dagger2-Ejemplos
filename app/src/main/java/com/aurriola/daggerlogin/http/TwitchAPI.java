package com.aurriola.daggerlogin.http;

import com.aurriola.daggerlogin.http.twitch.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Alexander Urriola.
 */
public interface TwitchAPI {
    @GET("games/top")//Top de juegos
    //Con Header, se agrega el Client-Id, se firma el ID de cliente, esperado por Twitch.
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);
}
