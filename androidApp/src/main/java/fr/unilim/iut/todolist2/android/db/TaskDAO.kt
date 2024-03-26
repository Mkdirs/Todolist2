package fr.unilim.iut.todolist2.android.db

import android.content.Context
import fr.unilim.iut.todolist2.android.FORMATTER
import fr.unilim.iut.todolist2.android.model.State
import fr.unilim.iut.todolist2.android.model.Task
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Locale

class TaskDAO(context: Context) : Dao<Task>(context, TableContract.TASK) {
    override fun get(id: Int): Task? {
        val projection = arrayOf(
            DBHelper.TaskEntry.COLUMN_ID,
            DBHelper.TaskEntry.COLUMN_NAME,
            DBHelper.TaskEntry.COLUMN_STATE,
            DBHelper.TaskEntry.COLUMN_DUE_DATE,
            DBHelper.TaskEntry.COLUMN_REPOSITORY
        )

        val selection = "${DBHelper.TaskEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf("$id")
        val sortOrder = "${DBHelper.TaskEntry.COLUMN_ID} ASC"

        val cursor = readDB.query(
            DBHelper.TaskEntry.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        var task:Task? = null
        with(cursor){
            if(cursor.moveToNext()){
                val state = State.entries.find { it.id == getInt(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_STATE)) }
                val limit = getString(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_DUE_DATE))?.let {
                    LocalDateTime.parse(it, FORMATTER)
                }
                task = Task(
                    id,
                    name = getString(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_NAME)),
                    limit,
                    state!!,
                    repository = getInt(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_REPOSITORY))
                )
            }
        }

        return task;
    }

    override fun list(): List<Task> {
        val projection = arrayOf(
            DBHelper.TaskEntry.COLUMN_ID,
            DBHelper.TaskEntry.COLUMN_NAME,
            DBHelper.TaskEntry.COLUMN_STATE,
            DBHelper.TaskEntry.COLUMN_DUE_DATE,
            DBHelper.TaskEntry.COLUMN_REPOSITORY
        )

        val sortOrder = "${DBHelper.TaskEntry.COLUMN_ID} ASC"

        val cursor = readDB.query(
            DBHelper.TaskEntry.TABLE,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val tasks:MutableList<Task> = mutableListOf()
        with(cursor){
            while(cursor.moveToNext()){
                val state = State.entries.find { it.id == getInt(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_STATE)) }
                val limit = getString(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_DUE_DATE))?.let {
                    LocalDateTime.parse(it, FORMATTER)
                }
                tasks.add(Task(
                    getInt(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_NAME)),
                    limit,
                    state!!,
                    repository = getInt(getColumnIndexOrThrow(DBHelper.TaskEntry.COLUMN_REPOSITORY))
                ))
            }
        }

        return tasks.toList()
    }
}