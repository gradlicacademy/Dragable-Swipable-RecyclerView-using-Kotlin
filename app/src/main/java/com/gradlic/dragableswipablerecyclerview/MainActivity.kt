package com.gradlic.dragableswipablerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dragDropAdapter: DragDropAdapter
    private var itemsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createFakeItems()

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        dragDropAdapter = DragDropAdapter(itemsList)
        val myList : DragDropSwipeRecyclerView = findViewById(R.id.list)
        myList.layoutManager = LinearLayoutManager(this)
        myList.adapter = dragDropAdapter
        myList.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        myList.reduceItemAlphaOnSwiping = true


        val onItemSwipeListener = object : OnItemSwipeListener<String>{
            override fun onItemSwiped(
                position: Int,
                direction: OnItemSwipeListener.SwipeDirection,
                item: String
            ): Boolean {
                Log.d("MainMenu", "Position = $position, Direction = $direction, Item = $item")
                when(direction){
                    OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT -> {
                        Toast.makeText(applicationContext, "$item deleted", Toast.LENGTH_SHORT).show()
                        //delete code
                    }
                    OnItemSwipeListener.SwipeDirection.LEFT_TO_RIGHT ->{
                        Toast.makeText(applicationContext, "$item archived", Toast.LENGTH_SHORT).show()
                        //archive code

                    }
                    else -> return false
                }
                return false
            }
        }
        myList.swipeListener = onItemSwipeListener
        fabAddItem()
    }

    private fun createFakeItems(){
        for (i in 0..30){
            itemsList.add("items $i")
        }
    }

    private fun fabAddItem(){
        findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            Log.d("MainActivity", "Button pressed")
            val builder = AlertDialog.Builder(this)
            val inflater: LayoutInflater  = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)

            val editText: EditText = dialogLayout.findViewById(R.id.et_edittext)

            with(builder){
                setTitle("Add a note")
                setPositiveButton("Ok"){ dialog, which ->
                    dragDropAdapter.updateItem(editText.text.toString())

                    Toast.makeText(applicationContext, "Successfully added", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancel"){ dialog, which ->
                    Log.d("MainMenu", "Negative Button Clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }


}