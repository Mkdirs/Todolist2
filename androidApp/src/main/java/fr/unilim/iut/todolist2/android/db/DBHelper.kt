package fr.unilim.iut.todolist2.android.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

enum class TableContract {
    TASK, REPOSITORY
}
class DBHelper(context:Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    companion object{
        const val VERSION = 1
        const val DB_NAME = "ToDoneDB"
    }

    object TaskEntry: BaseColumns {
        const val TABLE = "task"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DUE_DATE = "dueDate"
        const val COLUMN_STATE = "state"
        const val COLUMN_REPOSITORY = "repository"
    }

    object RepositoryEntry : BaseColumns{
        const val TABLE = "repository"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_task_table = """
            create table ${TaskEntry.TABLE} (
                ${TaskEntry.COLUMN_ID} INTEGER PRIMARY KEY,
                ${TaskEntry.COLUMN_NAME} TEXT NOT NULL,
                ${TaskEntry.COLUMN_DUE_DATE} TEXT,
                ${TaskEntry.COLUMN_STATE} INTEGER NOT NULL,
                ${TaskEntry.COLUMN_REPOSITORY} INTEGER,
                FOREIGN KEY (${TaskEntry.COLUMN_REPOSITORY}) REFERENCES ${RepositoryEntry.TABLE}(${RepositoryEntry.COLUMN_ID})
            )
        """.trimIndent()

        val create_repository_table = """
            create table ${RepositoryEntry.TABLE}(
                ${RepositoryEntry.COLUMN_ID} INTEGER PRIMARY KEY,
                ${RepositoryEntry.COLUMN_NAME} TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(create_repository_table)
        db?.execSQL(create_task_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TaskEntry.TABLE}")
        db?.execSQL("DROP TABLE IF EXISTS ${RepositoryEntry.TABLE}")
        onCreate(db)
    }
}



interface IntoContentValues{
    fun into():ContentValues
}