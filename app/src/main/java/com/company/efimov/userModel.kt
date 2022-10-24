package com.company.efimov

class userModel(val phone:String?="",val pass:String?="",val fio:String?="",val address:String?="",val appartament:String?="", val status:String?="") {

    companion object{
        var currentuser:userModel?=null
    }
}