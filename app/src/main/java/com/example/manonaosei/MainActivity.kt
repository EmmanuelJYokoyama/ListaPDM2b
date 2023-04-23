package com.example.manonaosei

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var TAG: String = "APP"
    private lateinit var email: EditText
    private lateinit var senha : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        var btn: Button = findViewById(R.id.btn)
        email = findViewById(R.id.email)
        senha = findViewById(R.id.password)



    }

    fun VerificarUsuario(email:String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Logado com sucesso.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Erro Login.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun UserCreate(email: String, pass: String){
        var text: TextView = findViewById(R.id.txtvw);

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    var usuario :FirebaseUser? = auth.currentUser
                    var idUsuario: String = usuario!!.uid
                    text.text = "Usuario Criado"
                }else{
                    text.text = "Falha"
                }
            }
    }
}