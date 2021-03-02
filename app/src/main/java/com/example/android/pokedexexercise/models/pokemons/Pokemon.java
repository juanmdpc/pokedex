package com.example.android.pokedexexercise.models.pokemons;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Pokemon {

    private int number;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] urlSplit = url.split("/");

        return Integer.parseInt(urlSplit[urlSplit.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
