package com.example.firebasenote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    lateinit var edit_text_username : TextInputEditText
    lateinit var edit_text_email : TextInputEditText
    lateinit var edi_text_password : TextInputEditText
    lateinit var edit_text_confirm_password : TextInputEditText
    lateinit var button_signup : Button
    lateinit var text_view_login : TextView
    lateinit var progress_bar : ProgressBar
    //
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        init()

        text_view_login.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            onDestroy()
        }


        button_signup.setOnClickListener{
                if ((edit_text_username.text.toString() == "") || (edit_text_email.text.toString() == "") || (edi_text_password.text.toString() == "") || (edit_text_confirm_password.text.toString() == "")){
                    Toast.makeText(this,"please fill values!!!", Toast.LENGTH_SHORT).show()
                }
                else{
                    if (edi_text_password.text.toString() == edit_text_confirm_password.text.toString() ){
                        progress_bar.visibility = View.VISIBLE
                        button_signup.visibility = View.INVISIBLE
                        val email=edit_text_email.text.toString()
                        val password=edi_text_password.text.toString()
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                intent.putExtra("email",email)
                                intent.putExtra("password",password)
                                startActivity(Intent(this,LoginActivity::class.java))
                                finish()
                                Toast.makeText(this,"Sign up Successful",Toast.LENGTH_SHORT).show()

                            }
                        }.addOnFailureListener { exception ->
                            Toast.makeText(this,"Sign up error !!!",Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                            progress_bar.visibility = View.INVISIBLE
                            button_signup.visibility = View.VISIBLE
                        }
                    }
                    else{
                        Toast.makeText(this,"password does not match!",Toast.LENGTH_SHORT).show()
                    }

                }
        }


    }


    fun init(){
        edit_text_username = findViewById(R.id.edit_text_user_name_signup)
        edit_text_email = findViewById(R.id.edit_text_email_signup)
        edi_text_password = findViewById(R.id.edit_text_password_signup)
        edit_text_confirm_password = findViewById(R.id.edit_text_confirm_password_signup)
        button_signup = findViewById(R.id.button_signup)
        text_view_login = findViewById(R.id.textview_login_signup)
        progress_bar = findViewById(R.id.progress_bar_signup)
        //
        firebaseAuth = FirebaseAuth.getInstance()
    }
}