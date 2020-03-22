package com.auto.surelabs.tell.covid_19information.network

import com.auto.surelabs.tell.covid_19information.model.login.ResponseLogin
import com.auto.surelabs.tell.covid_19information.model.registrasi.ResponseRegistrasi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@Suppress("SpellCheckingInspection")
class NetworkModule {

    private fun getOkHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .baseUrl(BASE_URL)
            .build()
    }

    fun getServices(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }


    interface ApiService {
        @FormUrlEncoded
        @POST("registrasi")
        fun doRegistrasi(
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("nama") nama: String?
        ): retrofit2.Call<ResponseRegistrasi>

        @FormUrlEncoded
        @POST("login")
        fun doLogin(
            @Field("username") username: String?,
            @Field("password") password: String?
        ): retrofit2.Call<ResponseLogin>

        @FormUrlEncoded
        @POST("get_detail_by_email")
        fun doGetDetail(@Field("username") username: String?): retrofit2.Call<ResponseLogin>

        @FormUrlEncoded
        @POST("update_profile")
        fun doUpdateAccount(
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("nama") nama: String?
        ): retrofit2.Call<ResponseRegistrasi>

    }

    companion object {
        private const val BASE_URL = "http://192.168.57.1/covid-backend/index.php/hacked/"
    }
}