package com.fredy.rutina.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Un registro de seguimiento para un día concreto (fecha real, no solo "lunes").
 * fuenteAuto = true cuando los valores vinieron de Health Connect (reloj), no manuales.
 */
@Entity(tableName = "tracking")
data class TrackingEntity(
    @PrimaryKey val fecha: String, // formato yyyy-MM-dd
    val diaId: String,             // lun, mar, mie, jue, vie, sab, dom
    val hechoHoy: Boolean = false,
    val usaAlt: Boolean = false,
    val fcAvg: Int? = null,
    val fcMax: Int? = null,
    val calorias: Int? = null,
    val tiempoMin: Int? = null,
    val distanciaKm: Double? = null,
    val suenoHoras: Double? = null,
    val pasos: Int? = null,
    val fuenteAuto: Boolean = false,
    val notas: String = ""
)
