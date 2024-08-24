package com.rishiz.institute.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rishiz.institute.data.remote.response.NetworkResponse
import com.rishiz.institute.domain.model.SocialMediaLinkReq
import com.rishiz.institute.ui.component.CustomTextField
import com.rishiz.institute.viewmodel.InstituteViewModel


@Composable
fun AddLinkScreen(navController: NavController, viewModel: InstituteViewModel = hiltViewModel()) {
    val addState by viewModel.saveState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

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
            Toast.makeText(LocalContext.current, "Link Added Successfully", Toast.LENGTH_SHORT)
                .show()
            navController.popBackStack()
        }
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

            val focusManager = LocalFocusManager.current
            Text(
                text = "Add Social Media Links",
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
            OutlinedButton(
                onClick = {
                    val req = SocialMediaLinkReq(
                        userId = user.value,
                        platform = platform.value,
                        url = url.value
                    )
                    viewModel.saveLinkApi(req)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Add",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                )

            }

        }
    }
}
