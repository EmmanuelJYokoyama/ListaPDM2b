package com.example.manonaosei

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG: String = "APP"
    private lateinit var email: EditText
    private lateinit var senha : EditText
    private lateinit var btn_reset: Button
    private lateinit var btn_login : Button
    private lateinit var btn_delete : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        btn_reset = findViewById(R.id.btn_Reset)
        btn_login = findViewById(R.id.btn_Log)
        btn_delete = findViewById(R.id.btn_Del)
        email = findViewById(R.id.email)
        senha = findViewById(R.id.password)

        login_cadastro()
        resetPass()
        deleteUser()


    }



    fun Usuario_create(email:String, senha:String){
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    var usuario :FirebaseUser? = auth.currentUser
                    var idUsuario: String = usuario!!.uid
                    Toast.makeText(baseContext, "Usuario Criado!", Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(baseContext, "Falha na criação!", Toast.LENGTH_LONG).show()

                }
            }

    }

    fun login_cadastro(){
        btn_login.setOnClickListener{
            var email: String = email.text.toString()
            var senha: String = senha.text.toString()

            verifyUser(email, senha)

        }

    }

    fun resetPass(){
        btn_reset.setOnClickListener {
            var senha: String = senha.text.toString()
            val usuario = FirebaseAuth.getInstance().currentUser
            usuario?.updatePassword(senha)?.addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Password reseted sucessfully",Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(baseContext, "/-/-/-/-",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun deleteUser(){
        val usuario = FirebaseAuth.getInstance().currentUser
        btn_delete.setOnClickListener {
            usuario?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Delete Sucessful", Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    fun verifyUser(email: String, pass: String){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                val usuario = auth.currentUser
                if(usuario != null){
                    if(usuario.isEmailVerified){
                        usuario.sendEmailVerification().addOnCompleteListener{task ->
                            if(task.isSuccessful){
                                Log.d(TAG, "Email sent!")
                            }
                        }
                    }
                }
                Toast.makeText(baseContext, "Login Sucessful",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(baseContext, "Login error, sign up in process.",Toast.LENGTH_LONG).show()
                Usuario_create(email,pass)
            }

        }


    }
}
