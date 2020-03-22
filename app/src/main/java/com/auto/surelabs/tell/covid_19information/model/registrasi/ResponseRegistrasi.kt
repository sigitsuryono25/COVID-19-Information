package com.auto.surelabs.tell.covid_19information.model.registrasi

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection")
data class ResponseRegistrasi(

    @SerializedName("message")
    val message: String,

    @SerializedName("code")
    val code: Int
)