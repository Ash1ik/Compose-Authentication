package ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import api.sendLogoutRequest
import authentication.composeapp.generated.resources.Res
import authentication.composeapp.generated.resources.smoke_man
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.example.project.SessionManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    userName: String,
    userEmail: String,
    navController: NavController,
    onBackClick: () -> Unit
) {
    val drawerOpen = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // Main Content with TopAppBar
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Profile",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
//                    navigationIcon = {
//                        IconButton(onClick = onBackClick) {
//                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                        }
//                    },
                    actions = {
                        IconButton(onClick = { drawerOpen.value = true }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )

            }
        ) { padding ->
            // Place your profile content here like before
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileContent(padding)

                Spacer(modifier = Modifier.height(32.dp))

                TextView(
                    labelName = "Username",
                    mainText = userName
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextView(
                    labelName = "Email I'd",
                    mainText = userEmail
                )
            }
        }

        // Right-side Drawer
        AnimatedVisibility(
            visible = drawerOpen.value,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(250.dp)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { drawerOpen.value = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close Drawer")
                    }
                }
                Text("Settings", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                val token = SessionManager.getAuthToken()
                Text(
                    text = "Logout",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                try {
                                    sendLogoutRequest(token)
                                    navController.navigate("Registration") {
                                        popUpTo("Profile") { inclusive = true }
                                    }
                                    SessionManager.logout()
                                } catch (e: Exception) {
                                    println("Logout failed: ${e.message}")
                                }
                            }
                        }
                        .padding(8.dp)
                )

            }
        }
    }
}

@Composable
fun ProfileContent(padding: PaddingValues) {
    // Paste your current profile screen body here (the one with the column, image, button etc.)
    Column(
        modifier = Modifier
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFFF8D76))
                    .height(125.dp)
                    .fillMaxWidth()
            ) {

            }
            Card(
                modifier = Modifier
                    .height(122.dp)
                    .width(122.dp)
                    .align(Alignment.BottomCenter)
                    .border(
                        width = 2.dp,
                        color = Color.White, // Or any other color you prefer
                        shape = CircleShape
                    ),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.smoke_man),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

//        Button(
//            onClick = {
//
//            },
//            modifier = Modifier
//                .width(110.dp)
//                .height(36.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Black
//            ),
//            shape = RoundedCornerShape(5.dp),
//            elevation = ButtonDefaults.buttonElevation(4.dp)
//        ) {
//            Text(
//                text = "Edit Profile",
//                fontSize = 12.sp
//            )
//        }
    }
}


@Composable
fun TextView(labelName: String, mainText: String){

    Column() {
        Text(
            text = labelName,
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(318.dp)
                .height(40.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = mainText,
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}