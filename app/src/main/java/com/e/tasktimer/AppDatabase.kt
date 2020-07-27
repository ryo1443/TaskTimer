package com.e.tasktimer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * このアプリのBasicとなるdatabaseクラス
 * このクラスを使うべき唯一のクラスは、 [AppProvide].
 */

private const val TAG = "AppDatabase"

private const val DATABASE_NAME = "TaskTimer.db"
private const val DATABASE_VERSION = 1

internal class AppDatabase private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        Log.d(TAG, "AppDatabase: initialising")
    }

    override fun onCreate(db: SQLiteDatabase) {
        //CREATE TABLE Tasks(_id integer primary key not null, Name text not null, Description text, SortOrder integer);
        Log.d(TAG, "onCreate: starts")
        val sSQL = """create table ${TasksContract.TABLE_NAME} (
            |${TasksContract.Columns.ID} integer primary key not null,
            |${TasksContract.Columns.TASK_NAME} text not null,
            |${TasksContract.Columns.TASK_DESCRIPTION} text,
            |${TasksContract.Columns.TASK_SORT_ORDER} integer);
        """.trimMargin().replaceIndent(" ")
        Log.d(TAG, sSQL)
        db.execSQL(sSQL) //?でnullを解決するより、関数の引数をnon-nullにする方が安全。
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "onUpgrade: starts")
        when(oldVersion) {
            1 -> {
                //upgrade logic from v1
            }
            else -> throw IllegalStateException("onUpgrade() with unknown newVersion: $newVersion")
        }
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase)

//    companion object {
//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase =
//            instance ?: synchronized(this) {
//                instance ?: AppDatabase(context).also { instance = it }
//            }
//    }
}