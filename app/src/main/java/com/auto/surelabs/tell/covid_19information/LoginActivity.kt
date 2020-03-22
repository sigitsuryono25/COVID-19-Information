package com.auto.surelabs.tell.covid_19information

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.tell.covid_19information.model.login.ResponseLogin
import com.auto.surelabs.tell.covid_19information.network.NetworkModule
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    @Suppress("SpellCheckingInspection")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*
        * Like a Facebook Apps and more,
        * login cukup 1 kali aja, waktu apps dibuka udah nggak pake login lagi.
        * Ini fungsi pengecekan didalam sharedpreferencesnya
        * apakah ada KEY dengan nama username didalamnya
        * Kalo ada, akhiri LoginActivity dan pindah ke ProfileActivity
        * */

        //start checking from here
        pref = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE)
        if (pref.contains(Constant.USERNAME)) {
            finish()
            startActivity(
                Intent(
                    this@LoginActivity,
                    ProfileActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        //end checking in here

        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            NetworkModule().getServices().doLogin(
                username = username.text.toString(),
                password = password.text.toString()
            ).enqueue(object : retrofit2.Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    /*Kemaren bertanya kenapa harus diberi blok if code == 200
                    * alasanya kita harus tau apakah username dan password yang kita kirimkan
                    * itu ada atau nggak didalam database. Sesuai dengan webservicenya kemaren
                    * kalo code 200 berarti request yang kita lakukan ditemukan atau data yang
                    * kita minta itu ada. Selain code 200, berarti data tidak ada.
                    *
                    * didalam blok if code == 200 kita bisa atur apa yang dilakukan sistem ketika
                    * username dan password yang kita inputkan tadi ada didatabase, Contoh paling
                    * sederhana ada melakukan toast ketika username dan password ditemukan. Tapi pada
                    * versi ini, toast sudah aku hapus dan aku gantikan dengan pengaturan data ke
                    * sharedpreferences.
                    *
                    *
                    * */
                    val code = response.body()?.code
                    if (code == 200) {
                        val data = response.body()?.data

                        //set value to sharedPreferences
                        //sharedPreferences like a session on website apps
                        pref.edit().apply {
                            putString(Constant.USERNAME, data?.username)
                            putString(Constant.NAME, data?.nama)
                        }.apply()

                        finish()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                ProfileActivity::class.java
                            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.errorBody()?.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
        }

    }
}
