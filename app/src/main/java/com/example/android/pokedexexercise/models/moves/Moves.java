package com.example.android.pokedexexercise.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Moves {
    @SerializedName("move")
    @Expose
    private Move move;

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
