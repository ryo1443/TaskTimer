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

internal class AppDatabase constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        Log.d(TAG, "AppDatabase: initialising")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //CREATE TABLE Tasks(_id integer primary key not null, Name text not null, Description text, SortOrder integer);
        Log.d(TAG, "onCreate: starts")
        val sSQL = """create table ${TasksContract.TABLE_NAME} (
            |${TasksContract.Columns.ID} integer primary key not null,
            |${TasksContract.Columns.TASK_NAME} text not null,
            |${TasksContract.Columns.TASK_DESCRIPTION} text,
            |${TasksContract.Columns.TASK_SORT_ORDER} integer);
        """.trimMargin().replaceIndent(" ")
        Log.d(TAG, sSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}