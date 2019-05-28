package rtower.post

import com.google.gson.annotations.SerializedName

data class DeviceEventData(
    @SerializedName("Description") var description: String,
    @SerializedName("Level") var level: String,
    @SerializedName("Type") var type: String,
    @SerializedName("time") var time: String,
    @SerializedName("Closed") var close: Boolean = false
)