package ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import api.SignInRequest
import api.sendSignInRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.common.CustomizedButton
import ui.common.EditPasswordField
import ui.common.EditTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(onBackClick: () -> Unit,navController: NavController) {

    val focusManager = LocalFocusManager.current
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    var identity by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                            text = "Login",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )

        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus() // ⬅ Hides keyboard when clicking anywhere outside
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EditTextButton("Enter your email", "Email", value = identity , onValueChange = { identity = it })
            EditPasswordField("Enter your password", "Password", value = password, onValueChange = { password = it })

            Spacer(modifier = Modifier.height(16.dp))

            CustomizedButton(
                name = "Login",
                onClick = {
                    val request = SignInRequest(identity, password)
                    scope.launch {
                        isLoading = true
                        try {
                            val response = withContext(Dispatchers.IO) {
                                sendSignInRequest(request)
                            }
                            val user = response.data.auth_user
                            val name = user.name
                            val email = user.email

                            navController.navigate("Profile/$name/$email") {
                                popUpTo("login") { inclusive = true }
                            }

                        } catch (e: Exception) {
                            println("Snackbar: ${e.message}")
                            snackbarHostState.showSnackbar("Error: ${e.message ?: "Unknown error"}")
                        } finally {
                            isLoading = false
                        }
                    }
                },
                isLoading = isLoading
            )




        }
    }
}
