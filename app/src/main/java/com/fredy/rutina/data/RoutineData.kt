package com.fredy.rutina.data

/**
 * Fuente de datos de la rutina de Fredy. Base tomada de la versión "V10 Limpia",
 * ajustada a sus horarios reales (patinaje lun/mié 1.5h, fútbol vie/sáb 1.25h,
 * natación dom 50min, casa mar/jue 1h) y ampliada con más ejercicios de biblioteca
 * y rotación semanal real de pecho/bíceps/tríceps/core en los días de casa.
 */
object RoutineData {

    val weeklyPlan: List<DayPlan> = listOf(
        DayPlan(
            id = "lun", dia = "LUNES", titulo = "PATINAJE", subtitulo = "Resistencia Z2",
            duracion = "90 min", intensidad = "ALTA", deporte = "patinaje", esDeporte = true,
            planNormal = listOf(
                PlanItem("plancha", "3x40s", "30s", "Core opcional", "Seguro")
            ),
            planAlternativoId = "LUN_ALT"
        ),
        DayPlan(
            id = "mar", dia = "MARTES", titulo = "CASA 60min", subtitulo = "PECHO SUELO + BRAZOS + BICI",
            duracion = "60 min", intensidad = "FUERZA", deporte = null, esDeporte = false,
            planNormal = listOf(
                PlanItem("bici", "8 min", "-", "FC 120-135", "Prepara rodilla"),
                PlanItem("ROT_PECHO", "4x12", "90s", "(rota cada semana)", ""),
                PlanItem("diamante", "3x10", "60s", "Manos diamante", "Sin rodilla"),
                PlanItem("ROT_BICEPS1", "4x10", "75s", "(rota cada semana)", ""),
                PlanItem("ROT_TRICEPS1", "4x12", "60s", "(rota cada semana)", ""),
                PlanItem("ROT_CORE", "3x8", "90s", "(rota cada semana)", ""),
                PlanItem("bici", "10 min HIIT", "-", "30s/30s x10", "Sentado")
            ),
            planAlternativoId = "CASA"
        ),
        DayPlan(
            id = "mie", dia = "MIÉRCOLES", titulo = "PATINAJE", subtitulo = "Intervalos",
            duracion = "90 min", intensidad = "MEDIA", deporte = "patinaje", esDeporte = true,
            planNormal = listOf(
                PlanItem("rueda", "3x30s+8", "40s", "Hollow + rueda corta", "Seguro")
            ),
            planAlternativoId = "MIE_ALT"
        ),
        DayPlan(
            id = "jue", dia = "JUEVES", titulo = "CASA 60min", subtitulo = "BRAZOS + ESPALDA + ABS",
            duracion = "60 min", intensidad = "FUERZA", deporte = null, esDeporte = false,
            planNormal = listOf(
                PlanItem("bici", "6 min", "-", "Activación", "Vasto medial"),
                PlanItem("remo-barra", "4x12", "90s", "45° barra ombligo", "Semiflex"),
                PlanItem("ROT_BICEPS2", "3x12", "60s", "(rota cada semana)", ""),
                PlanItem("ROT_TRICEPS2", "3x12", "45s", "(rota cada semana)", ""),
                PlanItem("ROT_CORE", "3x10+30s", "90s", "(rota cada semana)", ""),
                PlanItem("bici", "12 min Z2", "-", "130-145FC", "Menisco")
            ),
            planAlternativoId = "CASA"
        ),
        DayPlan(
            id = "vie", dia = "VIERNES", titulo = "FÚTBOL", subtitulo = "Partido",
            duracion = "75 min", intensidad = "ALTA", deporte = "fútbol", esDeporte = true,
            planNormal = listOf(
                PlanItem("plancha", "3x12 lado", "30s", "Anti-rotación", "Clave fútbol")
            ),
            planAlternativoId = "VIE_ALT"
        ),
        DayPlan(
            id = "sab", dia = "SÁBADO", titulo = "FÚTBOL", subtitulo = "Competición",
            duracion = "75 min", intensidad = "ALTA", deporte = "fútbol", esDeporte = true,
            planNormal = emptyList(),
            planAlternativoId = "SAB_ALT"
        ),
        DayPlan(
            id = "dom", dia = "DOMINGO", titulo = "NATACIÓN + ABS", subtitulo = "Recuperación",
            duracion = "50 min", intensidad = "SUAVE", deporte = "natación", esDeporte = true,
            planNormal = listOf(
                PlanItem("rueda", "3 rondas abs", "60s", "Crunch15 + elev piernas12 + plancha40s + rueda8", "Suelo acolchado")
            ),
            planAlternativoId = "DOM_ALT"
        )
    )

