package com.rishiz.institute.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rishiz.user.R
import com.rishiz.user.domain.model.SocialMediaLink
import com.rishiz.user.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteScreen(
    viewModel1: UserViewModel = hiltViewModel(),
    navigation: () -> Unit
) {

    var isLoading by remember { mutableStateOf(false) }
    var linkList = remember { emptyList<SocialMediaLink>() }
    val linkState by viewModel1.linkState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel1.getAllLinks()
    }

    when (linkState) {
        com.rishiz.user.data.remote.response.NetworkResponse.Empty -> {

        }

        is com.rishiz.user.data.remote.response.NetworkResponse.Error -> {
            isLoading = false
        }

        com.rishiz.user.data.remote.response.NetworkResponse.Loading -> {
            isLoading = true
        }

        is com.rishiz.user.data.remote.response.NetworkResponse.Success -> {
            isLoading = false
            val response = linkState as com.rishiz.user.data.remote.response.NetworkResponse.Success
            if (response.data.success) {
                linkList = response.data.data!!
            }
            if (linkList.isEmpty()) {
                linkList = getDummySocialMediaLinks()
            }
        }
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Institute") }
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigation()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Link")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(linkList) {
                val painter = when (it.platform.lowercase()) {
                    "linkedin" -> painterResource(R.drawable.linkedin)
                    "snapchat" -> painterResource(R.drawable.snapchat)
                    "facebook" -> painterResource(R.drawable.facebook)
                    "skype" -> painterResource(R.drawable.skype)
                    "instagram" -> painterResource(R.drawable.instagram)
                    "whatsapp" -> painterResource(R.drawable.whatsapp)
                    "youtube" -> painterResource(R.drawable.youtube)
                    else -> painterResource(R.drawable.linkedin)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "icons",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(
                        Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.platform,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = it.url,
                            style = TextStyle(
                                color = Color.Blue,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        )
                        Spacer(Modifier.height(5.dp))
                        HorizontalDivider(Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

fun getDummySocialMediaLinks(): List<SocialMediaLink> {
    return listOf(
        SocialMediaLink(userId = "user1", platform = "Snapchat", url = "http://snapchat.com/user1"),
        SocialMediaLink(
            userId = "user2",
            platform = "Instagram",
            url = "http://instagram.com/user2"
        ),
        SocialMediaLink(userId = "user4", platform = "YouTube", url = "http://youtube.com/user4")
    )
}
