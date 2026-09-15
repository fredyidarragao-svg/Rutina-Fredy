package com.fredy.rutina

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.rutina.data.AppDatabase
import com.fredy.rutina.data.PlanItem
import com.fredy.rutina.data.RoutineData
import com.fredy.rutina.data.TrackingEntity
import com.fredy.rutina.health.DailyHealthMetrics
import com.fredy.rutina.health.HealthConnectAvailability
import com.fredy.rutina.health.HealthConnectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val dao = db.trackingDao()
    val healthConnect = HealthConnectManager(application)

    private val fechaFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // id de día -> ¿usando rutina ALT?
    private val _altToggles = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val altToggles: StateFlow<Map<String, Boolean>> = _altToggles.asStateFlow()

    private val _libraryFilter = MutableStateFlow("TODOS")
    val libraryFilter: StateFlow<String> = _libraryFilter.asStateFlow()

    private val _todayTracking = MutableStateFlow<TrackingEntity?>(null)
    val todayTracking: StateFlow<TrackingEntity?> = _todayTracking.asStateFlow()

    private val _healthAvailability = MutableStateFlow(HealthConnectAvailability.NO_DISPONIBLE)
    val healthAvailability: StateFlow<HealthConnectAvailability> = _healthAvailability.asStateFlow()

    private val _hasHealthPermissions = MutableStateFlow(false)
    val hasHealthPermissions: StateFlow<Boolean> = _hasHealthPermissions.asStateFlow()

    /** true si la FC promedio de las últimas sesiones registradas viene alta (posible fatiga). */
    private val _fatigaAlta = MutableStateFlow(false)
    val fatigaAlta: StateFlow<Boolean> = _fatigaAlta.asStateFlow()

    /** Semana de rotación 1..4 para pecho/bíceps/tríceps/core, basada en la semana del año. */
    val rotationWeek: Int
        get() {
            val weekOfYear = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear())
            return ((weekOfYear - 1) % 4) + 1
        }

    init {
        _healthAvailability.value = healthConnect.availability()
        loadTodayTracking()
        evaluarFatiga()
        viewModelScope.launch {
            _hasHealthPermissions.value = runCatching { healthConnect.hasAllPermissions() }.getOrDefault(false)
        }
    }

    fun toggleAlt(dayId: String) {
        _altToggles.value = _altToggles.value.toMutableMap().apply {
            this[dayId] = !(this[dayId] ?: false)
        }
    }

    fun setLibraryFilter(categoria: String) {
        _libraryFilter.value = categoria
    }

    /** Resuelve los slots de rotación (ROT_*) al ejercicio real según la semana actual. */
    private fun resolveIfRotating(item: PlanItem): PlanItem {
        if (!item.idLib.startsWith("ROT_")) return item
        val resolvedId = when (item.idLib) {
            "ROT_PECHO" -> RoutineData.pechoRotation[rotationWeek] ?: "floor-barra"
            "ROT_CORE" -> RoutineData.coreRotation[rotationWeek] ?: "rueda"
            "ROT_TRICEPS1" -> RoutineData.bicepsTricepsRotation[rotationWeek]?.triceps?.getOrNull(0) ?: "patada"
            "ROT_TRICEPS2" -> RoutineData.bicepsTricepsRotation[rotationWeek]?.triceps?.getOrNull(1) ?: "ext-sobre-cabeza"
            "ROT_BICEPS1" -> RoutineData.bicepsTricepsRotation[rotationWeek]?.biceps?.getOrNull(0) ?: "curl-barra"
            "ROT_BICEPS2" -> RoutineData.bicepsTricepsRotation[rotationWeek]?.biceps?.getOrNull(1) ?: "curl-martillo"
            else -> item.idLib
        }
        val ex = RoutineData.exerciseById(resolvedId)
        return item.copy(
            idLib = resolvedId,
            tecnica = ex?.tecnica ?: item.tecnica,
            rodilla = ex?.rodilla ?: item.rodilla
        )
    }

    fun activePlanFor(dayId: String): List<PlanItem> {
        val day = RoutineData.weeklyPlan.find { it.id == dayId } ?: return emptyList()
        val usaAlt = _altToggles.value[dayId] == true
        val base = if (day.esDeporte && usaAlt) {
            RoutineData.altRoutines[day.planAlternativoId]?.ejercicios ?: day.planNormal
        } else {
            day.planNormal
        }
        return base.map { resolveIfRotating(it) }
    }

    fun activeTitleFor(dayId: String): String {
        val day = RoutineData.weeklyPlan.find { it.id == dayId } ?: return ""
        val usaAlt = _altToggles.value[dayId] == true
        return if (day.esDeporte && usaAlt) {
            RoutineData.altRoutines[day.planAlternativoId]?.titulo ?: (day.titulo + " ALT")
        } else {
            day.titulo
        }
    }

    private fun loadTodayTracking() {
        viewModelScope.launch {
            val fecha = LocalDate.now().format(fechaFormatter)
            _todayTracking.value = dao.getByFecha(fecha)
        }
    }

    /** Índices (dentro del plan activo de hoy) que ya se marcaron "Realizado". */
    fun completedIndices(): Set<Int> {
        val raw = _todayTracking.value?.completados ?: ""
        return raw.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
    }

    /** Marca/desmarca un ejercicio puntual como realizado; si se completan todos, marca el día hecho. */
    fun toggleExerciseDone(dayId: String, index: Int, totalItems: Int) {
        viewModelScope.launch {
            val fecha = LocalDate.now().format(fechaFormatter)
            val existente = dao.getByFecha(fecha) ?: TrackingEntity(fecha = fecha, diaId = dayId)
            val set = existente.completados.split(",").mapNotNull { it.trim().toIntOrNull() }.toMutableSet()
            if (set.contains(index)) set.remove(index) else set.add(index)
            val hecho = totalItems > 0 && set.size >= totalItems
            val actualizado = existente.copy(
                diaId = dayId,
                completados = set.sorted().joinToString(","),
                hechoHoy = hecho,
                usaAlt = _altToggles.value[dayId] == true
            )
            dao.upsert(actualizado)
            _todayTracking.value = actualizado
        }
    }

    fun marcarHechoHoy(dayId: String, hecho: Boolean) {
        viewModelScope.launch {
            val fecha = LocalDate.now().format(fechaFormatter)
            val existente = dao.getByFecha(fecha)
            val actualizado = (existente ?: TrackingEntity(fecha = fecha, diaId = dayId))
                .copy(hechoHoy = hecho, usaAlt = _altToggles.value[dayId] == true)
            dao.upsert(actualizado)
            _todayTracking.value = actualizado
        }
    }

    fun guardarMetricas(
        dayId: String,
        fcAvg: Int?,
        fcMax: Int?,
        calorias: Int?,
        tiempoMin: Int?,
        distanciaKm: Double?,
        suenoHoras: Double?,
        pasos: Int?,
        fuenteAuto: Boolean
    ) {
        viewModelScope.launch {
            val fecha = LocalDate.now().format(fechaFormatter)
            val existente = dao.getByFecha(fecha)
            val actualizado = (existente ?: TrackingEntity(fecha = fecha, diaId = dayId)).copy(
                fcAvg = fcAvg, fcMax = fcMax, calorias = calorias, tiempoMin = tiempoMin,
                distanciaKm = distanciaKm, suenoHoras = suenoHoras, pasos = pasos, fuenteAuto = fuenteAuto
            )
            dao.upsert(actualizado)
            _todayTracking.value = actualizado
            evaluarFatiga()
        }
    }

    /** Revisa las últimas sesiones con FC registrada; si el promedio viene alto, sugiere bajar intensidad. */
    private fun evaluarFatiga() {
        viewModelScope.launch {
            val recientes = dao.getRecent(4).mapNotNull { it.fcAvg }
            _fatigaAlta.value = recientes.size >= 2 && recientes.take(3).average() > 155.0
        }
    }

    fun refreshHealthPermissions() {
        viewModelScope.launch {
            _hasHealthPermissions.value = runCatching { healthConnect.hasAllPermissions() }.getOrDefault(false)
        }
    }

    suspend fun leerMetricasDeHoyDesdeReloj(): DailyHealthMetrics = healthConnect.readTodayMetrics()
}
