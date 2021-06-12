package net.chenyoung.example.composechallengeforandroidstudyjam

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

class MainViewModel : ViewModel() {
    private val appService = ServiceCreator.create<AppService>()

    val query = mutableStateOf("")

    val cocktails: MutableState<List<Cocktail>?> = mutableStateOf(null)

    fun searchByName(name: String = "margarita") {
        appService.searchByName(name).enqueue(object :
            Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                val response = response.body()
                if (response != null) {
                    cocktails.value = response.cocktails
                    for (cocktail in cocktails.value as List<Cocktail>) {
                        Log.d("MainActivity", "id is ${cocktail.id}")
                        Log.d("MainActivity", "name is ${cocktail.name}")
                        Log.d("MainActivity", "category is ${cocktail.category}")
                    }
                }
            }

            override fun onFailure(
                call: Call<Response>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }

    fun searchByCategory(category: String = "Ordinary_Drink") {
        appService.fetchByCategory(category).enqueue(object :
            Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                val response = response.body()
                if (response != null) {
                    for (cocktail in response.cocktails) {
                        Log.d("MainActivity", "id is ${cocktail.id}")
                        Log.d("MainActivity", "name is ${cocktail.name}")
                        Log.d("MainActivity", "category is ${cocktail.category}")
                    }
                }
            }

            override fun onFailure(
                call: Call<Response>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }
}