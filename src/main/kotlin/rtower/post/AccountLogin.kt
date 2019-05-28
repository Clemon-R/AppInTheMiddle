package rtower.post

import com.google.gson.annotations.SerializedName

data class AccountLogin(
    @SerializedName("login") var username: String,
    @SerializedName("password") var password: String
)