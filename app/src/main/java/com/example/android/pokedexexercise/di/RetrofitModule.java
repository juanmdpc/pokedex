package com.example.android.pokedexexercise.di;

import com.bumptech.glide.Glide;
import com.example.android.pokedexexercise.webservice.PokeApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class will provide all the necessaries instances for that
 * our classes can work (i.e. MainActivity).
 */
@Module
public class RetrofitModule {

    // Base URL to make request to API REST
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    // URL that show sprite default about pokemon
    private static final String SPRITE_URL
            = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    /**
     * @return just an instance of {@link GsonConverterFactory} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    /**
     * @return just an instance of {@link HttpLoggingInterceptor} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * @param httpLoggingInterceptor allows to logs request and response information.
     * @return just an instance of {@link OkHttpClient.Builder} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    /**
     * @return just an instance of {@link RxJava3CallAdapterFactory} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    RxJava3CallAdapterFactory provideRxJava3CallAdapterFactory() {
        return RxJava3CallAdapterFactory.create();
    }

    /**
     * @param gsonConverterFactory      used for conversion from gson to json.
     * @param okHttpBuilder             used to read the HTTP requests.
     * @param rxJava3CallAdapterFactory will allow to create and return observables.
     * @return just an instance of {@link Retrofit} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                             OkHttpClient okHttpBuilder,
                             RxJava3CallAdapterFactory rxJava3CallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava3CallAdapterFactory)
                .client(okHttpBuilder)
                .build();
    }

    /**
     * @param retrofit to HTTP calls.
     * @return just an instance of {@link PokeApiService} object (singleton), and
     * provide this for dependency inject (provides).
     */
    @Singleton
    @Provides
    PokeApiService providePokeApiService(Retrofit retrofit) {
        return retrofit.create(PokeApiService.class);
    }
}
