package com.arkangel.lostintravelsharedlibrary.util

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

data class UploadResult(
    val success: ImageUploadData?,
    val error: Int?,
)

//{"format":"png","resource_type":"image","created_at":"2023-05-01T10:11:29Z","tags":[],"bytes":72340,"type":"upload","etag":"96ff0f62787deca4885b1e6648646404","placeholder":false,"url":"http://res.cloudinary.com/mobile-paradigm/image/upload/v1682935889/LostInTravel/dfjttsxz8vqmpl7yoir8.png","secure_url":"https://res.cloudinary.com/mobile-paradigm/image/upload/v1682935889/LostInTravel/dfjttsxz8vqmpl7yoir8.png","folder":"LostInTravel","access_mode":"public","original_filename":"file"}

@kotlinx.serialization.Serializable
data class ImageUploadData(
    @SerialName("asset_id")
    val assetId: String,

    @SerialName("public_id")
    val publicId: String,

    @SerialName("version")
    val version: Int,

    @SerialName("version_id")
    val versionId: String,

    @SerialName("signature")
    val signature: String,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,

    @SerialName("format")
    val format: String,

    @SerialName("resource_type")
    val resourceType: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("url")
    val url: String,

    @SerialName("secure_url")
    val secureUrl: String,
)

object ImageUploader {

    private val apiKey = "665581768821491"
    private val apiSecret = "9MoBEx0n4l44pMoJ8tLE6J3iHOo"
    private val uploadPreset = "lostintravel"
    private val cloudName = "mobile-paradigm"

    fun uploadImage(input: ImageBytes): Flow<UploadResult>  = flow {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }

        val auth = "Basic ${"$apiKey:$apiSecret".encodeToBase64()}"

        val image = input.toByteArray()

        val formData =  formData {
            append("upload_preset", uploadPreset)
            append("api_key", apiKey)
            append("file", image, Headers.build {
                append(HttpHeaders.ContentType, "image/png")
                append(HttpHeaders.ContentDisposition, "filename=\"file.png\"")
            })
        }

        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload",
            formData = formData,
        ) {
            headers {
                append(HttpHeaders.Authorization, auth)
                append(HttpHeaders.Accept, "*/*")
                append("upload_preset", uploadPreset)
            }
            parameter("upload_preset", uploadPreset)
            parameter("api_key", apiKey)
        }

        if (response.status.value == 200 || response.status.value == 201) {
            emit(UploadResult(
                success = response.body(),
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