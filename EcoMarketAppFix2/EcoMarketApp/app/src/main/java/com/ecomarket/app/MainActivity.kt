package com.ecomarket.app

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // (1)
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imgProfile?.setImageURI(uri)
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("photoUri", uri.toString()).apply()
        }
    }

    // (2)
    private var imgProfile: ImageView? = null

    // (3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // (4)
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val email = prefs.getString("email", "") ?: ""

        // (5)
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        imgProfile = findViewById(R.id.imgProfile)
        val btnChangePhoto = findViewById<Button>(R.id.btnChangePhoto)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // (6)
        tvGreeting.text = "Hola, bienvenido de nuevo " + email + ", gracias por elegir EcoMarket 🌱"

        // (7)
        val savedPhoto = prefs.getString("photoUri", null)
        if (!savedPhoto.isNullOrEmpty()) {
            imgProfile?.setImageURI(Uri.parse(savedPhoto))
        }

        // (8)
        btnChangePhoto.setOnClickListener {
            pickImage.launch("image/*")
        }

        // (9)
        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            finish()
        }
    }
}