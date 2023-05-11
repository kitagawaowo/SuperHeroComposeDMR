package pe.edu.upc.superherocompose.ui.screens.superheroes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pe.edu.upc.superherocompose.data.model.SuperHero

@Composable
fun FavoritesSuperHeroes(viewModel: SuperHeroViewModel) {
    val favorites by viewModel.favoriteHeroes.observeAsState(listOf())
    viewModel.fetchFavorites()

    LazyColumn {

        items(favorites) { superHero ->
            SuperHeroCard(
                superHero,
                insertHero = {
                    viewModel.insert(superHero)
                }, deleteHero = {
                    viewModel.delete(superHero)
                })
        }
    }

}
@Composable
fun SuperHeroes(viewModel: SuperHeroViewModel) {

    Column {
        SuperHeroSearch(viewModel = viewModel)
        SuperHeroList(viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroSearch(viewModel: SuperHeroViewModel, modifier: Modifier = Modifier) {
    val name by viewModel.name.observeAsState("")
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = name,
        onValueChange = {
            viewModel.update(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, null)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.fetchByName()
                focusManager.clearFocus()

            }
        )
    )

}

@Composable
fun SuperHeroList(viewModel: SuperHeroViewModel) {
    val superHeroes by viewModel.superHeroes.observeAsState(listOf())

    LazyColumn {

        items(superHeroes) { superHero ->
            SuperHeroCard(
                superHero,
                insertHero = {
                    viewModel.insert(superHero)
                }, deleteHero = {
                    viewModel.delete(superHero)
                })
        }
    }
}

@Composable
fun SuperHeroCard(
    superHero: SuperHero,
    insertHero: () -> Unit,
    deleteHero: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Text(text = superHero.id, modifier = modifier.width(32.dp))
            SuperHeroImage(superHero)
            SuperHeroItem(superHero, insertHero, deleteHero)
        }
    }
}

@Composable
fun SuperHeroItem(
    superHero: SuperHero,
    insertHero: () -> Unit,
    deleteHero: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFavorite = remember {
        mutableStateOf(false)
    }
    isFavorite.value = superHero.favorite

    Spacer(modifier = modifier.width(8.dp))
    Row {
        Column(modifier = modifier.weight(7f)) {
            Text(text = superHero.name, fontWeight = FontWeight.Bold)
            Text(text = superHero.biography.fullName)
        }
        IconButton(
            modifier = modifier.weight(1f),
            onClick = {
                if (isFavorite.value) {
                    deleteHero()
                } else {
                    insertHero()
                }
                isFavorite.value = !isFavorite.value

            }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null,
                tint = if (isFavorite.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SuperHeroImage(superHero: SuperHero, modifier: Modifier = Modifier) {
    AsyncImage(
        model = superHero.image.url,
        contentDescription = null,
        modifier = modifier
            .size(92.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}