package rtower

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import common.ApiRtower
import common.StateCode
import rtower.post.AccountToken
import java.lang.Exception

class Rtower{
    companion object {
        fun getToken(): rtower.post.AccountToken? {
            val json = Gson().toJson(rtower.post.AccountLogin("removed", "removed"))
            val (_, response, result) = Fuel.post(ApiRtower.AUTH.getUrl()).body(json).responseString()
            when (StateCode.parse(response.statusCode)) {
                StateCode.OK -> return Gson().fromJson(result.component1(), rtower.post.AccountToken::class.java)
                StateCode.KO -> {
                    error(result)
                }
            }
        }

        fun sendDevices(rtowerToken: rtower.post.AccountToken, packet: rtower.post.DeviceHolder): Boolean {
            var yaml = ObjectMapper(YAMLFactory()).writeValueAsString(packet).replace("---\n", "").replace("\"", "")
            val (_, response, _) = Fuel.post(ApiRtower.LISTINGS.getUrl())
                .header(
                    mapOf(
                        "X-User-Token" to rtowerToken.token,
                        "Content-Type" to "application/yaml"
                    )
                ).body(yaml.byteInputStream()).responseString()
            return StateCode.parse(response.statusCode) == StateCode.OK
        }

        fun sendDeviceLog(rtowerToken: rtower.post.AccountToken, imei: String, packet: rtower.post.DeviceData): Boolean {
            var yaml = ObjectMapper(YAMLFactory()).writeValueAsString(packet).replace("---\n", "").replace("\"", "")
            val (_, response, _) = Fuel.post(ApiRtower.DEVICEDATA.parse(imei).getUrl())
                .header(
                    mapOf(
                        "X-User-Token" to rtowerToken.token,
                        "Content-Type" to "application/yaml"
                    )
                ).body(yaml.byteInputStream()).responseString()
            return StateCode.parse(response.statusCode) == StateCode.OK
        }

        fun getDeviceLogs(rtowerToken: rtower.post.AccountToken, imei: String): Array<rtower.get.DeviceLog> {
            val (_, response, result) = Fuel.get(ApiRtower.DEVICELOGSDATA.parse(imei, "0", "10000").getUrl())
                .header(
                    mapOf(
                        "X-User-Token" to rtowerToken.token
                    )
                ).responseString()
            when (StateCode.parse(response.statusCode)) {
                StateCode.OK -> {
                    try {
                        return Gson().fromJson(result.component1(), Array<rtower.get.DeviceLog>::class.java)
                    }catch (e: Exception){
                    }
                    return arrayOf()
                }
                StateCode.KO -> {
                    println(result)
                    return arrayOf()
                }
            }
        }

        fun sendDeviceEventLog(rtowerToken: AccountToken, imei: String, packet: rtower.post.DeviceEventData) : Boolean{
            var json = Gson().toJson(packet)
            val (_, response, result) = Fuel.post(ApiRtower.DEVICEEVENTSDATA.parse(imei).getUrl())
                .header(
                    mapOf(
                        "X-User-Token" to rtowerToken.token
                    )
                ).body(json).responseString()

            return StateCode.parse(response.statusCode) == StateCode.OK
        }
    }
}