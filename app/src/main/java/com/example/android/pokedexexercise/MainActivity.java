package com.example.android.pokedexexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.pokedexexercise.adapters.OnPokemonClickListener;
import com.example.android.pokedexexercise.di.BaseApplication;
import com.example.android.pokedexexercise.models.pokemons.Pokemon;
import com.example.android.pokedexexercise.models.pokemons.PokemonResponse;
import com.example.android.pokedexexercise.webservice.PokeApiService;
import com.example.android.pokedexexercise.adapters.PokemonListAdapter;
import com.example.android.pokedexexercise.di.RetrofitModule;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements OnPokemonClickListener {

    private static final String TAG = MainActivity.class.getName();
    private static final int SPAN_COUNT = 3;
    private static final int LIMIT = 20;

    // variable will increment in 20, to do new consults
    private int offset = 0;
    // control and allow just one execution when the last item reached.
    private boolean suitableToLoad = true;

    private PokemonListAdapter mAdapter;
    private ArrayList<Pokemon> mPokemon;

    private Disposable mDisposable;

    // Allow us inject WebServiceClient but it is still a NullPointerException.
    @Inject
    PokeApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPokemon = new ArrayList<>();
        mAdapter = new PokemonListAdapter(MainActivity.this, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        // Add a listener to recyclerview for identify an scroll movement
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // scroll is going to below
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    // allow just one execution of data reload
                    if (suitableToLoad) {
                        // last item reached
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            suitableToLoad = false;
                            offset += 20;
                            // new consult to API
                            fetchData(offset);
                        }
                    }
                }
            }
        });

        setUpDagger();
        fetchData(offset);
    }

    /**
     * Set up the 'service' {@link PokeApiService} object to be the instance created
     * in {@link RetrofitModule}
     */
    private void setUpDagger() {
        ((BaseApplication)getApplication()).getRetrofitComponent().inject(this);
    }

    /**
     * Do consult to API REST with Retrofit and get the information through RxJava Observable.
     *
     * @param offset variable to continue consults to API.
     */
    private void fetchData(int offset) {
        service.getObservablePokemonList(offset, LIMIT)
                // execute call in secondary thread.
                .subscribeOn(Schedulers.io())
                // manage the data in the main thread.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PokemonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // bind subscriber and observer.
                        mDisposable = d;
                    }

                    // Triggered for each emitted value
                    @Override
                    public void onNext(PokemonResponse pokemonResponse) {
                        suitableToLoad = true;
                        // set response of pokemon's list into ArrayList 'mPokemon'
                        mPokemon = pokemonResponse.getPokemon();
                        // add new response to ArrayList of adapter
                        mAdapter.addPokemonList(mPokemon);
                    }

                    // Triggered if there is any errors during the event.
                    @Override
                    public void onError(Throwable e) {
                        suitableToLoad = true;
                        Log.e(TAG, "onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // for avoid memory leak problems, do we release the connection.
        mDisposable.dispose();
    }

    /**
     * This method execute when an list item had been clicked.
     *
     * @param number of a pokemon from position clicked.
     */
    @Override
    public void OnPokemonClick(int number) {
        // Set intent to start DetailActivity
        Intent intent = new Intent(this, DetailActivity.class);
        // Add information to intent, in this case an integer value that is pokemon' number of
        // position clicked.
        intent.putExtra("DETAIL_POKEMON_URL", number);
        startActivity(intent);
    }
}