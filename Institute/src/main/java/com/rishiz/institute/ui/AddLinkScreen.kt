package com.rishiz.institute.ui

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rishiz.institute.R
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import com.rishiz.institute.ui.component.CustomTextField
import com.rishiz.institute.viewmodel.InstituteViewModel
import com.rishiz.user.ui.screens.components.CircleLoader
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun AddLinkScreen(
    navController: NavController, viewModel: InstituteViewModel = hiltViewModel(),
    isEdit: Boolean,
    userId: String = "",
    mediaUrl: String = "",
    mediaIcon: String = "",
    navigate: () -> Unit,
) {
    val context = LocalContext.current
    val addState by viewModel.saveState.collectAsState()
    val updateState by viewModel.updateState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var imgUri = rememberSaveable { mutableStateOf("") }
    var painter = rememberAsyncImagePainter(
        imgUri.value.ifEmpty {
            com.rishiz.user.R.drawable.instagram
        }
    )
    var imageMultipart by remember { mutableStateOf<MultipartBody.Part?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imgUri.value = it.toString()
            // Convert the URI to MultipartBody.Part
            imageMultipart = uriToMultipart(it, context, "icon")
        }

    }

    when (addState) {
        NetworkResponse.Empty -> {
            isLoading = false
        }

        NetworkResponse.Loading -> {
            isLoading = true
        }

        is NetworkResponse.Error -> {
            isLoading = false
            val response = addState as NetworkResponse.Error
            Toast.makeText(LocalContext.current, response.error, Toast.LENGTH_SHORT).show()
        }

        is NetworkResponse.Success -> {
            isLoading = false
            Toast.makeText(LocalContext.current, "Link Added Successfully", Toast.LENGTH_SHORT)
                .show()

        }

    }
    when (updateState) {
        NetworkResponse.Empty -> {
            isLoading = false
        }

        NetworkResponse.Loading -> {
            isLoading = true
        }

        is NetworkResponse.Error -> {
            isLoading = false
            val response = updateState as NetworkResponse.Error
            Toast.makeText(LocalContext.current, response.error, Toast.LENGTH_SHORT).show()
        }

        is NetworkResponse.Success -> {
            isLoading = false
            Toast.makeText(LocalContext.current, "Link update Successfully", Toast.LENGTH_SHORT)
                .show()

        }

    }
    if (isLoading) {
        CircleLoader()
    }
    Surface {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            val user = rememberSaveable { mutableStateOf("") }
            val platform = rememberSaveable { mutableStateOf("") }
            val url = rememberSaveable { mutableStateOf("") }

            if (isEdit) {
                user.value = userId
                url.value = mediaUrl

            }
            val focusManager = LocalFocusManager.current
            Text(
                text = if (isEdit) "Update Social Media Links" else "Add Social Media Links",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.height(30.dp))

            CustomTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                hint = "Enter User",
                textFieldValue = user,
                textLabel = "User",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                imeAction = ImeAction.Next,
            )

            CustomTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                hint = "Enter Social Media Platform",
                textFieldValue = platform,
                textLabel = "Social Media Platform",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                imeAction = ImeAction.Next,
            )
            CustomTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                hint = "Enter Url",
                textFieldValue = url,
                textLabel = "Url",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                imeAction = ImeAction.Next,
            )
            Spacer(Modifier.height(30.dp))

            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(150.dp)
                            .background(Color.LightGray)
                            .border(
                                width = 1.dp,
                                color = Color.Green,
                                shape = CircleShape
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 150.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Yellow,
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_add_circle_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(50.dp)
                            .padding(10.dp)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            OutlinedButton(
                onClick = {
                    val req = SocialMediaLinkReq(
                        userId = user.value,
                        platform = platform.value,
                        url = url.value,
                        icon = imgUri.value
                    )

                    if (isEdit) {
                        viewModel.updateLinkApi(req)
                    } else {
                        viewModel.saveLinkApi(req)
                    }
                    navigate()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = if (isEdit) "Update" else "Add",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                )

            }

        }
    }
}

fun uriToMultipart(uri: Uri, context: Context, paramName: String): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)

    val tempFile = File.createTempFile("upload", "tmp", context.cacheDir)
    tempFile.deleteOnExit()

    inputStream?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }

    // Create a RequestBody instance from the file
    val requestFile = tempFile
        .asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())

    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(paramName, tempFile.name, requestFile)
}
