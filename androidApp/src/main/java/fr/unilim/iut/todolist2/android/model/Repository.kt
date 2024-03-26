package fr.unilim.iut.todolist2.android.model

import android.content.ContentValues
import fr.unilim.iut.todolist2.android.db.DBHelper
import fr.unilim.iut.todolist2.android.db.IntoContentValues

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
) : IntoContentValues {
    override fun into(): ContentValues {
        val values = ContentValues().apply {
            put(DBHelper.RepositoryEntry.COLUMN_ID, id)
            put(DBHelper.RepositoryEntry.COLUMN_NAME, name)
        }

        return values
    }
}
