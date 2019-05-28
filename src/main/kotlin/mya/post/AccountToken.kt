package mya.post

import com.google.gson.annotations.SerializedName

data class AccountToken(
    @SerializedName("id_token") var token: String
)