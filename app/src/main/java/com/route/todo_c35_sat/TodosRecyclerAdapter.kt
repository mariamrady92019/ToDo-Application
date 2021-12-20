package com.route.todo_c35_sat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.route.todo_c35_sat.database.model.Todo


class TodosRecyclerAdapter(var items: MutableList<Todo>?) :
    RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>()  {

    var onItemClicked: OnItemClicked? = null
  var todoItems : MutableList<Todo>?= items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false);
        return ViewHolder(view);
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!!.get(position);
        holder.title.setText(item.name)
        holder.description.setText(item.details)



       if(item.isDone==true){
            holder.done.setBackgroundResource(R.drawable.done_backgrouned)
            holder.title.setTextColor(Color.GREEN)
            holder.lineView.setBackgroundResource(R.drawable.done_backgrouned)

        }

        if (onItemClicked!= null) {
            holder.cardView.setOnClickListener(View.OnClickListener {
                onItemClicked?.onItemClicked(item)
            })

             holder.rightView.setOnClickListener(View.OnClickListener {
                 onItemClicked?.onItemClickedToBeDeleted(position,item)
             })


        }



    }

    fun changeData(newItems: MutableList<Todo>) {
        items = newItems;
        notifyDataSetChanged();// notify adapter that data has been changed
    }

    override fun getItemCount(): Int = items?.size ?: 0;



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val rightView: ImageView = itemView.findViewById(R.id.right_view)
        val cardView: CardView = itemView.findViewById(R.id.cardview)

        val done: ImageView = itemView.findViewById(R.id.mark_as_done)
        val lineView:View= itemView.findViewById(R.id.line_view)

    }


    interface OnItemClicked {
        fun onItemClicked(todo: Todo)
        fun onItemClickedToBeDeleted(position: Int, todo: Todo)


    }



}