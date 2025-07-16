package com.example.pokemonapiapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokemonapiapp.PokemonViewModel

@Composable
fun PokemonScreen(viewModel: PokemonViewModel = viewModel()) {
    var input by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter Pokémon name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.fetchPokemon(input.text.lowercase())
        }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.pokemon?.let { pokemon ->
            Text("Name: ${pokemon.name}", style = MaterialTheme.typography.headlineSmall)
            Text("Type: ${pokemon.type}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier.size(120.dp)
            )
        }
    }
}
