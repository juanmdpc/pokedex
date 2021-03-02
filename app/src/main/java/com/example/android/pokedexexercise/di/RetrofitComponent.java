package com.example.android.pokedexexercise.di;

import com.example.android.pokedexexercise.DetailActivity;
import com.example.android.pokedexexercise.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Bridge between {@link RetrofitModule} (module) and classes (i.e. MainActivity, DetailActivity)
 * that requesting to dependency injection (objects).
 */
@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    /**
     *
     * @param mainActivity (class) to do injected.
     */
    void inject(MainActivity mainActivity);

    /**
     *
     * @param detailActivity (class) to do injected.
     */
    void inject(DetailActivity detailActivity);
}
