package com.example.android.pokedexexercise;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.pokedexexercise.di.BaseApplication;
import com.example.android.pokedexexercise.di.GlideApp;
import com.example.android.pokedexexercise.models.details.PokemonDetailResponse;
import com.example.android.pokedexexercise.webservice.PokeApiService;
import com.example.android.pokedexexercise.di.RetrofitModule;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getName();
    private static final String SPRITE_URL
            = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    private int detailPokemonNumber;

    private Disposable disposable;

    // Allow us inject WebServiceClient but it is still a NullPointerException
    @Inject
    PokeApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get information sent by early activity. In this case is pokemon's number.
        detailPokemonNumber = getIntent().getIntExtra("DETAIL_POKEMON_URL", -1);

        setUpDagger();
        fetchData();
    }

    /**
     * Set up the 'service' {@link PokeApiService} object to be the instance created
     * in {@link RetrofitModule}.
     */
    private void setUpDagger() {
        ((BaseApplication)getApplication()).getRetrofitComponent().inject(this);
    }

    /**
     * Do consults to API REST with Retrofit and get the data through Observable RxJava.
     */
    private void fetchData() {
        service.getObservablePokemonDetail(detailPokemonNumber)
                // execute call in secondary thread.
                .subscribeOn(Schedulers.io())
                // manage the data in main thread.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PokemonDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // bind subscriber and observer.
                        disposable = d;
                    }

                    // Triggered for each emitter value.
                    @Override
                    public void onNext(PokemonDetailResponse pokemonDetailResponse) {
                        // Get information about specific pokemon.
                        String name = pokemonDetailResponse.getName();
                        float height = pokemonDetailResponse.getHeight();
                        float weight = pokemonDetailResponse.getWeight();
                        String abilities = pokemonDetailResponse.travelAbilities();
                        String moves = pokemonDetailResponse.travelMoves();

                        // Set Activity's label
                        setTitle(name);

                        // Update information into the views.
                        setUpViews(name, height, weight, abilities, moves);
                    }

                    // Triggered when there is any error during the event.
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // for avoid memory leak, do we release the connection.
        disposable.dispose();
    }

    /**
     * Update information about pokemon in each view from item list.
     *
     * @param name the pokemon
     * @param height the pokemon
     * @param weight the pokemon
     * @param abilities the pokemon
     * @param moves the pokemon
     */
    private void setUpViews(String name, float height, float weight, String abilities, String moves) {
        ImageView imageView = findViewById(R.id.pokemon_image_view);

        // Load image from WebServiceClient
        GlideApp.with(DetailActivity.this)
                .load(SPRITE_URL + detailPokemonNumber + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        TextView nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(name);

        TextView heightTextView = findViewById(R.id.height_text_view);
        heightTextView.setText(String.valueOf(height));

        TextView weightTextView = findViewById(R.id.weight_text_view);
        weightTextView.setText(String.valueOf(weight));

        TextView abilitiesTextView = findViewById(R.id.abilities_text_view);
        abilitiesTextView.setText(abilities);

        TextView movesTextView = findViewById(R.id.moves_text_view);
        movesTextView.setText(moves);
    }
}
