package com.example.android.pokedexexercise.webservice;

import com.example.android.pokedexexercise.models.details.PokemonDetailResponse;
import com.example.android.pokedexexercise.models.pokemons.PokemonResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {

    /**
     *
     * @param offset will change in 20 for each new consult.
     * @param limit will remains static, in this case 20, get the 20 latest.
     * @return an {@link Observable} of {@link PokemonResponse} object that will emitter
     *          information (onNext() method) about a pokemon's list.
     */
    @GET("pokemon")
    Observable<PokemonResponse> getObservablePokemonList(@Query("offset") int offset, @Query("limit") int limit);

    /**
     *
     * @param number of pokemon to request
     * @return an {@link Observable} of {@link PokemonDetailResponse} object that will emitter
     * information (onNext() method) about details' pokemon.
     */
    @GET("pokemon/{number}")
    Observable<PokemonDetailResponse> getObservablePokemonDetail(@Path("number") int number);
}
