package com.solopov.feature_authentication_impl.presentation.password_recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_impl.R

@Composable
fun PasswordRecoveryScreen(
    state: PasswordRecoveryViewModel.FirstState,
    onSendClick: (String) -> Unit,
    onBackClick: () -> Unit,
    userDataValidator: UserDataValidator,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        BackIconButton(
            icon = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
            onClick = onBackClick)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var text by rememberSaveable { mutableStateOf("") }

        var errorMessage: String? by rememberSaveable { mutableStateOf(null) }

        var isError by rememberSaveable { mutableStateOf(false) }

        var isEnabled by rememberSaveable() { mutableStateOf(false) }

        fun validate(email: String) {
            errorMessage = userDataValidator.validateEmail(email)
            isError = !errorMessage.isNullOrEmpty()
            isEnabled = !isError
        }


        val shape = RoundedCornerShape(8.dp)

        TextField(
            modifier = Modifier
                .widthIn(max = 320.dp),
//                .border(1.dp, colorResource(id = R.color.text_input_box_stroke_color), shape),
//                .background(colorResource(id = R.color.cultured), shape),
            shape = shape,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                errorSupportingTextColor = colorResource(id = R.color.neonRed),
                unfocusedContainerColor = colorResource(id = R.color.cultured),
                focusedContainerColor = colorResource(id = R.color.cultured),
                errorContainerColor = colorResource(id = R.color.cultured),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.colorPrimary),
            ),

            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.inter_regular))),
            value = text,
            onValueChange = {
                text = it
                validate(text)
            },
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        // if isError then errorMessage is definitely not empty or null
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                    )
                }
            },
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                onSendClick(text)
                text = ""
                      },
            enabled = isEnabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.colorPrimary),
                contentColor = Color.White
            ),
        ) {
            Text(
                text = stringResource(R.string.send),
                fontFamily = FontFamily(Font(R.font.inter_semibold))
            )
        }
    }
}

@Composable
fun BackIconButton(
    icon: Painter,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(width = 120.dp, height = 120.dp),
        content = {
            Icon(painter = icon, contentDescription = null)
        },
    )
}

