package com.rishiz.institute.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun CustomTextField(
    modifier: Modifier,
    textFieldValue: MutableState<String>,
    textLabel: String ="",
    onValueChange: () -> Unit = {},
    hint: String="",
    maxChar: Int? = null,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isReadOnly:Boolean=false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue.value.take(maxChar ?: 40),
        onValueChange = {
            textFieldValue.value = it
            onValueChange()
        },
        label = {
            Text(text = textLabel)
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color= MaterialTheme.colorScheme.primary
        ),
        placeholder = { Text(text = hint) },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization, keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        readOnly = isReadOnly
    )
}