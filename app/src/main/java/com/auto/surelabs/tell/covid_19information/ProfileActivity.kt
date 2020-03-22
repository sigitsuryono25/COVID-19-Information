package com.auto.surelabs.tell.covid_19information

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : AppCompatActivity() {

    @Suppress("SpellCheckingInspection")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)

        //get value from sharedPreferences
        val prefereces = getSharedPreferences(Constant.PREFERENCES_NAME, Context.MODE_PRIVATE)
        prefereces.apply {
            namaLengkap.text = this.getString(Constant.NAME, "")
            username.text = this.getString(Constant.USERNAME, "")
        }
        /*Catatan
        *
        * pengambilan data pada sharedpreferences itu tergantung dengan tipe data waktu ngeset ya
        * kalo tipenya itu integer waktu nge-set berarti waktu ambil juga harus integer
        *
        * example
        *
        * set value ke sharedpreferences dengan tipe int
        * putInt("nilai1", 100)
        *
        * get value dengan tipe int
        * getInt("nilai1", 0)
        *
        * setiap pengambilan value harus mempunyai nilai default.
        * contoh kalo key nilai1 =null maka yang dipanggil adalah nilai default nya, yaitu 0
        *
        *
        *
        * */

        editProfile.setOnClickListener {
            val editProfileIntent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(editProfileIntent)
        }
    }
}
