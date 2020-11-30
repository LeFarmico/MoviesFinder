package com.lefarmico.moviesfinder

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.adapter.ParentRecyclerViewAdapter
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.parent_recycler.*
import kotlinx.android.synthetic.main.parent_recycler.view.*


class MainActivity : AppCompatActivity() {

    private var actionMode: ActionMode? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initToolsBar()
        initDelegatesRecycler()

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
    private fun initRecycler() {
        recyclerView = recycler_parent
        recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            adapter = ParentRecyclerViewAdapter(ContainerDataFactory().getContainerModel(10, 10))
        }
    }
    private fun initActionToolBar(){
            actionMode = startActionMode(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                val inflater = mode?.menuInflater
                inflater?.inflate(R.menu.top_app_bar, menu)
                mode?.title = "Select option"
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.fav -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Add to favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    R.id.search -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Search movie",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    R.id.more -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Open menu",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    else -> false
                }
            }
            override fun onDestroyActionMode(mode: ActionMode?) {}
        })
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
}
