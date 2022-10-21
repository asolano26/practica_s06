package com.practica_s06

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.practica_s06.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth


        binding.btRegister.setOnClickListener { haceRegistro() }
        binding.btLogin.setOnClickListener { haceLogin() }
    }

    private fun haceLogin() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //Funcion para crear usuario en firebase (correo/contraseña) -> llamada asíncrona
        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {
                    Log.d("Autenticando", "usuario logeado")
                    user = auth.currentUser //Recupero info del usuario creado
                } else {
                    Log.d("Autenticando", "error autenticando el usuario")

                }
                actualiza(user)
            }
    }

    private fun haceRegistro() {

        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        //Funcion para crear usuario en firebase (correo/contraseña) -> llamada asíncrona
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                var user: FirebaseUser? = null
                if (task.isSuccessful) {
                    Log.d("Autenticando", "usuario creado")
                    user = auth.currentUser //Recupero info del usuario creado
                } else {
                    Log.d("Autenticando", "error creando el usuario")

                }
                actualiza(user)
            }
    }

    private fun actualiza(user: FirebaseUser?) {
        //Si hay un usuario definido se pasa la pantalla principal (activity)
        if (user != null) {
            //se pasa a la otra pantalla
            val intent = Intent(this, Central::class.java)
            startActivity(intent)
        }
    }
}