package fr.unilim.iut.todolist2.android.model

import java.util.Date

/**
 * Classe représentant une tâche
 *
 * @param id Identifiant de la tâche, utilisé dans la BDD
 * @param name Nom de la tâche
 * @param limit Date limite pour réaliser la tâche, nulle s'il n'y en a pas
 * @param state L'état de la tâche
 * @see State
 */
data class Task(
    val id:Int,
    val name:String,
    val limit:Date?,
    val state:State
)

