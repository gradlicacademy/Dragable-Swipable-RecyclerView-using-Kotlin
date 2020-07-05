package com.gradlic.dragableswipablerecyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter

class DragDropAdapter(dataSet: MutableList<String>) : DragDropSwipeAdapter<String, DragDropAdapter.ViewHolder>(dataSet) {

    private var list: MutableList<String> = this.dataSet as MutableList<String>

    inner class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView){
        val itemText: TextView = itemView.findViewById(R.id.item_text)
        val dragIcon : ImageView = itemView.findViewById(R.id.drag_icon)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                Log.d("DragDropAdapter", list[position])
                Toast.makeText(itemView.context, dataSet[position], Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    override fun getViewToTouchToStartDraggingItem(
        item: String,
        viewHolder: DragDropAdapter.ViewHolder,
        position: Int
    ): View? {
        return viewHolder.dragIcon
    }

    override fun onBindViewHolder(
        item: String,
        viewHolder: DragDropAdapter.ViewHolder,
        position: Int
    ) {
        viewHolder.itemText.text = dataSet[position]
    }

    override fun onDragFinished(item: String, viewHolder: ViewHolder) {
        super.onDragFinished(item, viewHolder)
        Log.d("DragDropAdapter", "$dataSet")
    }

    fun updateItem(item: String){
        list.add(0, item)
        notifyItemInserted(0)

        Log.d("DragDropAdapter", "$list")
    }


}