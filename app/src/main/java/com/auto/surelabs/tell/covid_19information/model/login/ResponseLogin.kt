package com.auto.surelabs.tell.covid_19information.model.login

import com.google.gson.annotations.SerializedName


data class ResponseLogin(

    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("code")
    val code: Int
)