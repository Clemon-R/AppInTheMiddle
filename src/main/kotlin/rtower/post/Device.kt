package rtower.post


import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("metadata") var metadata: DeviceMetadata,
    @SerializedName("spec") val spec: MutableMap<String, String> = mutableMapOf("manufacturer" to "rtone", "type" to "tracker", "model" to "MYA")
)