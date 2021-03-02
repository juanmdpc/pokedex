package com.example.android.pokedexexercise.models.abilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Abilities {

    @SerializedName("ability")
    @Expose
    private Ability ability;

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
}