    val altRoutines: Map<String, AltRoutine> = mapOf(
        "LUN_ALT" to AltRoutine(
            titulo = "LUNES ALT • Pecho + Tríceps + Bici",
            subtitulo = "No patinaje hoy → 60min fuerza plano + quema",
            ejercicios = listOf(
                PlanItem("bici", "10 min Z1", "-", "Calentamiento suave 120-130FC", "Ideal"),
                PlanItem("floor-barra", "4x12", "90s", "Pausa 1s suelo, pecho superior", "100% seguro"),
                PlanItem("floor-manc", "3x12", "75s", "Mango kettlebell como mancuerna", "Seguro"),
                PlanItem("diamante", "3x10", "60s", "Definición interna", "Seguro"),
                PlanItem("ext-sobre-cabeza", "4x12", "60s", "Mango kettlebell sobre cabeza", "Sentado"),
                PlanItem("patada", "3x12", "45s", "Tríceps corte", "Seguro"),
                PlanItem("bici", "12 min HIIT 30/30", "-", "Sprint 90% + suave", "Sentado")
            )
        ),
        "MIE_ALT" to AltRoutine(
            titulo = "MIÉRCOLES ALT • Brazos Completa + Core",
            subtitulo = "No patinaje hoy → bíceps con mancuernas + tríceps",
            ejercicios = listOf(
                PlanItem("bici", "6 min activación", "-", "Z1 suave", "Prepara"),
                PlanItem("curl-barra", "4x10", "75s", "Sentado suelo", "Seguro"),
                PlanItem("curl-martillo", "3x12", "60s", "Martillo neutro mango", "Seguro"),
                PlanItem("curl-conc-kb", "3x10 c/b", "60s", "Concentrado mango kettlebell", "Sentado"),
                PlanItem("ext-sobre-cabeza", "3x12", "60s", "Cabeza larga", "Sentado"),
                PlanItem("flex-cerrada", "3x12", "60s", "Peso corporal tríceps", "Seguro"),
                PlanItem("rueda", "3x10", "90s", "Rueda + plancha", "Toalla"),
                PlanItem("bici", "8 min Z2", "-", "Quema", "Menisco")
            )
        ),
        "VIE_ALT" to AltRoutine(
            titulo = "VIERNES ALT • Circuito Quema Kettlebell + Bici",
            subtitulo = "No fútbol hoy → 60min grasa con mango",
            ejercicios = listOf(
                PlanItem("bici", "5 min Z1", "-", "Calent", "Suave"),
                PlanItem("swing-suave-kb", "4x15", "60s", "Hasta pecho, cadera bisagra, <90° rodilla", "90° max sin impacto"),
                PlanItem("remo-1mano-kb", "3x12 c/b", "60s", "Mango kettlebell remo", "Apoyo"),
                PlanItem("floor-manc", "3x12", "60s", "Press con mango", "Suelo"),
                PlanItem("flex-cerrada", "3x12", "45s", "Tríceps peso corporal", "Seguro"),
                PlanItem("plancha", "3x40s+30s", "30s", "Core anti-rotación", "Seguro"),
                PlanItem("bici", "15 min Z2", "-", "135-150FC constante", "Perfecto")
            )
        ),
        "SAB_ALT" to AltRoutine(
            titulo = "SÁBADO ALT • Full Body Kettlebell Suave",
            subtitulo = "No fútbol hoy → recuperación + fuerza",
            ejercicios = listOf(
                PlanItem("bici", "8 min Z1 suave", "-", "Recuperación", "Muy suave"),
                PlanItem("remo-1mano-kb", "3x12", "75s", "Espalda con mango", "Seguro"),
                PlanItem("curl-conc-kb", "3x10 c/b", "60s", "Bíceps pico mango", "Sentado"),
                PlanItem("floor-barra", "3x12", "75s", "Pecho suelo", "Seguro"),
                PlanItem("swing-suave-kb", "3x15", "60s", "Swing suave glúteo", "<90°"),
                PlanItem("rueda", "2x10", "90s", "Core corto", "Toalla"),
                PlanItem("bici", "10 min Z1", "-", "Enfriamiento", "Suave")
            )
        ),
        "DOM_ALT" to AltRoutine(
            titulo = "DOMINGO ALT • Espalda + Bíceps + Abs",
            subtitulo = "No natación hoy → fuerza tirón + bíceps",
            ejercicios = listOf(
                PlanItem("bici", "6 min Z1", "-", "Activación", "Suave"),
                PlanItem("remo-1mano-kb", "4x12 c/b", "60s", "Mango kettlebell", "Apoyo"),
                PlanItem("remo-barra", "4x12", "90s", "Barra 45°", "Semiflex"),
                PlanItem("curl-barra", "4x10", "75s", "Barra sentado", "Sentado"),
                PlanItem("curl-martillo-cruzado", "3x10 c/b", "60s", "Martillo cruzado mango", "Seguro"),
                PlanItem("rueda", "3x12", "60s", "Abs completo", "Suelo"),
                PlanItem("plancha", "2x40s", "30s", "Plancha", "Seguro")
            )
        )
    )

