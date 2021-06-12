package net.chenyoung.example.composechallengeforandroidstudyjam

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("drinks") var cocktails: List<Cocktail>)
