package api


import kotlinx.serialization.Serializable

//@Serializable
//data class SignUpRequest(
//    val name: String,
//    val calling_code: String,
//    val phone: String,
//    val email: String,
//    val password: String,
//    val c_password: String
//)

@Serializable
data class SignUpRequest(
    val username: String,
    val email: String,
    val password1: String,
    val password2: String
)

@Serializable
data class SignInRequest(
    val username: String,
    val email: String,
    val password: String,
)

