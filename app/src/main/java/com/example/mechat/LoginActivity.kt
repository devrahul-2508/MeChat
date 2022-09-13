package com.example.mechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_login.*
import android.text.Spannable

import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView

import android.widget.TextView.BufferType
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*


class LoginActivity : AppCompatActivity() {

    private var TAG="12134"
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmail=findViewById<TextView>(R.id.edt_email)
        val edtPassword=findViewById<TextView>(R.id.edt_password)
        auth= FirebaseAuth.getInstance()


        SignUp.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        btnLogIn.setOnClickListener {
           val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)



                } else {

                           Toast.makeText(this,"User Not Found",Toast.LENGTH_SHORT
                           ).show()
                }
            }
    }
}