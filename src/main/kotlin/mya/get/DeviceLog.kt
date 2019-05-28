package mya.get

import com.google.gson.annotations.SerializedName

data class DeviceLog(
    @SerializedName("IMEI") var imei: String,
    @SerializedName("PhoneNumber") var phoneNumber: String,
    @SerializedName("FirmwareVersion") var firmwareVersion: String,
    @SerializedName("AlarmType") var alarmType: Int,
    @SerializedName("AlarmDate") var alarmDate: String, //Date
    @SerializedName("FixDate") var fixDate: String,
    @SerializedName("Latitude") var latitude: Float,
    @SerializedName("Longitude") var longitude: Float,
    @SerializedName("HDOP") var hdop: Float,
    @SerializedName("GroundSpeed") var groundSpeed: Float,
    @SerializedName("BatGauge") var batGauge: Float,
    @SerializedName("BatStateOfHealth") var batStateOfHealth: Int,
    @SerializedName("AlarmChronoNumber") var alarmChronoNumber: String, //Id
    @SerializedName("Canal") var canal: Int,
    @SerializedName("SerialNumber") var serialNumber: String,
    @SerializedName("Iccid") var iCcId: String,
    @SerializedName("Temperature") var temperature: Float,
    @SerializedName("Pression") var pression: Float,
    @SerializedName("Lastdayweartime") var lastDayWearTime: String,
    @SerializedName("Dayweartime") var dayWearTime: String,
    @SerializedName("WifiScan") var wifiScan: Array<DeviceLogWifiScan>,
    @SerializedName("WifiScanDate") var wifiScanDate: String,
    @SerializedName("GeolocSource") var geolocalisationSource: String,
    @SerializedName("SigfoxId") var sigfoxId: String,
    @SerializedName("SigfoxPac") var sigfoxPac: String,
    @SerializedName("Batchargestatus") var batChargeStatus: Int
)
//"Gsmfwversion":"30.31,A01.00","Kombosvers":"API: 0.7 FW: 0.8","Hwrevision":null,"GPS_ALTITUDE":null,"GPS_HORZ_ACC":null,"GPS_VERT_ACC":null,"GPS_DIRECTION":null,"GPS_TDOP":null,"GPS_VDOP":null,"GPS_NUM_SAT":null,"CELL_ID":null,"CELL_ID_MCC":null,"CELL_ID_MNC":null,"CELL_ID_LAC":null,"CELL_ID_CI":null},