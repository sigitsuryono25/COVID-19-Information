package com.auto.surelabs.tell.covid_19information.model.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Suppress("SpellCheckingInspection")
data class Data(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("nama")
    val nama: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("terdaftar_pada")
    val terdaftar_pada: String? = null
) : Serializable