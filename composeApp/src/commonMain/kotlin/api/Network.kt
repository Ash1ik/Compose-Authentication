package api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun sendSignUpRequest(signUpData: SignUpRequest): HttpResponse {
    return client.post("https://foodlearningapp.onrender.com/auth/registration/") {
        contentType(ContentType.Application.Json)
        setBody(signUpData)
    }
}

suspend fun sendSignInRequest(signInData: SignInRequest): HttpResponse {
    return client.post("https://foodlearningapp.onrender.com/auth/login/") {
        contentType(ContentType.Application.Json)
        setBody(signInData)
    }
}
