package com.example.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Exercise(
    val id: String,
    val name: String,
    val description: String,
    val primaryMuscle: String, // Pectoraux, Dos, Épaules, Biceps, Triceps, Abdominaux, Jambes, Cardio
    val secondaryMuscles: List<String>,
    val difficulty: String, // Débutant, Intermédiaire, Avancé
    val guideSteps: List<String>,
    val animationType: String, // e.g., "bench_press", "squat", "pushup" for custom canvas draw
    val videoExplainer: String = "Vidéo explicative disponible sur l'application"
)

data class TrainingProgram(
    val id: String,
    val name: String,
    val goal: String,
    val difficulty: String,
    val exercises: List<ProgramExercise>,
    val weatherTips: String,
    val estCalories: Int,
    val estDurationMinutes: Int
)

data class ProgramExercise(
    val exercise: Exercise,
    val sets: Int,
    val reps: String, // e.g., "10", "12", "30s"
    val restSeconds: Int
)

object ExerciseLibrary {
    val list = listOf(
        // Pectoraux
        Exercise(
            id = "pec_1",
            name = "Développé Couché (Bench Press)",
            description = "L'exercice fondamental pour bâtir de puissants pectoraux, sollicitant également les triceps et l'avant de l'épaule.",
            primaryMuscle = "Pectoraux",
            secondaryMuscles = listOf("Triceps", "Épaules (Anterieur)"),
            difficulty = "Intermédiaire",
            guideSteps = listOf(
                "Allongez-vous sur le banc, les pieds bien à plat au sol.",
                "Saisissez la barre avec un écartement légèrement supérieur à la largeur des épaules.",
                "Décrochez la barre et descendez-la lentement jusqu'à effleurer le milieu de la poitrine.",
                "Poussez la barre vers le haut en contractant fortement les pectoraux en fin de mouvement."
            ),
            animationType = "bench_press"
        ),
        Exercise(
            id = "pec_2",
            name = "Pompes Classiques (Pushups)",
            description = "Mouvement de poids de corps incontournable pour les pectoraux, les épaules et le gainage abdominal.",
            primaryMuscle = "Pectoraux",
            secondaryMuscles = listOf("Triceps", "Abdominaux"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Placez vos mains au sol, écartées de la largeur des épaules, corps parfaitement aligné.",
                "Descendez le corps en maintenant le tronc serré jusqu'à toucher presque le sol de la poitrine.",
                "Repoussez de manière explosive en expirant."
            ),
            animationType = "pushup"
        ),
        
        // Dos
        Exercise(
            id = "dos_1",
            name = "Tractions Pronation (Pull-ups)",
            description = "Meilleur exercice pour élargir le dos, renforce le grand dorsal et les biceps.",
            primaryMuscle = "Dos",
            secondaryMuscles = listOf("Biceps", "Avant-bras"),
            difficulty = "Avancé",
            guideSteps = listOf(
                "Suspendez-vous à la barre, mains en pronation plus écartées que les épaules.",
                "Tirez votre corps vers le haut en concentrant l'effort sur les coudes.",
                "Amenez votre menton au-dessus de la barre en contractant le dos.",
                "Redescendez lentement en contrôlant la descente."
            ),
            animationType = "pull_up"
        ),
        Exercise(
            id = "dos_2",
            name = "Tirage Buste Penché (Rowing)",
            description = "Développe l'épaisseur du dos (trapèzes, rhomboïdes, grand rond).",
            primaryMuscle = "Dos",
            secondaryMuscles = listOf("Biceps", "Arrière d'Épaule"),
            difficulty = "Intermédiaire",
            guideSteps = listOf(
                "Penchez votre buste à 45 degrés, le dos parfaitement droit.",
                "Tirez la barre vers le bas de votre sternum en ramenant vos coudes vers l'arrière.",
                "Contractez le dos en position haute, puis relâchez progressivement."
            ),
            animationType = "rowing"
        ),

        // Épaules
        Exercise(
            id = "ep_1",
            name = "Développé Militaire (Military Press)",
            description = "Développe la force globale de la ceinture scapulaire et de la coiffe des rotateurs.",
            primaryMuscle = "Épaules",
            secondaryMuscles = listOf("Triceps", "Haut des Pectoraux"),
            difficulty = "Intermédiaire",
            guideSteps = listOf(
                "Debout ou assis, barre reposant sur le haut de la poitrine.",
                "Poussez la barre au-dessus de votre tête en verrouillant légèrement les bras.",
                "Ramenez la barre de façon contrôlée sous le menton."
            ),
            animationType = "shoulder_press"
        ),
        Exercise(
            id = "ep_2",
            name = "Élévations Latérales (Lateral Raises)",
            description = "Isole spécifiquement le deltoïde moyen pour donner une carrure large en V.",
            primaryMuscle = "Épaules",
            secondaryMuscles = listOf("Trapèzes"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Debout, un haltère dans chaque main le long du corps.",
                "Élevez les bras latéralement jusqu'à l'horizontale en gardant les coudes très légèrement fléchis.",
                "Redescendez lentement à la position de départ."
            ),
            animationType = "lateral_raise"
        ),

        // Biceps
        Exercise(
            id = "bi_1",
            name = "Curl Haltère (Dumbbell Curl)",
            description = "L'exercice classique d'isolation pour développer le pic du biceps brachial.",
            primaryMuscle = "Biceps",
            secondaryMuscles = listOf("Avant-bras"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Debout ou assis, un haltère dans chaque main en prise neutre.",
                "Montez l'haltère en effectuant une rotation du poignet (supination) pour que la paume regarde vers le haut.",
                "Contractez intensément le biceps au sommet avant de redescendre le poids."
            ),
            animationType = "bicep_curl"
        ),

        // Triceps
        Exercise(
            id = "tri_1",
            name = "Dips sur Banc (Bench Dips)",
            description = "Idéal pour recruter le vaste externe du triceps avec le poids corporel.",
            primaryMuscle = "Triceps",
            secondaryMuscles = listOf("Épaules", "Pectoraux"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Asseyez-vous sur le bord d’un banc, mains posées sur le bord.",
                "Avancez vos pieds et suspendez vos fesses dans le vide.",
                "Fléchissez les coudes à 90 degrés pour descendre, puis repoussez fermement."
            ),
            animationType = "dips"
        ),

        // Abdominaux
        Exercise(
            id = "abs_1",
            name = "Gainage Planche (Plank)",
            description = "Renforcement profond du transverse pour un ventre plat et un dos solide.",
            primaryMuscle = "Abdominaux",
            secondaryMuscles = listOf("Lombaires", "Épaules"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Prenez appui sur vos avant-bras et vos pointes de pieds.",
                "Maintenez le corps parfaitement droit (alignement tête-bassin-talons).",
                "Contractez volontairement la sangle abdominale et les fessiers sans creuser le dos."
            ),
            animationType = "plank"
        ),
        
        // Jambes
        Exercise(
            id = "leg_1",
            name = "Squat Gobelet (Goblet Squat)",
            description = "Idéal pour renforcer les quadriceps, fessiers et ischio-jambiers tout en préservant le dos.",
            primaryMuscle = "Jambes",
            secondaryMuscles = listOf("Fessiers", "Ischio-Jambiers"),
            difficulty = "Débutant",
            guideSteps = listOf(
                "Tenez un haltère verticalement contre votre poitrine.",
                "Descendez en poussant le bassin vers l’arrière, fesses sous la ligne des genoux.",
                "Remontez en poussant fort sur les talons et en gardant le dos droit."
            ),
            animationType = "squat"
        ),

        // Cardio
        Exercise(
            id = "card_1",
            name = "Burpees",
            description = "Exercice pliométrique à haute intensité pour brûler un maximum de graisse et tester le souffle.",
            primaryMuscle = "Cardio",
            secondaryMuscles = listOf("Jambes", "Pectoraux", "Épaules", "Gainage"),
            difficulty = "Avancé",
            guideSteps = listOf(
                "D'une position debout, accroupissez-vous et posez les mains au sol.",
                "Lancez vos pieds en arrière pour vous retrouver en position de planche.",
                "Faites une pompe complète, ramenez les pieds sous la poitrine.",
                "Sautez verticalement le plus haut possible les bras vers le ciel."
            ),
            animationType = "burpee"
        )
    )

