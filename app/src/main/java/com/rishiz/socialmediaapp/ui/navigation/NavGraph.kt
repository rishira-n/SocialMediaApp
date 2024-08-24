package com.rishiz.socialmediaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rishiz.institute.ui.AddLinkScreen
import com.rishiz.institute.ui.InstituteScreen
import com.rishiz.socialmediaapp.ui.Screens.MainScreen
import com.rishiz.user.ui.screens.UserScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController, ScreenMain) {
        composable<ScreenMain> {
            MainScreen(navController)
        }
        composable<ScreenUser> {
            UserScreen()
        }
        composable<ScreenLinkList> {
            InstituteScreen(
                navigation = {
                    navController.navigate(ScreenAddLink)
                })
        }
        composable<ScreenAddLink> {
            AddLinkScreen(navController = navController)
        }
    }

}

@Serializable
object ScreenMain

@Serializable
object ScreenUser

@Serializable
object ScreenLinkList

@Serializable
object ScreenAddLink