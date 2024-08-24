package com.rishiz.socialmediaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
                navigation = { edit, it ->
                    navController.navigate(
                        ScreenAddLink(
                            isEdit = edit,
                            userId = it.userId,
                            url = it.linkedin,
                            icon = ""//it.icon is null for some post it is crashing
                        )
                    )
                })
        }
        composable<ScreenAddLink> {
            val arg = it.toRoute<ScreenAddLink>()
            AddLinkScreen(
                navController, navigate = {
                    navController.navigate(ScreenLinkList) {
                        popUpTo(
                            ScreenAddLink(
                                isEdit = false,
                                userId = "",
                                url = "",
                                icon = ""
                            )
                        ) { inclusive = true }
                    }
                },
                isEdit = arg.isEdit,
                userId = arg.userId,
                mediaUrl = arg.url,
                mediaIcon = arg.icon
            )
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
data class ScreenAddLink(val isEdit: Boolean, val userId: String, val url: String, var icon: String)

