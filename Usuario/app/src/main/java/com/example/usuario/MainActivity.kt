package com.example.usuario
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imgProfile = findViewById<ImageView>(R.id.imgProfile)
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnExit = findViewById<Button>(R.id.btnExit)


        etFullName.setText("Jorge Cubillo")
        etEmail.setText("jorge.cubillo@uam.edu.ni")


        imgProfile.setOnClickListener {
            Toast.makeText(this, "Función para cambiar foto (Editable)", Toast.LENGTH_SHORT).show()
        }


        btnSave.setOnClickListener {
            val name = etFullName.text.toString()
            val pass = etPassword.text.toString()

            if (name.isNotEmpty() && pass.length >= 8) {
                Toast.makeText(this, "Datos de $name guardados con éxito", Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this, "Verifique el nombre y que la contraseña tenga 8 caracteres", Toast.LENGTH_LONG).show()
            }
        }


        btnExit.setOnClickListener {
            finish()
        }
    }
}