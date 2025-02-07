package com.example.cosmicexplorerapp_jeyakumari_ramji.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cosmicexplorerapp_jeyakumari_ramji.database.AppDatabase
import com.example.cosmicexplorerapp_jeyakumari_ramji.network.RetrofitInstance
import com.example.cosmicexplorerapp_jeyakumari_ramji.repository.CosmicRepository
import com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens.FavoritesScreen
import com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens.HomeScreen
import com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens.MarsRoverScreen
import com.example.cosmicexplorerapp_jeyakumari_ramji.ui.screens.DetailScreen
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModel
import com.example.cosmicexplorerapp_jeyakumari_ramji.viewmodel.CosmicViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the application context and database instance
        val context = applicationContext
        val apiService = RetrofitInstance.apiService
        val database = AppDatabase.getInstance(context)

        // Create the repository and pass context and database
        val repository = CosmicRepository(apiService = apiService, database = database)

        // Create the ViewModel using the factory
        val viewModel: CosmicViewModel = ViewModelProvider(
            this,
            CosmicViewModelFactory(application, apiService, repository) // Pass necessary parameters
        )[CosmicViewModel::class.java]

        setContent {
            // NavController will manage the navigation
            val navController = rememberNavController()

            // NavHost defines the available screens and their navigation paths
            NavHost(navController = navController, startDestination = "home") {
                // Home screen, where you can navigate to other screens
                composable("home") {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToMarsRover = { navController.navigate("marsRover") },
                        onNavigateToFavorites = { navController.navigate("favorites") }
                    )
                }

                // MarsRover screen where photos are displayed
                composable("marsRover") {
                    MarsRoverScreen(
                        navController = navController, // Pass navController here
                        viewModel = viewModel
                    )
                }

                // Favorites screen that displays the list of favorite photos
                composable("favorites") {
                    FavoritesScreen(viewModel = viewModel)
                }

                // DetailScreen to show detailed info about a selected photo
                composable("detail/{photoId}") { backStackEntry ->
                    val photoId = backStackEntry.arguments?.getString("photoId")
                    DetailScreen(photoId = photoId, viewModel = viewModel)
                }
            }
        }
    }
}