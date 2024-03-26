package fr.unilim.iut.todolist2.android.db

import android.content.Context
import fr.unilim.iut.todolist2.android.model.Repository
import fr.unilim.iut.todolist2.android.model.State
import fr.unilim.iut.todolist2.android.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class RepositoryDAO(context: Context) : Dao<Repository>(context, TableContract.REPOSITORY) {
    private val taskDAO:TaskDAO
    init {
        taskDAO = TaskDAO(context)
    }
    override fun get(id: Int): Repository? {
        val projection = arrayOf(
            DBHelper.RepositoryEntry.COLUMN_ID,
            DBHelper.RepositoryEntry.COLUMN_NAME
        )

        val selection = "${DBHelper.RepositoryEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf("$id")
        val sortOrder = "${DBHelper.RepositoryEntry.COLUMN_ID} ASC"

        val cursor = readDB.query(
            DBHelper.RepositoryEntry.TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        var repository:Repository? = null

        with(cursor){
            if(cursor.moveToNext()){
                repository = Repository(
                    id,
                    getString(getColumnIndexOrThrow(DBHelper.RepositoryEntry.COLUMN_NAME)),
                    taskDAO.list().filter { it.repository == id }
                )

            }
        }

        return repository;
    }

    override fun list(): List<Repository> {
        val projection = arrayOf(
            DBHelper.RepositoryEntry.COLUMN_ID,
            DBHelper.RepositoryEntry.COLUMN_NAME
        )

        val sortOrder = "${DBHelper.RepositoryEntry.COLUMN_ID} ASC"

        val cursor = readDB.query(
            DBHelper.RepositoryEntry.TABLE,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val repositories:MutableList<Repository> = mutableListOf()

        with(cursor){
            while(cursor.moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DBHelper.RepositoryEntry.COLUMN_ID))
                repositories.add(Repository(
                    id,
                    getString(getColumnIndexOrThrow(DBHelper.RepositoryEntry.COLUMN_NAME)),
                    taskDAO.list().filter { it.repository == id }
                ))

            }
        }

        return repositories.toList()
    }
}