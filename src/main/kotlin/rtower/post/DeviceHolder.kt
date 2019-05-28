package rtower.post

import com.google.gson.annotations.SerializedName

data class DeviceHolder(
    @SerializedName("metadata") val metadata: DeviceHolderMetadata = DeviceHolderMetadata(),
    @SerializedName("spec") val spec: DeviceHolderSpec = DeviceHolderSpec()
)