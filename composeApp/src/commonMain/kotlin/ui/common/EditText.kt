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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTextButton(
    textName: String,
    name: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val isError = value.length > 40
    val isValid = value.isNotEmpty() && !isError
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
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
            value = value.take(40),
            onValueChange = onValueChange,
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
                    isValid -> Color(0xFF4CAF50)
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

@Composable
fun PhoneNumberField(
    textName: String,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val displayNumber = if (phoneNumber.startsWith("0")) phoneNumber.drop(1) else phoneNumber
    val maxDigits = 10 // typical after +880

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = textName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = displayNumber.take(maxDigits),
            onValueChange = {
                val clean = if (it.startsWith("0")) it.drop(1) else it
                onPhoneChange(clean.filter { c -> c.isDigit() }) // keep digits only
            },
            label = { Text("+880") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .width(342.dp)
                .height(62.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )
    }
}
