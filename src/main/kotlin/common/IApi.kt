package common

interface IApi{
    var result: String
    fun parse(vararg  params: String) : IApi{
        for (i in 0 until params.size) {
            result = result.replace("%$i", params[i])
        }
        return this
    }
    fun getUrl() : String{
        return result
    }
}