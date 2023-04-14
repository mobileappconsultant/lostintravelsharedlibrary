package model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val _id: String,
    val country: String,
    val created_at: String,
    val email: String,
    val full_name: String,
    val password: String,
    val is_password_reset: Boolean,
    val is_verified: Boolean,
    val location: Location,
    val updated_at: String
)