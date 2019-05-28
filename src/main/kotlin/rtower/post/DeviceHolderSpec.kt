package rtower.post


import com.google.gson.annotations.SerializedName

data class DeviceHolderSpec(
    @SerializedName("devices") val devices: MutableList<Device> = mutableListOf()
)