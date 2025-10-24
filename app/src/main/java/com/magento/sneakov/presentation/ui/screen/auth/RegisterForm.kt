package com.magento.sneakov.presentation.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import compose.icons.fontawesomeicons.solid.AddressCard
import compose.icons.fontawesomeicons.solid.Eye
import compose.icons.fontawesomeicons.solid.EyeSlash
import compose.icons.fontawesomeicons.solid.Lock
import compose.icons.fontawesomeicons.solid.User
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterForm(
    viewModel: RegisterViewModel = getViewModel(),
    onSuccessRegister: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Regex kiểm tra cơ bản
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/~]).{6,}$")

    // Trạng thái hợp lệ
    val isEmailValid = emailRegex.matches(email)
    val isNameValid = name.trim().length >= 3
    val isPasswordValid = passwordRegex.matches(password)

    // Nút chỉ bật khi mọi thứ hợp lệ
    val isFormValid = isEmailValid && isNameValid && isPasswordValid

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Đăng ký tài khoản", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        // --- EMAIL ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = email.isNotEmpty() && !isEmailValid,
            supportingText = {
                if (email.isNotEmpty() && !isEmailValid) {
                    Text("Email không hợp lệ")
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.User,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // --- HỌ TÊN ---
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Họ tên") },
            isError = name.isNotEmpty() && !isNameValid,
            supportingText = {
                if (name.isNotEmpty() && !isNameValid) {
                    Text("Họ tên phải có ít nhất 3 ký tự")
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.AddressCard,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // --- MẬT KHẨU ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            isError = password.isNotEmpty() && !isPasswordValid,
            supportingText = {
                if (password.isNotEmpty() && !isPasswordValid) {
                    Text("Mật khẩu phải ≥ 6 ký tự và có ít nhất 1 số")
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) FontAwesomeIcons.Solid.Eye else FontAwesomeIcons.Solid.EyeSlash,
                        contentDescription = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu",
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
        )

        Spacer(Modifier.height(24.dp))

        // --- NÚT ĐĂNG KÝ ---
        Button(
            onClick = {viewModel.register(email = email, password = password, fullName = name)},
            enabled = isFormValid && state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.6f)
            )
        ) {
            Text("Đăng ký")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text("Đăng nhập")
        }
    }
    when{
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
        state.isSuccess -> onSuccessRegister()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {
    SneakovTheme {
        //RegisterForm(onNavigateToLogin = {})
    }
}
