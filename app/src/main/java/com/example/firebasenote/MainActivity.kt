package com.example.firebasenote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var button_log_out: Button
    //
    var is_checked by Delegates.notNull<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        CheckLogin()

        button_log_out.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
            FirebaseAuth.getInstance().signOut()
        }

    }

    fun init() {
        button_log_out = findViewById(R.id.button_log_out_main_activity)
        //
        var intent = intent
        is_checked = intent.getBooleanExtra("checked",false)



    }


    fun CheckLogin() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onDestroy()
    }

    override fun onStop() {
        super.onStop()
        if(!is_checked){
            FirebaseAuth.getInstance().signOut()
        }
    }


}