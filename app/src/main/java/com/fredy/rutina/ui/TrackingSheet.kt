package com.fredy.rutina.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.fredy.rutina.AppViewModel
import com.fredy.rutina.health.HealthConnectAvailability
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingSheet(
    viewModel: AppViewModel,
    dayId: String,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val availability by viewModel.healthAvailability.collectAsState()
    val hasPermissions by viewModel.hasHealthPermissions.collectAsState()
    var cargando by remember { mutableStateOf(false) }
    var fuenteAuto by remember { mutableStateOf(false) }

    var fcAvg by remember { mutableStateOf("") }
    var fcMax by remember { mutableStateOf("") }
    var calorias by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var sueno by remember { mutableStateOf("") }
    var pasos by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = viewModel.healthConnect.permissionRequestContract()
    ) {
        viewModel.refreshHealthPermissions()
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Registrar métricas de hoy", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))

            when {
                availability != HealthConnectAvailability.DISPONIBLE -> {
                    Text(
                        "Health Connect no está disponible en este teléfono. Instálalo desde Play Store " +
                            "para traer aquí los datos que tu Amazfit sincroniza vía Zepp/Mi Fitness.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                !hasPermissions -> {
                    Button(onClick = { permissionLauncher.launch(viewModel.healthConnect.permissions) }) {
                        Icon(Icons.Filled.Watch, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Conectar con el reloj (Health Connect)")
                    }
                }
                else -> {
                    Button(
                        onClick = {
                            cargando = true
                            scope.launch {
                                val m = viewModel.leerMetricasDeHoyDesdeReloj()
                                fcAvg = m.fcAvg?.toString() ?: fcAvg
                                fcMax = m.fcMax?.toString() ?: fcMax
                                calorias = m.calorias?.toString() ?: calorias
                                tiempo = m.minutosEjercicio?.toString() ?: tiempo
                                sueno = m.suenoHoras?.toString() ?: sueno
                                pasos = m.pasos?.toString() ?: pasos
                                fuenteAuto = true
                                cargando = false
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Watch, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (cargando) "Leyendo del reloj..." else "Traer datos del reloj")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = fcAvg, onValueChange = { fcAvg = it; fuenteAuto = false }, label = { Text("FC promedio") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = fcMax, onValueChange = { fcMax = it; fuenteAuto = false }, label = { Text("FC máxima") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = calorias, onValueChange = { calorias = it; fuenteAuto = false }, label = { Text("Calorías") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = tiempo, onValueChange = { tiempo = it; fuenteAuto = false }, label = { Text("Tiempo (min)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = distancia, onValueChange = { distancia = it; fuenteAuto = false }, label = { Text("Distancia (km)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = sueno, onValueChange = { sueno = it; fuenteAuto = false }, label = { Text("Sueño (horas)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = pasos, onValueChange = { pasos = it; fuenteAuto = false }, label = { Text("Pasos") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.guardarMetricas(
                        dayId = dayId,
                        fcAvg = fcAvg.toIntOrNull(),
                        fcMax = fcMax.toIntOrNull(),
                        calorias = calorias.toIntOrNull(),
                        tiempoMin = tiempo.toIntOrNull(),
                        distanciaKm = distancia.toDoubleOrNull(),
                        suenoHoras = sueno.toDoubleOrNull(),
                        pasos = pasos.toIntOrNull(),
                        fuenteAuto = fuenteAuto
                    )
                    onDismiss()
                }
            ) {
                Text("Guardar")
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
