package com.example.android.pokedexexercise.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.pokedexexercise.R;
import com.example.android.pokedexexercise.di.GlideApp;
import com.example.android.pokedexexercise.models.pokemons.Pokemon;

import java.util.ArrayList;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {
    private static final String SPRITE_URL
            = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    private ArrayList<Pokemon> mPokemon;
    private Context mContext;
    private OnPokemonClickListener mOnPokemonClickListener;

    /**
     * Initialize the dataset of the Adapter
     * @param context reference to current activity
     */
    public PokemonListAdapter(Context context, OnPokemonClickListener onPokemonClickListener) {
        mPokemon = new ArrayList<>();
        mContext = context;
        mOnPokemonClickListener = onPokemonClickListener;
    }

    /**
     * Provide a reference to the type of views that we are using.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView pokemonCardView;
        private ImageView pokemonImageView;
        private TextView pokemonNameTextView;

        OnPokemonClickListener onPokemonClickListener;

        public ViewHolder(View itemView, OnPokemonClickListener onPokemonClickListener) {
            super(itemView);

            pokemonImageView = itemView.findViewById(R.id.pokemon_image_view);
            pokemonNameTextView = itemView.findViewById(R.id.pokemon_text_view);
            pokemonCardView = itemView.findViewById(R.id.card_view);
            this.onPokemonClickListener = onPokemonClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.onPokemonClickListener.OnPokemonClick(mPokemon.get(getAdapterPosition()).getNumber());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view, mOnPokemonClickListener);
    }

    // Replace the contents of a View (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = mPokemon.get(position);
        holder.pokemonNameTextView.setText(pokemon.getName());

        // Load image from WebServiceClient
        GlideApp.with(mContext)
                .load(SPRITE_URL + pokemon.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImageView);
    }

    // Return the size of our dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPokemon.size();
    }

    /**
     * Adding new information without replace the old. Likewise update the recyclerview on screen.
     * @param pokemon data source of items to populate to recyclerview
     */
    public void addPokemonList(ArrayList<Pokemon> pokemon) {
        mPokemon.addAll(pokemon);
        notifyDataSetChanged();
    }
}
