package pe.edu.upc.superherocompose.ui.screens.home

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import pe.edu.upc.superherocompose.ui.screens.superheroes.FavoritesSuperHeroes
import pe.edu.upc.superherocompose.ui.screens.superheroes.SuperHeroViewModel
import pe.edu.upc.superherocompose.ui.screens.superheroes.SuperHeroes

@Composable
fun HomeNavigation (viewModel: SuperHeroViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(
                navigateFind = { navController.navigate("SuperHeroes") },
                navigateFavorites = { navController.navigate("Favorites") }
            )
        }
        composable("SuperHeroes") {
            SuperHeroes(viewModel = viewModel)
        }
        composable("Favorites") {
            FavoritesSuperHeroes(viewModel = viewModel)
        }
    }
}
@Composable
fun HomeScreen(
    navigateFind: () -> Unit = {},
    navigateFavorites: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column {
        BrandingImage(modifier)
        Row {
            Spacer(modifier = modifier.size(16.dp))
            Button(
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .size(200.dp, 48.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                onClick = { navigateFind() }
            ) {
                Text(text = "Find heroes")
            }

            Button(
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .size(200.dp, 48.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                onClick = { navigateFavorites() }
            ) {
                Text(text = "Favorites")
            }
        }
    }
    //HomeNavigation(viewModel)
}

@Composable
fun BrandingImage(modifier: Modifier = Modifier) {
    AsyncImage(
        model = "https://www.womenseday.org/wp-content/uploads/2017/11/UPCMini-1.jpg",
        contentDescription = null,
        modifier = modifier
            .size(200.dp)
            .clip(shape = RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
    )
}