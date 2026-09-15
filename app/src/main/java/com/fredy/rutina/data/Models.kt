package com.fredy.rutina.data

/** Un ejercicio de la biblioteca, con toda su técnica y nota de rodilla/menisco. */
data class Exercise(
    val id: String,
    val nombre: String,
    val subtitulo: String,
    val musculos: String,
    val bullets: List<String>,
    val tecnica: String,
    val rodilla: String,
    val foco: String,
    val seriesRecom: String,
    val categoria: String,
    val usaKettlebell: Boolean
)

/** Una entrada dentro del plan de un día: referencia a un ejercicio + series/descanso de ese día puntual. */
data class PlanItem(
    val idLib: String,
    val series: String,
    val descanso: String,
    val tecnica: String,
    val rodilla: String
)

/** El plan de un día de la semana. */
data class DayPlan(
    val id: String,
    val dia: String,
    val titulo: String,
    val subtitulo: String,
    val duracion: String,
    val intensidad: String,
    val deporte: String?,
    val esDeporte: Boolean,
    val planNormal: List<PlanItem>,
    val planAlternativoId: String
)

/** Rutina alternativa para un día de deporte (ej: si no hay patinaje ese día). */
data class AltRoutine(
    val titulo: String,
    val subtitulo: String,
    val ejercicios: List<PlanItem>
)

/** Rotación de combinación bíceps/tríceps por semana del mes (1 a 4). */
data class BicepsTricepsCombo(
    val triceps: List<String>,
    val biceps: List<String>,
    val label: String
)
