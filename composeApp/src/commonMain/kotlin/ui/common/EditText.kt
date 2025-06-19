package ui.common


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTextButton(textName: String, name: String) {

    var text by remember { mutableStateOf("") }
    val isError = text.length > 40
    val isValid = text.isNotEmpty() && !isError
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
//            .clickable{ focusManager.clearFocus() }
    ) {
        Text(
            text = textName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))



        OutlinedTextField(
            value = text.take(40),
            onValueChange = { text = it },
            label = { Text(name) },
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = when {
                    isError -> Color.Red
                    isValid -> Color(0xFF4CAF50) // Green
                    else -> Color.Gray
                },
                unfocusedBorderColor = when {
                    isError -> Color.Red
                    isValid -> Color(0xFF4CAF50)
                    else -> Color.Gray
                },
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            ),
            modifier = Modifier
                .width(342.dp)
                .height(62.dp),
            shape = RoundedCornerShape(16.dp)
        )

    }


}