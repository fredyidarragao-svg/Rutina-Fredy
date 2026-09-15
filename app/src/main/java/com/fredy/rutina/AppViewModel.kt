package com.fredy.rutina

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fredy.rutina.data.AppDatabase
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

    /** Semana de rotación 1..4 para el combo bíceps/tríceps, basada en la semana del año. */
    val rotationWeek: Int
        get() {
            val weekOfYear = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfYear())
            return ((weekOfYear - 1) % 4) + 1
        }

    init {
        _healthAvailability.value = healthConnect.availability()
        loadTodayTracking()
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

    fun activePlanFor(dayId: String): List<com.fredy.rutina.data.PlanItem> {
        val day = RoutineData.weeklyPlan.find { it.id == dayId } ?: return emptyList()
        val usaAlt = _altToggles.value[dayId] == true
        return if (day.esDeporte && usaAlt) {
            RoutineData.altRoutines[day.planAlternativoId]?.ejercicios ?: day.planNormal
        } else {
            day.planNormal
        }
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
        }
    }

    fun refreshHealthPermissions() {
        viewModelScope.launch {
            _hasHealthPermissions.value = runCatching { healthConnect.hasAllPermissions() }.getOrDefault(false)
        }
    }

    suspend fun leerMetricasDeHoyDesdeReloj(): DailyHealthMetrics = healthConnect.readTodayMetrics()
}
