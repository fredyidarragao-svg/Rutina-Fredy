# Rutina Fredy — V1 (Android nativo)

App nativa Android (Kotlin + Jetpack Compose) con **toda tu rutina real** tomada de tu
versión "V10 Limpia": los 7 días, las 5 rutinas ALT, la biblioteca de 18 ejercicios con
técnica y nota de rodilla/menisco, y la rotación de bíceps/tríceps de 4 semanas.

## Cómo se conecta con tu Amazfit

Xiaomi/Amazfit no deja que apps de terceros se conecten por Bluetooth directo al reloj.
Por eso esta app usa **Health Connect** (el hub oficial de salud de Android):

1. Tu app Zepp o Mi Fitness (la que ya usas con el Amazfit) sincroniza tus datos
   (frecuencia cardíaca, pasos, sueño, ejercicio) a Health Connect.
2. Esta app lee esos datos desde Health Connect — no necesita permisos especiales
   de Zepp ni acceso directo al reloj.
3. Dentro de cada día, en "Registrar métricas del reloj" puedes traerlos con un botón,
   o escribirlos a mano si prefieres.

**Antes de compilar, verifica en tu teléfono:**
- Que la app **Health Connect** esté instalada (Play Store, o ya viene si tu Android es 14+).
- Que Zepp/Mi Fitness tenga activada la sincronización hacia Health Connect (dentro de
  Zepp: Perfil → Configuración → Health Connect, actívalo).

## Cómo abrir y compilar

1. Instala **Android Studio** (versión reciente, Ladybug o superior).
2. Abre esta carpeta como proyecto (`File → Open`).
3. Deja que Android Studio sincronice Gradle (te pedirá generar el wrapper si falta,
   acepta — solo necesita internet la primera vez).
4. Conecta tu Honor 400 por USB con depuración activada, o usa un emulador.
5. Dale ▶ Run. Se instala directo en el teléfono.

## Estructura

- `data/RoutineData.kt` — toda tu rutina real (no se inventó nada, viene del archivo que subiste).
- `data/TrackingEntity.kt` + `AppDatabase.kt` — historial local (Room), no se sube a ningún servidor.
- `health/HealthConnectManager.kt` — lectura de FC, pasos, sueño, ejercicio desde Health Connect.
- `ui/WeekScreen.kt` — semana completa.
- `ui/DayDetailScreen.kt` — ejercicios del día, toggle de rutina ALT, marcar "hecho hoy".
- `ui/LibraryScreen.kt` — biblioteca de ejercicios filtrable por categoría.
- `ui/TrackingSheet.kt` — registrar métricas (manual o desde el reloj).

## Qué falta ajustar (para la siguiente vuelta)

- Ícono de la app: puse uno simple de pesa; si quieres uno más elaborado lo hago.
- Historial visual (gráficas de progreso semana a semana) — no incluido aún en V1.
- Distancia automática desde Health Connect (`DistanceRecord`) — ahora mismo esa
  métrica queda para registrar a mano; se puede automatizar igual que pasos.
- Notificación/recordatorio diario de la rutina.
- Exportar historial a CSV o compartir por WhatsApp.

Dime qué de esto ajustamos primero, o si algo del comportamiento (rotación de semana,
textos, colores) no coincide con lo que esperabas y lo corrijo.
