package com.auto.surelabs.tell.covid_19information

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.tell.covid_19information.model.registrasi.ResponseRegistrasi
import com.auto.surelabs.tell.covid_19information.network.NetworkModule
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            NetworkModule().getServices().doRegistrasi(
                username = username.text.toString(),
                password = password.text.toString(),
                nama = namaLengkap.text.toString()
            ).enqueue(object : retrofit2.Callback<ResponseRegistrasi> {
                override fun onFailure(call: Call<ResponseRegistrasi>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<ResponseRegistrasi>,
                    response: Response<ResponseRegistrasi>
                ) {
                    if (response.isSuccessful) {
//                        val code = response.body()?.code
//                        if(code == 200){
//                            Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
//                        }else{
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
//                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            response.errorBody().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
        }
    }
}
