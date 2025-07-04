package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.CaseMap.Title
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter (
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
        class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return todos.size
    }
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked :Boolean ){
        if (isChecked){
            tvTodoTitle.paintFlags =tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else {
            tvTodoTitle.paintFlags =tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }
    fun deleteDoneTodos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]

        val tvTodoTitle = holder.itemView.findViewById<TextView>(R.id.tvTodolist)
        val cbDone = holder.itemView.findViewById<android.widget.CheckBox>(R.id.cbDone)

        tvTodoTitle.text = curTodo.title
        cbDone.isChecked = curTodo.isChecked
        toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)

        cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(tvTodoTitle, isChecked)
            curTodo.isChecked = !curTodo.isChecked
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
       return TodoViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.iterm_todo,
               parent,
               false
           )
       )
    }
}
