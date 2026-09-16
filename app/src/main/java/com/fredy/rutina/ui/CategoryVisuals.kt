package com.fredy.rutina.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.ui.graphics.vector.ImageVector
import com.fredy.rutina.R
import com.fredy.rutina.ui.theme.AzulAccion
import com.fredy.rutina.ui.theme.NaranjaAlerta
import com.fredy.rutina.ui.theme.RojoAlta
import com.fredy.rutina.ui.theme.VerdeOk
import androidx.compose.ui.graphics.Color

/**
 * Ícono + color de miniatura según la categoría del ejercicio, para reconocer de un
 * vistazo el tipo de movimiento sin tener que abrir el video. No reemplaza una
 * ilustración paso a paso del ejercicio exacto, solo ayuda a ubicar rápido de qué se trata.
 */
object CategoryVisuals {

    /** Pictograma dibujado a mano para ejercicios sin foto real disponible. */
    fun localDrawableFor(exerciseId: String): Int? = when (exerciseId) {
        "diamante" -> R.drawable.ic_diamond_pushup
        "plancha-toques" -> R.drawable.ic_plank_taps
        else -> null
    }

    fun iconFor(categoria: String): ImageVector = when (categoria) {
        "PECHO" -> Icons.Filled.FitnessCenter
        "TRICEPS" -> Icons.Filled.FitnessCenter
        "BICEPS" -> Icons.Filled.FitnessCenter
        "ESPALDA" -> Icons.Filled.Accessibility
        "HOMBROS" -> Icons.Filled.Accessibility
        "CORE" -> Icons.Filled.SelfImprovement
        "PIERNA" -> Icons.Filled.DirectionsRun
        else -> Icons.Filled.DirectionsBike
    }

    fun colorFor(categoria: String): Color = when (categoria) {
        "PECHO" -> AzulAccion
        "TRICEPS" -> RojoAlta
        "BICEPS" -> NaranjaAlerta
        "ESPALDA" -> VerdeOk
        "HOMBROS" -> AzulAccion
        "CORE" -> NaranjaAlerta
        "PIERNA" -> VerdeOk
        else -> AzulAccion
    }
}
