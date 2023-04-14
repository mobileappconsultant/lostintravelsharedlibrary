package model

import kotlinx.serialization.SerialName

data class UserModel(
    @SerialName("full_name")
    val fullName: String? = null,

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phoneNumber: String? = null,

    @SerialName("password")
    val password: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("location")
    val location: Location = Location("","")
)