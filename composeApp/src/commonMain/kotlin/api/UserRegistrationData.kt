package api


import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val name: String,
    val calling_code: String,
    val phone: String,
    val email: String,
    val password: String,
    val c_password: String
)

@Serializable
data class SignInRequest(
    val identity: String,
    val password: String,
)

@Serializable
data class SignInResponse(
    val msg: String,
    val data: SignInData
)

@Serializable
data class SignInData(
    val api_token: String,
    val auth_user: AuthUser
)

@Serializable
data class AuthUser(
    val name: String,
    val email: String
)
