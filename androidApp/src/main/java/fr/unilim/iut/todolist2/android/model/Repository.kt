package fr.unilim.iut.todolist2.android.model

/**
 * Regroupement de tâches
 *  @param id Identifiant du répertoire, utilisé dans la BDD
 *  @param name Nom du répertoire
 *  @param tasks Liste des tâches contenues dans le répertoire
 *  @see Task
 */
data class Repository(
    val id:Int,
    val name:String,
    val tasks:List<Task>
)
