package com.juanmora.square.employeelist.features.home.data.api.responses

import com.google.gson.annotations.SerializedName

data class EmployeeItemResponse(
    @SerializedName("uuid")
    val id: String,
    @SerializedName("full_name")
    val name: String,
    @SerializedName("phone_number")
    val phone: String,
    @SerializedName("email_address")
    val email: String,
    @SerializedName("biography")
    val bio: String,
    @SerializedName("photo_url_small")
    val smallAvatarUrl: String,
    @SerializedName("photo_url_large")
    val largeAvatarUrl: String,
    @SerializedName("team")
    val teamName: String,
    @SerializedName("employee_type")
    val type: String
)
