package com.auto.surelabs.tell.covid_19information

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.tell.covid_19information.model.login.Data
import com.auto.surelabs.tell.covid_19information.model.login.ResponseLogin
import com.auto.surelabs.tell.covid_19information.model.registrasi.ResponseRegistrasi
import com.auto.surelabs.tell.covid_19information.network.NetworkModule
import kotlinx.android.synthetic.main.activity_edit_profile.*
import retrofit2.Call
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //get username from sharedPreferences
        pref = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE)
        pref.apply {
            //request detail to server
            val usernamePref = this.getString(Constant.USERNAME, "")
            if (usernamePref != "")
                NetworkModule().getServices().doGetDetail(usernamePref)
                    .enqueue(object : retrofit2.Callback<ResponseLogin> {
                        override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                        }

                        override fun onResponse(
                            call: Call<ResponseLogin>,
                            response: Response<ResponseLogin>
                        ) {
                            val code = response.body()?.code
                            if (code == 200) {
                                val data = response.body()?.data
                                namaLengkap.setText(data?.nama)
                                username.setText(data?.username)
                            } else {
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    })
        }

        simpanData.setOnClickListener {
            //make a layout for alertDialog
            val viewAlertDialog = LinearLayout(this@EditProfileActivity)
            viewAlertDialog.setPadding(20, 0, 20, 0)

            val editTextPassword = EditText(this@EditProfileActivity)
            editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_VARIATION_PASSWORD
            editTextPassword.hint = "entry password here"
            editTextPassword.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            viewAlertDialog.addView(editTextPassword)

            AlertDialog.Builder(this@EditProfileActivity)
                .setMessage("Enter your old password")
                .setView(viewAlertDialog)
                .setPositiveButton("Okay") { _, _ ->
                    if (editTextPassword.text.toString().isNotEmpty()) {
                        val data = Data(
                            username = username.text.toString(),
                            password = password.text.toString(),
                            nama = namaLengkap.text.toString()
                        )

                        //doUpdate
                        NetworkModule().getServices().doUpdateAccount(data)
                            .enqueue(object : retrofit2.Callback<ResponseRegistrasi> {
                                override fun onFailure(
                                    call: Call<ResponseRegistrasi>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@EditProfileActivity,
                                        t.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onResponse(
                                    call: Call<ResponseRegistrasi>,
                                    response: Response<ResponseRegistrasi>
                                ) {
                                    val code = response.body()?.code
                                    if (code == 200) {
                                        pref.edit().apply {
                                            this.putString(
                                                Constant.NAME,
                                                namaLengkap.text.toString()
                                            )
                                            this.putString(
                                                Constant.USERNAME,
                                                username.text.toString()
                                            )
                                        }.apply()
                                        Toast.makeText(
                                            this@EditProfileActivity,
                                            response.body()?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@EditProfileActivity,
                                            response.body()?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            })
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        }
    }
}
