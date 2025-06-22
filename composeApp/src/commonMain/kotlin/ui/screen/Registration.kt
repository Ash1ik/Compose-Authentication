package ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import api.SignUpRequest
import api.sendSignUpRequest
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch
import ui.common.CustomizedButton
import ui.common.EditPasswordField
import ui.common.EditTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavController) {

    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding() // ensures the screen moves up when keyboard appears
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus() // ⬅ Hides keyboard when clicking anywhere outside
                    })
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                EditTextButton("Enter your name", "Name", value = name, onValueChange = { name = it })
                EditTextButton("Enter your phone", "Phone", value = phone, onValueChange = { phone = it })
                EditTextButton("Enter your email", "Email", value = email, onValueChange = { email = it })
                EditPasswordField("Enter your password", "Password", value = password, onValueChange = { password = it })
                EditPasswordField("Re-Enter your password", "Confirm Password", value = confirmPassword, onValueChange = { confirmPassword = it })

                Spacer(modifier = Modifier.height(16.dp))

                CustomizedButton("Sign Up", onClick = {
                    if (password == confirmPassword) {
                        val request = SignUpRequest(
                            name = name,
                            calling_code = "880",
                            phone = phone,
                            email = email,
                            password = password,
                            c_password = confirmPassword
                        )
                        scope.launch {
                            isLoading = true
                            try {
                                val response = sendSignUpRequest(request)
                                if (response.status.isSuccess()) {
                                    snackbarHostState.showSnackbar("Success: ${response.status}")
                                    navController.navigate("login") {
                                        popUpTo("Registration") { inclusive = true }
                                    }
                                } else {
                                    snackbarHostState.showSnackbar("Registration failed: ${response.status}")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message ?: "Unknown error"}")
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Passwords do not match")
                        }
                    }
                },isLoading)


                Spacer(modifier = Modifier.height(32.dp))

                SignInText(
                    onSignInClick = {
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}


@Composable
fun SignInText(onSignInClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.Light)) {
            append("Don't have an account? ")
        }
        pushStringAnnotation(tag = "SIGN_IN", annotation = "sign_in")
        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
            append("Sign in")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "SIGN_IN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSignInClick()
                }
        },
        style = TextStyle(fontSize = 14.sp),
        modifier = Modifier.padding(top = 12.dp)
    )
}
