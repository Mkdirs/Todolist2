package fr.unilim.iut.todolist2.android.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

abstract class Dao<T:IntoContentValues>(context:Context, private val contract:TableContract) {
    private val dbHelper:DBHelper
    private val writeDB:SQLiteDatabase
    protected val readDB:SQLiteDatabase
    init {
        dbHelper = DBHelper(context)
        writeDB = dbHelper.writableDatabase
        readDB = dbHelper.readableDatabase
    }

    abstract fun get(id:Int):T?
    abstract fun list():List<T>

    fun put(item:T) {
        val table = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.TABLE
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.TABLE
        }
        writeDB.insert(table, null, item.into())
    }
    fun update(id:Int, new:T){
        val table = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.TABLE
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.TABLE
        }

        val columnId = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.COLUMN_ID
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.COLUMN_ID
        }
        writeDB.update(table, new.into(), "$columnId = ?", arrayOf("$id"))
    }

    fun delete(id:Int){
        val table = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.TABLE
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.TABLE
        }

        val columnId = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.COLUMN_ID
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.COLUMN_ID
        }

        writeDB.delete(table, "$columnId = ?", arrayOf("$id"))
    }

    fun clear(){
        val table = when(contract){
            TableContract.TASK -> DBHelper.TaskEntry.TABLE
            TableContract.REPOSITORY -> DBHelper.RepositoryEntry.TABLE
        }
        writeDB.delete(table, null, null)
    }
}