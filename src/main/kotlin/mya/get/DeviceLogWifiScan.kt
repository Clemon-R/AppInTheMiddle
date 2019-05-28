package mya.get


import com.google.gson.annotations.SerializedName

data class DeviceLogWifiScan(
    @SerializedName("mac") var mac: String,
    @SerializedName("rssi") var rssi: Int,
    @SerializedName("ssid") var ssid: String
)