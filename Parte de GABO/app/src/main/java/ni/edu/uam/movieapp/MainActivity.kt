package ni.edu.uam.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ni.edu.uam.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                MovieApp()
            }
        }
    }
}

data class Pelicula(
    val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String,
    @DrawableRes val imagenRes: Int
)

enum class Pantalla {
    LISTA,
    DETALLE,
    FAVORITOS
}

@Composable
fun MovieApp() {
    val peliculas = listOf(
        Pelicula(
            id = 1,
            titulo = "Interstellar",
            genero = "Ciencia ficción",
            anio = 2014,
            descripcion = "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio para salvar a la humanidad.",
            imagenRes = R.drawable.interstellar
        ),
        Pelicula(
            id = 2,
            titulo = "Inception",
            genero = "Acción / Ciencia ficción",
            anio = 2010,
            descripcion = "Un ladrón especializado en infiltrarse en los sueños recibe la tarea de implantar una idea en la mente de una persona.",
            imagenRes = R.drawable.inception
        ),
        Pelicula(
            id = 3,
            titulo = "The Batman",
            genero = "Acción / Drama",
            anio = 2022,
            descripcion = "Batman investiga una serie de crímenes en Gotham mientras descubre una red de corrupción.",
            imagenRes = R.drawable.batman
        )
    )

    var pantallaActual by remember { mutableStateOf(Pantalla.LISTA) }
    var peliculaSeleccionada by remember { mutableStateOf<Pelicula?>(null) }
    val favoritos = remember { mutableStateListOf<Pelicula>() }

    when (pantallaActual) {
        Pantalla.LISTA -> {
            ListaPeliculasScreen(
                peliculas = peliculas,
                onPeliculaClick = { pelicula ->
                    peliculaSeleccionada = pelicula
                    pantallaActual = Pantalla.DETALLE
                },
                onIrFavoritos = {
                    pantallaActual = Pantalla.FAVORITOS
                }
            )
        }

        Pantalla.DETALLE -> {
            peliculaSeleccionada?.let { pelicula ->
                DetallePeliculaScreen(
                    pelicula = pelicula,
                    esFavorita = favoritos.any { it.id == pelicula.id },
                    onVolver = {
                        pantallaActual = Pantalla.LISTA
                    },
                    onToggleFavorito = {
                        val yaExiste = favoritos.any { it.id == pelicula.id }
                        if (yaExiste) {
                            favoritos.removeAll { it.id == pelicula.id }
                        } else {
                            favoritos.add(pelicula)
                        }
                    },
                    onIrFavoritos = {
                        pantallaActual = Pantalla.FAVORITOS
                    }
                )
            }
        }

        Pantalla.FAVORITOS -> {
            FavoritosScreen(
                favoritos = favoritos,
                onVolverInicio = {
                    pantallaActual = Pantalla.LISTA
                },
                onPeliculaClick = { pelicula ->
                    peliculaSeleccionada = pelicula
                    pantallaActual = Pantalla.DETALLE
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPeliculasScreen(
    peliculas: List<Pelicula>,
    onPeliculaClick: (Pelicula) -> Unit,
    onIrFavoritos: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Películas") },
                actions = {
                    IconButton(onClick = onIrFavoritos) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Ir a favoritos"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(peliculas) { pelicula ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPeliculaClick(pelicula) },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = pelicula.imagenRes),
                            contentDescription = pelicula.titulo,
                            modifier = Modifier
                                .size(width = 90.dp, height = 130.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = pelicula.titulo,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Género: ${pelicula.genero}")
                            Text(text = "Año: ${pelicula.anio}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Toca para ver detalles",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePeliculaScreen(
    pelicula: Pelicula,
    esFavorita: Boolean,
    onVolver: () -> Unit,
    onToggleFavorito: () -> Unit,
    onIrFavoritos: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onIrFavoritos) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = pelicula.imagenRes),
                contentDescription = pelicula.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pelicula.titulo,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Género: ${pelicula.genero}")
            Text(text = "Año: ${pelicula.anio}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sinopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = pelicula.descripcion)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onToggleFavorito,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (esFavorita) "Quitar de favoritos"
                    else "Agregar a favoritos"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (esFavorita) {
                Text(
                    text = "Esta película está en tu lista de favoritos.",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    favoritos: List<Pelicula>,
    onVolverInicio: () -> Unit,
    onPeliculaClick: (Pelicula) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis favoritos") },
                navigationIcon = {
                    IconButton(onClick = onVolverInicio) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Volver al inicio"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (favoritos.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Sin favoritos",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("No has agregado películas a favoritos.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos) { pelicula ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPeliculaClick(pelicula) },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = pelicula.imagenRes),
                                contentDescription = pelicula.titulo,
                                modifier = Modifier
                                    .size(width = 80.dp, height = 110.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = pelicula.titulo,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = pelicula.genero)
                                Text(text = "Año: ${pelicula.anio}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieAppPreview() {
    MovieAppTheme {
        MovieApp()
    }
}