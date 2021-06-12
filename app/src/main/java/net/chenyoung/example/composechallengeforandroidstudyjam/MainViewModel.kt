package net.chenyoung.example.composechallengeforandroidstudyjam

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback

class MainViewModel : ViewModel(){
    val appService = ServiceCreator.create<AppService>()

    fun searchByName(name: String = "margarita") {
        appService.searchByName(name).enqueue(object :
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