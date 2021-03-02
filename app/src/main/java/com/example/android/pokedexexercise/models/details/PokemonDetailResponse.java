package com.example.android.pokedexexercise.models.details;

import android.util.Log;

import com.example.android.pokedexexercise.models.abilities.Abilities;
import com.example.android.pokedexexercise.models.moves.Moves;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonDetailResponse {
    @SerializedName("abilities")
    @Expose
    ArrayList <Abilities> abilities;
    @SerializedName("height")
    @Expose
    private float height;
    @SerializedName("moves")
    @Expose
    ArrayList<Moves> moves;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("weight")
    @Expose
    private float weight;

    public ArrayList<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Abilities> abilities) {
        this.abilities = abilities;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public ArrayList<Moves> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Moves> moves) {
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String travelAbilities() {
        StringBuilder abilities = new StringBuilder();

        for (int i = 0; i < this.abilities.size(); i++) {

            if (i == this.abilities.size() - 1) {
                abilities.append(this.abilities.get(i).getAbility().getName());
            }
            else {
                abilities.append(this.abilities.get(i).getAbility().getName()).append(", ");
            }
        }

        return abilities.toString();
    }

    public String travelMoves() {
        StringBuilder moves = new StringBuilder();
        int movesSize = this.moves.size();

        if (movesSize > 4) {
            movesSize = 4;
        }

        Log.d("AbilitiesSize", "travelMoves: "+ movesSize);

        for (int i = 0; i < movesSize; i++) {

            if (i == movesSize - 1) {
                moves.append(this.moves.get(i).getMove().getName());
            }
            else {
                moves.append(this.moves.get(i).getMove().getName()).append(", ");
            }
        }

        return moves.toString();
    }
}
