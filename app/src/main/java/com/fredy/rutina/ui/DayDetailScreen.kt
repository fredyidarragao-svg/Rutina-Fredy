package com.fredy.rutina.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fredy.rutina.AppViewModel
import com.fredy.rutina.data.PlanItem
import com.fredy.rutina.data.RoutineData
import com.fredy.rutina.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailScreen(
    viewModel: AppViewModel,
    dayId: String,
    onBack: () -> Unit
) {
    val day = RoutineData.weeklyPlan.find { it.id == dayId } ?: return
    val altToggles by viewModel.altToggles.collectAsState()
    val usaAlt = altToggles[dayId] == true
    val tracking by viewModel.todayTracking.collectAsState()
    var mostrarTracking by remember { mutableStateOf(false) }

    val plan = viewModel.activePlanFor(dayId)
    val titulo = viewModel.activeTitleFor(dayId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(day.dia) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        containerColor = FondoOscuro
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextoPrincipal)
                Text(day.duracion + " • " + day.intensidad, color = TextoSecundario, style = MaterialTheme.typography.bodyMedium)
            }

            if (day.esDeporte) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Superficie)
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Rutina alternativa", color = TextoPrincipal, fontWeight = FontWeight.Bold)
                            Text("Actívala si no hay ${day.deporte} hoy", color = TextoSecundario, style = MaterialTheme.typography.bodySmall)
                        }
                        Switch(checked = usaAlt, onCheckedChange = { viewModel.toggleAlt(dayId) })
                    }
                }
            }

            items(plan) { item -> ExerciseRow(item) }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Superficie)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Hecho hoy", color = TextoPrincipal, fontWeight = FontWeight.Bold)
                    Switch(
                        checked = tracking?.hechoHoy == true,
                        onCheckedChange = { viewModel.marcarHechoHoy(dayId, it) }
                    )
                }
            }

            item {
                Button(
                    onClick = { mostrarTracking = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulAccion)
                ) {
                    Icon(Icons.Filled.MonitorHeart, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Registrar métricas del reloj")
                }
            }

            tracking?.let { t ->
                if (t.fcAvg != null || t.pasos != null || t.calorias != null) {
                    item { MetricsSummary(t.fcAvg, t.fcMax, t.calorias, t.tiempoMin, t.distanciaKm, t.suenoHoras, t.pasos, t.fuenteAuto) }
                }
            }
        }
    }

    if (mostrarTracking) {
        TrackingSheet(
            viewModel = viewModel,
            dayId = dayId,
            onDismiss = { mostrarTracking = false }
        )
    }
}

@Composable
private fun ExerciseRow(item: PlanItem) {
    val ejercicio = RoutineData.exerciseById(item.idLib)
    var expandido by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Superficie)
            .clickableToggle { expandido = !expandido }
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(ejercicio?.nombre ?: item.idLib, color = TextoPrincipal, fontWeight = FontWeight.Bold)
                Text(item.tecnica, color = TextoSecundario, style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(item.series, color = AzulAccion, fontWeight = FontWeight.Bold)
                Text("Descanso ${item.descanso}", color = TextoSecundario, style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(Modifier.height(6.dp))
        Text("Rodilla: ${item.rodilla}", color = VerdeOk, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)

        if (expandido && ejercicio != null) {
            Spacer(Modifier.height(10.dp))
            Text(ejercicio.musculos, color = TextoSecundario, style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(6.dp))
            ejercicio.bullets.forEach { b ->
                Text("• $b", color = TextoPrincipal, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.height(6.dp))
            Text(ejercicio.tecnica, color = TextoSecundario, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun MetricsSummary(
    fcAvg: Int?, fcMax: Int?, calorias: Int?, tiempoMin: Int?,
    distanciaKm: Double?, suenoHoras: Double?, pasos: Int?, fuenteAuto: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Superficie)
            .padding(14.dp)
    ) {
        Text(
            if (fuenteAuto) "Desde el reloj (Health Connect)" else "Registro manual",
            color = TextoSecundario, style = MaterialTheme.typography.labelSmall
        )
        Spacer(Modifier.height(6.dp))
        val filas = listOfNotNull(
            fcAvg?.let { "FC promedio: $it" },
            fcMax?.let { "FC máx: $it" },
            calorias?.let { "Calorías: $it" },
            tiempoMin?.let { "Tiempo: $it min" },
            distanciaKm?.let { "Distancia: $it km" },
            suenoHoras?.let { "Sueño: $it h" },
            pasos?.let { "Pasos: $it" }
        )
        filas.forEach { Text(it, color = TextoPrincipal, style = MaterialTheme.typography.bodyMedium) }
    }
}

private fun Modifier.clickableToggle(onClick: () -> Unit): Modifier =
    this.then(androidx.compose.foundation.clickable(onClick = onClick))
