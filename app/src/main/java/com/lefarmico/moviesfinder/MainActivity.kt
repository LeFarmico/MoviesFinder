package com.lefarmico.moviesfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.parent_recycler.*
import kotlinx.android.synthetic.main.parent_recycler.view.*


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initToolsBar()
        initDelegatesRecycler()
        onFloatingActionButtonClick()

    }
    private fun initDelegatesRecycler(){
        recyclerView = recycler_parent
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = ContainerAdapter()
            (adapter as ContainerAdapter).items = ContainerDataFactory().getContainerModel(10, 10)
        }
    }
    private fun initToolsBar(){
        top_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.fav -> {
                    Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
    private fun onFloatingActionButtonClick(){
    }
}
