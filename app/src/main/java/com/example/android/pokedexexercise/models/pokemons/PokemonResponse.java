package com.example.android.pokedexexercise.models.pokemons;

import com.example.android.pokedexexercise.models.pokemons.Pokemon;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonResponse {

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private String next;
    @SerializedName("results")
    ArrayList<Pokemon> pokemon;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public ArrayList<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(ArrayList<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