    val exerciseLibrary: List<Exercise> = listOf(
        Exercise("floor-barra", "Floor Press Barra 25kg", "Pecho plano • sin banco", "Pectoral clavicular + tríceps",
            listOf("Tumbado rodillas 90°, lumbar pegada", "Barra baja hasta tríceps toque suelo, pausa 1s", "Sube explosivo codos 45°"),
            "Tempo 2-1-1. Ideal para pecho plano, quita efecto senos. 25kg con pausa = suficiente.",
            "100% seguro suelo.", "Pecho firme", "4x12 • 90s", "PECHO", false, imagenId = "Floor_Press"),
        Exercise("floor-manc", "Floor Press Mancuernas", "Pecho + control", "Pectoral + tríceps + core",
            listOf("Dos mancuernas o discos con mango kettlebell como mancuerna", "Baja hasta codos toquen suelo", "Sube sin chocar mancuernas"),
            "Si usas mango kettlebell, agarre neutro = más pecho interno y seguro muñeca.",
            "Seguro total en suelo.", "Pecho denso", "3x12 • 75s", "PECHO", true, imagenId = "Dumbbell_Floor_Press"),
        Exercise("diamante", "Flexión Diamante", "Pecho interno", "Pectoral interno + tríceps largo",
            listOf("Manos diamante bajo esternón", "Codos 45°, tabla perfecta", "Pecho roza manos 2s bajada"),
            "Regresión rodillas con toalla si cuesta.",
            "Sin rodilla.", "Definición central", "3x10 • 60s", "PECHO", false),
        Exercise("ext-sobre-cabeza", "Extensión Sobre Cabeza Mancuerna", "Tríceps largo 60% brazo", "Tríceps cabeza larga",
            listOf("Sentado suelo piernas cruzadas, mancuerna/mango sobre cabeza", "Codos al frente, baja a 90° detrás cabeza", "Solo antebrazo extiende"),
            "Mango kettlebell perfecto: peso centrado, agarre seguro. Clave para brazo grande.",
            "Sentado suelo 0 carga.", "Brazo grande", "4x12 • 60s", "TRICEPS", true, imagenId = "Seated_Triceps_Press"),
        Exercise("patada", "Patada Tríceps Mancuerna", "Definición + corte", "Tríceps lateral + largo",
            listOf("Inclinado 45°, rodilla semiflex, espalda recta", "Codo pegado torso, extiende completo atrás", "Aprieta 1s arriba"),
            "Mango kettlebell como mancuerna pesada, controla balance.",
            "Semiflex, peso talones.", "Tríceps marcado", "3x12 c/b • 45s", "TRICEPS", true, imagenId = "Tricep_Dumbbell_Kickback"),
        Exercise("flex-cerrada", "Flexión Cerrada Tríceps", "Peso corporal", "Tríceps + pecho interno",
            listOf("Manos bajo hombros, codos pegados costillas", "Baja pecho a manos", "Sube sin abrir codos"),
            "Más tríceps que flexión normal. Ideal sin material.",
            "Seguro.", "Fuerza tríceps", "3x12 • 60s", "TRICEPS", false, imagenId = "Close-Grip_Push-Up_off_of_a_Dumbbell"),
        Exercise("floor-cierre", "Floor Press Cierre Tríceps", "Tríceps + pecho", "Tríceps + pectoral interno",
            listOf("Barra agarre cerrado hombros ancho", "Codos pegados, barra a pecho bajo", "Extensión tríceps arriba"),
            "Versión tríceps del floor press. 25kg es mucho aquí, controla.",
            "Suelo.", "Pecho-tríceps", "4x10 • 75s", "TRICEPS", false, imagenId = "Close-Grip_Barbell_Bench_Press"),
        Exercise("frances-suelo", "Press Francés Suelo Barra", "Tríceps completo", "Tríceps las 3 cabezas",
            listOf("Tumbado suelo, barra sobre pecho", "Baja frente a frente, codos 90°", "Extiende sin mover hombro"),
            "Suelo evita sobre-extensión. Si molesta codo, baja solo a 100°.",
            "Total seguridad.", "Masa tríceps", "3x12 • 60s", "TRICEPS", false, imagenId = "Lying_Close-Grip_Barbell_Triceps_Extension_Behind_The_Head"),
        Exercise("curl-barra", "Curl Barra 25kg", "Bíceps masa", "Bíceps largo/corto + braquial",
            listOf("De pie espalda pared o sentado suelo", "Codos costillas, solo antebrazo", "Sube 1s aprieta, baja 2s"),
            "25kg pesado, sin balanceo. Sentado suelo mejor para aislar.",
            "Sentado protege 100%.", "Volumen bíceps", "4x10 • 75s", "BICEPS", false, imagenId = "Barbell_Curl"),
        Exercise("curl-martillo", "Curl Martillo Mancuernas", "Braquial + antebrazo", "Braquial + bíceps + supinador",
            listOf("Agarre neutro martillo", "Codos pegados, sube sin girar", "Baja lento 2s"),
            "Mango kettlebell en cada mano o 1 pesada alterna. Engrosa brazo visto de frente.",
            "Seguro.", "Brazo ancho", "3x12 • 60s", "BICEPS", true, imagenId = "Alternate_Hammer_Curl"),
        Exercise("curl-conc-kb", "Curl Concentrado 1 Mano con Mango Kettlebell", "Pico bíceps • herramienta clave", "Bíceps pico + braquial",
            listOf("Sentado, codo apoyado muslo interno", "Mango kettlebell agarre firme, discos ajustados", "Curl lento 2s arriba, aprieta 1s, baja 3s"),
            "Tu mango es perfecto aquí: peso regulable, agarre grueso = más activación. 8-12kg por lado ideal.",
            "Sentado, sin carga rodilla.", "Pico bíceps", "3x10 c/b • 60s", "BICEPS", true, imagenId = "Concentration_Curls"),
        Exercise("curl-martillo-cruzado", "Curl Martillo Cruzado", "Cabeza larga bíceps", "Bíceps cabeza larga + braquial",
            listOf("Mancuerna cruza hacia hombro opuesto", "Codo fijo, antebrazo cruza", "Baja controlado"),
            "Variante para cabeza larga, da altura al bíceps. Mango kettlebell agarre neutro.",
            "Seguro.", "Altura bíceps", "3x10 c/b • 60s", "BICEPS", true, imagenId = "Cross_Body_Hammer_Curl"),
        Exercise("remo-1mano-kb", "Remo 1 Mano con Mango Kettlebell", "Dorsal + postura • mango", "Dorsal + romboides + bíceps",
            listOf("Rodilla y mano en silla/suelo, espalda recta", "Mango kettlebell rema a ombligo", "Aprieta escápula 1s"),
            "Usa tu foto: mismo gesto. Peso ajustable = progresión perfecta. Ideal para V-taper.",
            "Apoyo reduce carga rodilla.", "Espalda ancha", "4x12 c/b • 60s", "ESPALDA", true, imagenId = "One-Arm_Kettlebell_Row"),
        Exercise("remo-barra", "Remo con Barra Suelo 25kg", "Espalda + postura", "Dorsal + romboides",
            listOf("Inclinado 45°, rodillas semiflex, barra colgando", "Rema a ombligo codos pegados", "Baja 2s sin caer"),
            "Suelo evita banco. Si lumbar molesta, 1 brazo con mango.",
            "Semiflex, peso talones.", "V-taper", "4x12 • 90s", "ESPALDA", false, imagenId = "Bent_Over_Barbell_Row"),
        Exercise("swing-suave-kb", "Swing Suave Kettlebell hasta Pecho", "Quema grasa sin impacto", "Glúteo + core + dorsal",
            listOf("Mango kettlebell 10-16kg, pies ancho hombros", "Bisagra cadera, no sentadilla profunda <90°", "Swing hasta pecho, no overhead, rodillas 20° flex"),
            "Suave para menisco: sin rebote rodilla, fuerza desde glúteo. Hasta pecho = protege hombro y rodilla.",
            "90° max, sin impacto. Ideal.", "Quema + glúteo", "4x15 • 60s", "ESPALDA", true, imagenId = "One-Arm_Kettlebell_Swings"),
        Exercise("rueda", "Rueda Abdominal", "Core anti-extensión", "Recto + transverso + dorsal",
            listOf("Rodillas colchoneta gruesa", "Rueda al frente sin arquear lumbar", "Vuelve con abdomen"),
            "Rango corto inicio +5cm/semana.",
            "Toalla triple.", "Core patinaje", "3x8 • 90s", "CORE", false, imagenId = "Barbell_Ab_Rollout"),
        Exercise("plancha", "Plancha Suelo + Lateral", "Core estable", "Core completo + hombros",
            listOf("Codos bajo hombros, glúteo apretado 40s", "Lateral 30s cada lado", "Respiración normal"),
            "Base para todo. Si rueda duele lumbar, solo plancha.",
            "Seguro.", "Estabilidad", "3x40s+30s", "CORE", false, imagenId = "Plank"),
        Exercise("bici", "Bici Estática HIIT / Z2", "Quema sin impacto", "Vasto medial + glúteo + cardio",
            listOf("Sillín cresta ilíaca, rodilla 25° flex abajo", "Z2 FC 135-150 80-90rpm", "HIIT 30/30 x10 sentado si rodilla"),
            "180min/sem Z2 + 20min HIIT. Planta pie, no punta.",
            "Mejor ejercicio menisco.", "Déficit", "12-45min", "CORE", false, imagenId = "Bicycling_Stationary"),
        Exercise("mountain-climbers", "Escalador (Mountain Climbers)", "Core + cardio", "Recto abdominal + core + hombros",
            listOf("Posición de plancha alta, manos bajo hombros", "Lleva rodilla al pecho alternando rápido", "Cadera estable, no subas glúteo"),
            "Ritmo controlado 30-40s, sube el ritmo según nivel. Excelente para bajar abdomen por el componente cardio.",
            "Bajo impacto en rodilla si el ritmo es moderado.", "Quema abdomen", "3x30-40s • 45s", "CORE", false, imagenId = "Mountain_Climbers"),
        Exercise("plancha-toques", "Plancha con Toque de Hombros", "Core anti-rotación", "Core + hombros + estabilidad",
            listOf("Plancha alta, pies separados ancho cadera", "Toca hombro contrario con mano, alterna", "Cadera lo más quieta posible"),
            "Si la cadera se mueve mucho, abre más los pies. Ideal complemento a la plancha estática.",
            "Seguro, sin apoyo de rodilla.", "Estabilidad + abdomen", "3x10 c/lado • 45s", "CORE", false),
        Exercise("press-militar-manc", "Press Militar con Mancuernas", "Hombro completo", "Deltoide anterior + medio + tríceps",
            listOf("De pie o sentado, mancuernas a la altura del hombro", "Empuja arriba sin arquear lumbar", "Baja controlado 2s"),
            "De pie exige más core; sentado contra pared si molesta la espalda baja.",
            "Sin impacto en rodilla.", "Hombro ancho", "3x12 • 75s", "HOMBROS", false, imagenId = "Standing_Military_Press"),
        Exercise("elevacion-lateral", "Elevación Lateral Mancuernas", "Deltoide medio", "Deltoide medio",
            listOf("Mancuernas a los costados, codos con leve flexión", "Sube hasta la altura del hombro, no más arriba", "Baja lento 2s sin balanceo"),
            "Peso ligero-moderado; el balanceo del cuerpo quita efectividad. Clave para hombro ancho visualmente.",
            "Sin impacto en rodilla.", "Hombro definido", "3x12 • 60s", "HOMBROS", false, imagenId = "Side_Lateral_Raise"),
        Exercise("goblet-squat-kb", "Sentadilla Goblet con Mango Kettlebell", "Pierna + core", "Cuádriceps + glúteo + core",
            listOf("Mango kettlebell sostenido a la altura del pecho", "Baja cadera atrás, rodilla en línea con el pie", "Sube empujando con el talón"),
            "Controla el rango: solo baja hasta donde la rodilla no moleste, no fuerces profundidad.",
            "Rango controlado, evita bajar de 90° si molesta.", "Pierna + core", "3x12 • 75s", "PIERNA", true, imagenId = "Goblet_Squat"),
        Exercise("remo-manc-un-brazo", "Remo a un Brazo con Mancuerna", "Espalda unilateral", "Dorsal + romboides + bíceps",
            listOf("Apoyo de mano y rodilla en silla o banco", "Rema la mancuerna a la cadera, codo pegado", "Aprieta escápula 1s arriba"),
            "Variante con mancuerna del remo con mango; alterna entre ambos para variedad.",
            "Apoyo reduce carga en rodilla de apoyo.", "Espalda ancha", "3x12 c/b • 60s", "ESPALDA", false, imagenId = "One-Arm_Dumbbell_Row")
    )

