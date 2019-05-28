package mya.post

import com.google.gson.annotations.SerializedName

data class AccountLogin(
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
)