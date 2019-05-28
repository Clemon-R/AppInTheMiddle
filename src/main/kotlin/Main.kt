@file:JvmName("MainKt")

import com.github.kittinunf.fuel.core.FuelManager
import com.google.gson.Gson
import mya.Mya
import rtower.Rtower
import kotlin.system.measureTimeMillis

/*interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)
}*/

//inline fun <reified T: JSONConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)

private val targetedDevice = "removed"

private val workingDevices: MutableList<mya.get.Device> = mutableListOf()

fun main() {
    FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json"
        , "Accept" to "*/*"
        , "Accept-Encoding" to "gzip, deflate"
        , "Connection" to "keep-alive")

    println("Getting the account token from mya...")
    val myaToken: mya.post.AccountToken = Mya.getToken() ?: return
    println("Current mya token ${myaToken.token}")

    println("Getting the account token from rtower...")
    val rtowerToken: rtower.post.AccountToken = Rtower.getToken() ?: return
    println("Current rtower token ${rtowerToken.token}")

    val myaDevices = Mya.getDevices(myaToken)
    println("Creating form to send the devices...")

    val packetFleet = rtower.post.DeviceHolder()

    for (device in myaDevices){
        println("Device(${device.imei}) ${device.name} - id ${device.id}: ${device.description}")

        val meta = rtower.post.DeviceMetadata("mya-${device.imei}")
        meta.labels += ("group" to device.deviceGroupId)

        val tmp = rtower.post.Device(meta)
        tmp.spec += ("serial" to device.id)

        packetFleet.spec.devices += tmp

        println("Device(${device.imei}) saved")
        workingDevices += device
    }
    println("Result: ${workingDevices.size} saved on ${myaDevices.size}")
    println("Send all the devices...")
    if (Rtower.sendDevices(rtowerToken, packetFleet)){
        println("All devices has been send")
    } else{
        error("Error happend")
    }
    println("Starting automatic in 1 secondes")
    Thread.sleep(1000)
    automaticSendData(myaToken, rtowerToken)
}

private fun automaticSendData(myaToken: mya.post.AccountToken, rtowerToken: rtower.post.AccountToken)
{
    println("Automatic adding logs...")
    val historic: MutableList<String> = mutableListOf()
    while (true) {
        if (workingDevices.isEmpty()){
            println("No device to check")
            break
        }
        println("Trying to find some new logs...")
        val start = measureTimeMillis {}
        val tmp = workingDevices.toMutableList()
        for (device in tmp) {
            if (targetedDevice != null && targetedDevice != device.imei){
                workingDevices.remove(device)
                continue
            }

            //println("Device(${device.imei}) Downloading current logs on Mya...")
            val myaLogs = Mya.getDeviceLogs(myaToken, device)

            if (myaLogs.isEmpty()){
                workingDevices.remove(device)
                continue
            }

            //println("Device(${device.imei}) Downloading current logs on Rtower...")
            val rtowerLogs = Rtower.getDeviceLogs(rtowerToken, device.imei)

            for (log in myaLogs) {
                if (log.alarmChronoNumber == "null" || historic.contains(log.alarmChronoNumber))
                    continue
                var check = true
                for (rlog in rtowerLogs) {
                    if (rlog.values.containsKey("id") && rlog.values["id"] == log.alarmChronoNumber)
                    {
                        check = false
                        break
                    }
                }
                if (check) {
                    println(Gson().toJson(log))
                    println("Device(${device.imei}) New log found, adding to rtower...")
                    val data = rtower.post.DeviceData(log.alarmDate.replace("[GMT]", ""))
                    data.value += ("id" to "${log.alarmChronoNumber}")
                    data.value += ("alarmType" to "${log.alarmType}")
                    data.value += ("battLevel" to "${log.batGauge}")
                    data.value += ("battCharging" to "${log.batChargeStatus.equals("1")}")
                    data.value += ("temp" to "${log.temperature}")
                    data.value += ("pressure" to "${log.pression}")
                    data.location += ("lat" to log.latitude)
                    data.location += ("lng" to log.longitude)
                    if (!Rtower.sendDeviceLog(rtowerToken, log.imei, data))
                        error("DeviceLog(${log.imei}) Impossible to send the data")
                    var event: rtower.post.DeviceEventData? = null
                    when (log.alarmType){
                        1 -> {
                            event = rtower.post.DeviceEventData(
                                "Alarm start"
                                , "WARN"
                                , "mya_alarm"
                                , log.alarmDate.replace("[GMT]", "")
                            )
                        }
                        15 -> {
                            event = rtower.post.DeviceEventData(
                                "Alarm safe"
                                , "WARN"
                                , "mya_alarm"
                                , log.alarmDate.replace("[GMT]", "")
                                , true
                            )
                        }
                        16 -> {
                            event = rtower.post.DeviceEventData(
                                "Alarm end"
                                , "WARN"
                                , "mya_alarm"
                                , log.alarmDate.replace("[GMT]", "")
                                , true
                            )
                        }
                        11 -> {
                            event = rtower.post.DeviceEventData(
                                "Alarm still running"
                                , "WARN"
                                , "mya_alarm"
                                , log.alarmDate.replace("[GMT]", "")
                            )
                        }
                    }
                    if (event != null && !Rtower.sendDeviceEventLog(rtowerToken, log.imei, event!!))
                        error("DeviceEvent(${log.imei}) Impossible to send the event")
                    historic += log.alarmChronoNumber
                }
            }
        }
        println("The scanning time was about ${(measureTimeMillis { } - start) / (1000)} secondes")
        Thread.sleep(10000)
    }
}