package api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.SessionManager

val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    install(Logging){
        level = LogLevel.ALL
    }
}

suspend fun sendSignUpRequest(signUpData: SignUpRequest): HttpResponse {
    return client.post("http://192.168.3.184:8000/api/v1/mobile/sign_up") {
        contentType(ContentType.Application.Json)
        setBody(signUpData)
    }
}

suspend fun sendSignInRequest(signInData: SignInRequest): SignInResponse {
    return client.post("http://192.168.3.184:8000/api/v1/mobile/sign_in") {
        contentType(ContentType.Application.Json)
        setBody(signInData)
    }.body()
}

suspend fun sendLogoutRequest(token: String): HttpResponse {
    val client = HttpClient()
    return client.post("http://192.168.3.184:8000/api/v1/mobile/logout") {
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
    }.also {
        client.close()
    }
}
