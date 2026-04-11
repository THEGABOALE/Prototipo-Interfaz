package com.example.usuario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.usuario.ui.theme.UsuarioTheme
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.usuario.R


class PerfilUsuario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName = findViewById<TextView>(R.id.tvFullName)
        val tvDate = findViewById<TextView>(R.id.tvCreationDate)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnBack = findViewById<Button>(R.id.btnBack)


        tvName.text = "Jorge Cubillo"
        tvDate.text = "08/04/2026"

        // 3. Botón Editar: Cambia el nombre al hacer clic
        btnEdit.setOnClickListener {
            tvName.text = "Jorge Cubillo (Actualizado)"
            Toast.makeText(this, "Perfil Actualizado", Toast.LENGTH_SHORT).show()
        }
        btnEdit.setOnLongClickListener {
            Toast.makeText(this, "Mantén presionado para editar", Toast.LENGTH_SHORT).show()
            true
        }


        // 4. Botón Regresar: Cierra la pantalla
        btnBack.setOnClickListener {
            finish()
        }
    }
}