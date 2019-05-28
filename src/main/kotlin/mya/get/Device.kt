package mya.get

import com.google.gson.annotations.SerializedName


data class Device(
    @SerializedName("imei") var imei: String,
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("deviceGroupId") var deviceGroupId: String,
    @SerializedName("sigfoxId") var sigfoxId: String,
    @SerializedName("crtSent") var crtSent: Boolean,
    @SerializedName("keySent") var keySent: Boolean
)