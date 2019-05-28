package rtower.post

import com.google.gson.annotations.SerializedName

data class DeviceData(
    @SerializedName("time") var time: String,
    @SerializedName("type") val type: String = "UpLink",
    @SerializedName("value") val value: MutableMap<String, String> = mutableMapOf(),
    @SerializedName("location") val location: MutableMap<String, Float> = mutableMapOf()
)