package fr.unilim.iut.todolist2.android.model

import android.content.ContentValues
import fr.unilim.iut.todolist2.android.FORMATTER
import fr.unilim.iut.todolist2.android.db.DBHelper
import fr.unilim.iut.todolist2.android.db.IntoContentValues
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale

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
    val limit:LocalDateTime?,
    val state:State,
    val repository:Int
) : IntoContentValues{
    override fun into(): ContentValues {
        val values = ContentValues().apply {
            put(DBHelper.TaskEntry.COLUMN_ID, id)
            put(DBHelper.TaskEntry.COLUMN_NAME, name)
            limit?.also {
                put(DBHelper.TaskEntry.COLUMN_DUE_DATE, it.format(FORMATTER))
            }

            put(DBHelper.TaskEntry.COLUMN_STATE, state.id)
            put(DBHelper.TaskEntry.COLUMN_REPOSITORY, repository)
        }

        return values
    }
}

