package com.e.tasktimer

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_list_items.*


class TaskViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
}

private const val TAG = "CursorRecyclerViewAdapt"
class CursorRecyclerViewAdapter(private var cursor: Cursor?) :
        RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        Log.d(TAG, "onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_items, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: starts")

        val cursor = cursor //スマートキャストの問題を避ける

        if (cursor == null || cursor.count == 0) {
            Log.d(TAG, "onBindViewHolder: providing instructions")
            holder.til_name.setText("Instructions")
            holder.til_description.setText("Use the add button (+) in the toolbar above to create new tasks." +
                    "\n\nTasks with lower sort orders will be placed higher up the list." +
                    "Tasks with the same sort order will be sorted alphabetically." +
                    "\n\nTapping a task will start the timer for that task (and will stop the timer for any previous task that was being timed)." +
                    "\n\nEach task has Edit and Delete buttons if you want to change the details or remove the task.")
            holder.til_edit.visibility = View.GONE
            holder.til_delete.visibility = View.GONE
        } else {
            if (!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position")
            }

            //カーソルからデータオブジェクトを作る
            val task = Task(
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_NAME)),
                cursor.getString(cursor.getColumnIndex(TasksContract.Columns.TASK_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(TasksContract.Columns.TASK_SORT_ORDER)))
            //IDはコンストラクタで設定されない
            task.id = cursor.getLong(cursor.getColumnIndex(TasksContract.Columns.ID))
            holder.til_name.text = task.name
            holder.til_description.text = task.description
            holder.til_edit.visibility = View.VISIBLE       //TODO: add onClick
            holder.til_delete.visibility = View.VISIBLE     //TODO: add onClick
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}