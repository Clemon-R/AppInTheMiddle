package rtower.post

import com.google.gson.annotations.SerializedName

data class DeviceMetadata(
    @SerializedName("name") var name: String,
    @SerializedName("labels") val labels: MutableMap<String, String> = mutableMapOf("fleet" to "mya")
)