package com.example.pokemonapiapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cz.msebera.android.httpclient.Header
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONObject

data class Pokemon(
    val name: String,
    val type: String,
    val imageUrl: String
)

class PokemonViewModel : ViewModel() {
    private val client = AsyncHttpClient()

    var pokemon by mutableStateOf<Pokemon?>(null)
        private set

    fun fetchPokemon(pokemonName: String) {
        val url = "https://pokeapi.co/api/v2/pokemon/$pokemonName"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                response: JSONObject
            ) {
                val name = response.getString("name")
                val type = response.getJSONArray("types")
                    .getJSONObject(0)
                    .getJSONObject("type")
                    .getString("name")
                val imageUrl = response.getJSONObject("sprites")
                    .getString("front_default")

                pokemon = Pokemon(name, type, imageUrl)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                throwable: Throwable,
                errorResponse: JSONObject?
            ) {
                println("Failed to fetch Pokémon: ${throwable.message}")
            }
        })
    }
}
