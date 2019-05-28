package rtower.post

import com.google.gson.annotations.SerializedName

data class DeviceHolderMetadata(
    @SerializedName("namespace") val namespace: String = "default",
    @SerializedName("name") val name: String = "charly-mya-test"
)