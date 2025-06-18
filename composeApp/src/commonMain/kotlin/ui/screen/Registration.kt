package ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.common.CustomizedButton
import org.example.project.common.EditPasswordField
import org.example.project.common.EditTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registration(navController: NavController) {

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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = "Register",
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 12.dp)
//            )

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
