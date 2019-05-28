package common

enum class StateCode(val result: Boolean){
    OK(true), KO(false);

    companion object{
        fun parse(code: Int) : StateCode{
            if (code == 200)
                return OK
            else
                return KO
        }
    }

}