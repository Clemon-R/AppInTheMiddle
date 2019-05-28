package rtower.post


import com.google.gson.annotations.SerializedName

data class AccountToken(
    @SerializedName("token") var token: String
)