    fun getExercisesByMuscle(muscle: String): List<Exercise> {
        return list.filter { it.primaryMuscle.equals(muscle, ignoreCase = true) }
    }

    fun generateProgram(
        goal: String,
        difficulty: String,
        tempCelsius: Float = 20f,
        precipitation: Float = 0f,
        windSpeed: Float = 0f
    ): TrainingProgram {
        // Filter elements based on difficulty
        val potentialExercises = list.filter {
            it.difficulty == "Débutant" || 
            (difficulty == "Intermédiaire" && it.difficulty != "Avancé") || 
            difficulty == "Avancé"
        }

        val selectedSpecs = when (goal) {
            "Weight Loss" -> {
                // Highly cardio/fat burning
                val chest = potentialExercises.firstOrNull { it.id == "pec_2" } ?: list.first { it.id == "pec_2" }
                val back = potentialExercises.firstOrNull { it.id == "dos_2" } ?: list.first { it.id == "dos_2" }
                val legs = potentialExercises.firstOrNull { it.id == "leg_1" } ?: list.first { it.id == "leg_1" }
                val cardio = potentialExercises.firstOrNull { it.id == "card_1" } ?: list.first { it.id == "card_1" }
                val abs = potentialExercises.firstOrNull { it.id == "abs_1" } ?: list.first { it.id == "abs_1" }
                listOf(
                    ProgramExercise(cardio, 4, "30s", 30),
                    ProgramExercise(legs, 3, "20", 45),
                    ProgramExercise(chest, 3, "15", 45),
                    ProgramExercise(back, 3, "15", 45),
                    ProgramExercise(abs, 3, "45s", 30)
                )
            }
            "Mass Gain", "Muscle Building" -> {
                // Heavy hypertrophy
                val bPress = potentialExercises.firstOrNull { it.id == "pec_1" } ?: list.first { it.id == "pec_1" }
                val row = potentialExercises.firstOrNull { it.id == "dos_2" } ?: list.first { it.id == "dos_2" }
                val mPress = potentialExercises.firstOrNull { it.id == "ep_1" } ?: list.first { it.id == "ep_1" }
                val curl = potentialExercises.firstOrNull { it.id == "bi_1" } ?: list.first { it.id == "bi_1" }
                val squat = potentialExercises.firstOrNull { it.id == "leg_1" } ?: list.first { it.id == "leg_1" }
                listOf(
                    ProgramExercise(bPress, 4, "8-12", 90),
                    ProgramExercise(squat, 4, "10", 90),
                    ProgramExercise(row, 4, "8-12", 90),
                    ProgramExercise(mPress, 3, "10", 75),
                    ProgramExercise(curl, 3, "12", 60)
                )
            }
            "Cardio" -> {
                val abs = potentialExercises.firstOrNull { it.id == "abs_1" } ?: list.first { it.id == "abs_1" }
                val burpee = potentialExercises.firstOrNull { it.id == "card_1" } ?: list.first { it.id == "card_1" }
                val pushup = potentialExercises.firstOrNull { it.id == "pec_2" } ?: list.first { it.id == "pec_2" }
                val squat = potentialExercises.firstOrNull { it.id == "leg_1" } ?: list.first { it.id == "leg_1" }
                listOf(
                    ProgramExercise(burpee, 4, "40s", 30),
                    ProgramExercise(squat, 4, "25", 45),
                    ProgramExercise(pushup, 3, "18", 45),
                    ProgramExercise(abs, 4, "60s", 30)
                )
            }
            else -> { // Fitness / Remise en forme
                val pushup = potentialExercises.firstOrNull { it.id == "pec_2" } ?: list.first { it.id == "pec_2" }
                val rowing = potentialExercises.firstOrNull { it.id == "dos_2" } ?: list.first { it.id == "dos_2" }
                val latRaises = potentialExercises.firstOrNull { it.id == "ep_2" } ?: list.first { it.id == "ep_2" }
                val squat = potentialExercises.firstOrNull { it.id == "leg_1" } ?: list.first { it.id == "leg_1" }
                val abs = potentialExercises.firstOrNull { it.id == "abs_1" } ?: list.first { it.id == "abs_1" }
                listOf(
                    ProgramExercise(squat, 3, "15", 60),
                    ProgramExercise(pushup, 3, "12", 60),
                    ProgramExercise(rowing, 3, "12", 60),
                    ProgramExercise(latRaises, 3, "15", 45),
                    ProgramExercise(abs, 3, "30s", 45)
                )
            }
        }

        // Apply Weather Intelligence to adapt recommendations
        var tips = "Météo idéale. Bon entraînement !"
        var timeMult = 1.0
        var calMult = 1.0

        if (tempCelsius > 28) {
            tips = "⚠️ Température élevée : Augmentez votre temps de repos de +15s. Réduisez l'effort cardio intense de 20%. Hydratez-vous abondamment (au moins 1L d'eau pendant la séance). Entraînez-vous de préférence en salle de sport climatisée."
            timeMult = 1.15
            calMult = 0.9
        } else if (tempCelsius < 10) {
            tips = "❄️ Météo fraîche : Réalisez impérativement un échauffement articulaire prolongé de 15 minutes. Vos muscles et tendons ont besoin de temps pour être prêts. Maintenez du cardio modéré continu."
            timeMult = 1.2
            calMult = 1.05
        }

        if (precipitation > 1.0) {
            tips += " 🌧️ Pluie torrentielle ! Restez à l'abri et privilégiez ce programme de musculation d'intérieur."
        }

        val baseCalories = when (difficulty) {
            "Débutant" -> 250
            "Intermédiaire" -> 400
            "Avancé" -> 550
            else -> 350
        }

        val baseMinutes = when (difficulty) {
            "Débutant" -> 35
            "Intermédiaire" -> 45
            "Avancé" -> 60
            else -> 45
        }

        return TrainingProgram(
            id = "prog_${goal.lowercase().replace(" ","_")}_${difficulty.lowercase()}",
            name = "Coaching $difficulty • $goal",
            goal = goal,
            difficulty = difficulty,
            exercises = selectedSpecs,
            weatherTips = tips,
            estCalories = (baseCalories * calMult).toInt(),
            estDurationMinutes = (baseMinutes * timeMult).toInt()
        )
    }
}