    /** Rotación semanal (4 semanas) del ejercicio principal de pecho en los días de casa. */
    val pechoRotation: Map<Int, String> = mapOf(
        1 to "floor-barra",
        2 to "floor-manc",
        3 to "floor-cierre",
        4 to "floor-manc"
    )

    /** Rotación semanal (4 semanas) del ejercicio final de core en los días de casa. */
    val coreRotation: Map<Int, String> = mapOf(
        1 to "rueda",
        2 to "plancha",
        3 to "mountain-climbers",
        4 to "plancha-toques"
    )

    /** Rotación de combos bíceps/tríceps: 4 semanas, se repite en ciclo. */
    val bicepsTricepsRotation: Map<Int, BicepsTricepsCombo> = mapOf(
        1 to BicepsTricepsCombo(listOf("ext-sobre-cabeza", "patada"), listOf("curl-barra", "curl-martillo"), "Patada Tríceps + Curl Barra"),
        2 to BicepsTricepsCombo(listOf("flex-cerrada", "floor-cierre"), listOf("curl-conc-kb", "curl-martillo-cruzado"), "Flex Cerrada + Curl Concentrado Mango"),
        3 to BicepsTricepsCombo(listOf("frances-suelo", "ext-sobre-cabeza"), listOf("curl-barra", "curl-conc-kb"), "Francés Suelo + Curl Concentrado Mango"),
        4 to BicepsTricepsCombo(listOf("patada", "flex-cerrada"), listOf("curl-martillo", "curl-martillo-cruzado"), "Patada + Martillo Cruzado")
    )

    val categorias = listOf("TODOS", "PECHO", "TRICEPS", "BICEPS", "ESPALDA", "HOMBROS", "CORE", "PIERNA")

    fun exerciseById(id: String): Exercise? = exerciseLibrary.find { it.id == id }
}
