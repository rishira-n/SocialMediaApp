package com.rishiz.institute.ui

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.viewmodel.InstituteViewModel
import com.rishiz.user.domain.model.SocialMediaLink
import com.rishiz.user.ui.screens.components.CircleLoader
import com.rishiz.user.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteScreen(
    viewModel1: UserViewModel = hiltViewModel(),
    viewModel: InstituteViewModel = hiltViewModel(),
    navigation: (isEdit: Boolean, arg: SocialMediaLink) -> Unit,
) {
    val linkState by viewModel1.linkState.collectAsState()
    val deleteState by viewModel.deleteState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    var linkList = remember { emptyList<SocialMediaLink>() }


    LaunchedEffect(Unit) {
        viewModel1.getAllLinks()
    }
    //Observing social media links state
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
            if (response.data.statusCode == 200) {
                linkList = response.data.data!!
            }
//            if (linkList.isEmpty()) {
//                linkList = getDummySocialMediaLinks()
//            }
        }
    }

    //Observing social media delete state
    when (deleteState) {
        NetworkResponse.Empty -> {}
        is NetworkResponse.Error -> {
            isLoading = false
            Toast.makeText(
                LocalContext.current,
                (deleteState as NetworkResponse.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }

        NetworkResponse.Loading -> {
            isLoading = true
        }

        is NetworkResponse.Success -> {
            isLoading = false
            Toast.makeText(LocalContext.current, "Link Deleted Successfully", Toast.LENGTH_SHORT)
                .show()
        }
    }
    if (isLoading) {
        CircleLoader()
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Institute") }
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigation(
                        false, SocialMediaLink(
                            _id = "",
                            icon = "",
                            linkedin = "",
                            userId = ""
                        )
                    )
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

//                val painter = when (it.platform.lowercase()) {
//                    "linkedin" -> painterResource(R.drawable.linkedin)
//                    "snapchat" -> painterResource(R.drawable.snapchat)
//                    "facebook" -> painterResource(R.drawable.facebook)
//                    "skype" -> painterResource(R.drawable.skype)
//                    "instagram" -> painterResource(R.drawable.instagram)
//                    "whatsapp" -> painterResource(R.drawable.whatsapp)
//                    "youtube" -> painterResource(R.drawable.youtube)
//                    else -> painterResource(R.drawable.linkedin)
//                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    //Icon
                    AsyncImage(
                        model = it.icon,
                        contentDescription = "icons",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(10.dp))

                    Column {
                        Row {
                            //Social media platform and link
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.8f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = it.linkedin,
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                    )
                                )
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = it.linkedin,
                                    style = TextStyle(
                                        color = Color.Blue,
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                    )
                                )
                            }
                            //Edit and Delete
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.3f)) {
                                Card(
                                    modifier = Modifier.size(30.dp),
                                ) {
                                    IconButton(
                                        onClick = {

                                            navigation(true, it)
                                        }) {
                                        // Use the Icon composable here
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "Edit icon",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                }
                                Spacer(Modifier.width(10.dp))
                                Card(
                                    modifier = Modifier.size(30.dp),
                                ) {
                                    IconButton(
                                        onClick = { viewModel.deleteLinkApi(it.userId) }) {
                                        // Use the Icon composable here
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "delete icon",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        HorizontalDivider(Modifier.height(1.dp))
                    }


                }
            }
        }
    }
}

//fun getDummySocialMediaLinks(): List<SocialMediaLink> {
//    return listOf(
//        SocialMediaLink(userId = "user1", platform = "Snapchat", url = "http://snapchat.com/user1",""),
//        SocialMediaLink(
//            userId = "user2",
//            platform = "Instagram",
//            url = "http://instagram.com/user2",""
//        ),
//        SocialMediaLink(userId = "user4", platform = "YouTube", url = "http://youtube.com/user4","")
//    )
//}
