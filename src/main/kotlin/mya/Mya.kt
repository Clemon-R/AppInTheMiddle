package mya

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson
import common.ApiMya
import common.StateCode
import mya.get.Device
import mya.get.DeviceLog
import mya.post.AccountLogin
import mya.post.AccountToken
import java.lang.Exception

class Mya {
    companion object{
        fun getToken() : AccountToken?
        {
            val json = Gson().toJson(AccountLogin("removed", "removed"))
            val (_, response, result) =  Fuel.post(ApiMya.AUTH.getUrl()).jsonBody(json).responseString()
            when (StateCode.parse(response.statusCode)){
                StateCode.OK -> return Gson().fromJson(result.component1(), AccountToken::class.java)
                StateCode.KO -> {
                    error(result)
                }
            }
        }

        fun getDevices(myaToken: AccountToken) : Array<Device>{
            val (_, response, result) =  Fuel.get(ApiMya.DEVICES.parse("0", "1000").getUrl())
                .header(
                    mapOf("Authorization" to "Bearer ${myaToken.token}")
                ).responseString()
            when (StateCode.parse(response.statusCode)){
                StateCode.OK -> {
                    try {
                        return Gson().fromJson(result.component1(), Array<Device>::class.java)
                    }catch (e: Exception){
                    }
                    return arrayOf()
                }
                StateCode.KO -> {
                    error(result)
                }
            }
        }

        fun getDeviceLogs(myaToken: AccountToken, myaDevice: Device) : Array<DeviceLog>
        {
            val (_, response, result) =  Fuel.get(ApiMya.DEVICELOGS.parse(myaDevice.imei).getUrl())
                .header(
                    mapOf("Authorization" to "Bearer ${myaToken.token}")
                ).responseString()
            when (StateCode.parse(response.statusCode)){
                StateCode.OK -> {
                    try {
                        return Gson().fromJson(result.component1(), Array<DeviceLog>::class.java)
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
    }
}