package com.example.mechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG="1234"
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth= FirebaseAuth.getInstance()
        val edtName=findViewById<TextView>(R.id.edt_name)
        val edtEmail=findViewById<TextView>(R.id.edt_email)
        val edtPassword=findViewById<TextView>(R.id.edt_password)

        LogIn.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val name=edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(name,email,password)

        }

    }

    private fun signUp(name:String,email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                        addUserToDatabase(name,email, auth.currentUser?.uid)
                   val intent=Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)



                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Some Error Occured",Toast.LENGTH_SHORT).show()


                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
               mDbRef =FirebaseDatabase.getInstance().getReference()

        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(name, email, uid))
        }
        
    }
}