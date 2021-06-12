package net.chenyoung.example.composechallengeforandroidstudyjam

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("idDrink") var id: String,
    @SerializedName("strDrink") var name: String,
    @SerializedName("strDrinkThumb") var image: String,
    @SerializedName("strCategory") var category: String
)
