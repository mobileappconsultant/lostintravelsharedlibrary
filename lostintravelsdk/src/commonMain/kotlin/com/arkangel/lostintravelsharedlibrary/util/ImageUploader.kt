package com.arkangel.lostintravelsharedlibrary.util

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

data class UploadResult(
    val success: String?,
    val error: Int?,
)

object ImageUploader {

    private val apiKey = "665581768821491"
    private val apiSecret = "9MoBEx0n4l44pMoJ8tLE6J3iHOo"
    private val uploadPreset = "lostintravel"
    private val cloudName = "mobile-paradigm"

    fun uploadImage(input: ImageBytes): Flow<UploadResult>  = flow {
        val client = HttpClient()

        val auth = "Basic ${"$apiKey:$apiSecret".encodeToBase64()}"

        val image = input.toByteArray()

        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload",
            formData = formData {
                append("file", image, Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=\"file.png\"")
                })
                append("upload_preset", uploadPreset)
                append("api_key", apiKey)
            },
        ) {
            headers {
                append(HttpHeaders.Authorization, auth)
                append(HttpHeaders.Accept, "*/*")
            }
        }

        println("Request: ${response.request.call.body<String>()}")

        if (response.status.value == 200 || response.status.value == 201) {
            val body = response.bodyAsText()

            val data = Json.decodeFromString<Map<String, Any?>>(body)

            emit(UploadResult(
                success = data["url"] as String,
                error = null,
            ))
        } else {
            println("Upload Error: ${response.bodyAsText()}")
            emit(UploadResult(
                success = null,
                error = response.status.value,
            ))
        }
    }

}