package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val user: User
)