package ui.screen

import androidx.compose.foundation.clickable
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
import org.example.project.common.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavController) {

    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

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

                EditTextButton("Enter your name", "Name")
                EditTextButton("Enter your email", "Email")
                EditPasswordField("Enter your password", "Password")
                EditPasswordField("Re-Enter your password", "Confirm Password")

                Spacer(modifier = Modifier.height(16.dp))

                CustomizedButton("Sign Up")

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

