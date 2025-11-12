package com.ecomarket.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    // (1)
    private lateinit var prefs: SharedPreferences

    // (2)
    private val usuariosValidos = mapOf(
        "eco1@ecomarket.cl" to "1234",
        "eco2@ecomarket.cl" to "abcd",
        "admin@ecomarket.cl" to "admin"
    )

    // (3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // (4)
        prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        // (5)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // (6)
        etEmail.setText(prefs.getString("email", ""))

        // (7)
        btnLogin.setOnClickListener {
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass = etPassword.text?.toString()?.trim().orEmpty()

            // (8)
            if (usuariosValidos[email] == pass) {
                prefs.edit().putString("email", email).apply()
                // (9)
                startActivity(Intent(this, MainActivity::class.java))
                // (10)
                finish()
            } else {
                // (11)
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}