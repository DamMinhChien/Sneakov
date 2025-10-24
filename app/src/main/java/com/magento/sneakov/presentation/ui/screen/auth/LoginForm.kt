package com.magento.sneakov.presentation.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magento.sneakov.presentation.ui.theme.SneakovTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Envelope
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash
import compose.icons.fontawesomeicons.solid.Lock
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginForm(viewModel: LoginViewModel = koinViewModel(), onNavigateToHome: ()-> Unit, onNavigateToRegister: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.isSuccess) {
        if(uiState.isSuccess){
            onNavigateToHome()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Chào mừng trở lại!", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var rememberMe by remember { mutableStateOf(false) }
        var passwordVisible by remember { mutableStateOf(false) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }

        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/~]).{6,}$")
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.trim()
                emailError = when{
                    it.isBlank() -> "Email không được để trống"
                    !emailRegex.matches(it) -> "Email không hợp lệ"
                    else -> null
                }
            },
            isError = emailError != null,
            supportingText = {
                if (emailError != null){
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Envelope,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it.trim()
                passwordError = when{
                    it.isBlank() -> "Mật khẩu không được để trống"
                    it.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự"
                    !passwordRegex.matches(it) -> "Phải có ít nhất 1 chữ hoa và 1 chữ số"
                    else -> null
                }
            },
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            label = { Text("Mật khẩu") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if(passwordVisible) FontAwesomeIcons.Solid.Eye else FontAwesomeIcons.Solid.EyeSlash,
                        contentDescription = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu",
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text("Nhớ mật khẩu")
            }
            Text(
                "Quên mật khẩu?", color = Color.Blue, modifier = Modifier.clickable { /* TODO */ })
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.login(username = email, password = password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp),
            enabled = passwordError == null && emailError == null && uiState.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(text = "Đăng nhập")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onNavigateToRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = Color.Gray
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
        ) {
            Text(text = "Đăng ký")
        }

        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.token != null -> Text("Token: ${uiState.token}")
            uiState.error != null -> Text("Error: ${uiState.error}")
            uiState.isSuccess -> Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_LONG).show()
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun LoginCardPreview() {
    SneakovTheme {
        AuthScreen(onNavigateToHome = {})
    }
}