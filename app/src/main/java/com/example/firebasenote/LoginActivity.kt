package com.example.firebasenote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class LoginActivity : AppCompatActivity() {

    lateinit var edit_text_email: TextInputEditText
    lateinit var edit_text_password: TextInputEditText
    lateinit var check_box_remember_me: CheckBox
    lateinit var text_view_forgot_password: TextView
    lateinit var button_login: Button
    lateinit var text_view_sign_up: TextView
    lateinit var progress_bar: ProgressBar

    //
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        button_login.setOnClickListener {


            if ((edit_text_email.text.toString() == "") || (edit_text_password.text.toString() == "")) {
                Toast.makeText(this, "please fill values!!!", Toast.LENGTH_SHORT).show()

            } else {
                val email = edit_text_email.text.toString()
                val pass = edit_text_password.text.toString()
                progress_bar.visibility = View.VISIBLE
                button_login.visibility = View.INVISIBLE
                firebaseAuth.signInWithEmailAndPassword(
                    email, pass
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("checked", check_box_remember_me.isChecked)
                        Toast.makeText(
                            this,
                            check_box_remember_me.isChecked.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        progress_bar.visibility = View.INVISIBLE
                        button_login.visibility = View.VISIBLE
                        Log.e("erroRegister", it.exception.toString())
                    }
                }

            }
        }



        text_view_sign_up.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun init() {
        edit_text_email = findViewById(R.id.edit_text_email_login_activity)
        edit_text_password = findViewById(R.id.edit_text_password_login_activity)
        check_box_remember_me = findViewById(R.id.check_box_login_activity)
        text_view_forgot_password = findViewById(R.id.text_view_forgot_password_login_activity)
        button_login = findViewById(R.id.button_login_login_activity)
        text_view_sign_up = findViewById(R.id.textview_sign_up_now_login_activity)
        progress_bar = findViewById(R.id.progress_bar_login_activity)
        firebaseAuth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}