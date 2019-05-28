package rtower.get

import com.google.gson.annotations.SerializedName

data class DeviceLog(
    @SerializedName("value") val values: MutableMap<String, String> = mutableMapOf()
)