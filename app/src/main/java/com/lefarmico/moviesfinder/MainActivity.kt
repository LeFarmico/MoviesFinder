package com.lefarmico.moviesfinder

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.moviesfinder.adapter.ParentRecyclerViewAdapter
import com.lefarmico.moviesfinder.adapterDelegates.ContainerAdapter
import com.lefarmico.moviesfinder.adapterDelegates.container.ParentModel
import com.lefarmico.moviesfinder.groupie.MovieContainerItem
import com.lefarmico.moviesfinder.groupie.MovieModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private var actionMode: ActionMode? = null
    lateinit var recyclerView: RecyclerView

    private val movieModel =
        com.lefarmico.moviesfinder.adapterDelegates.item.MovieItem(R.drawable.jaws, "Челюсти")
    private val parentModel = ParentModel(
        "Hottest",
        arrayListOf(movieModel, movieModel, movieModel,
            movieModel, movieModel, movieModel,))

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
            (adapter as ContainerAdapter).items = arrayListOf(parentModel, parentModel, parentModel, parentModel, parentModel, parentModel)
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
            adapter = ParentRecyclerViewAdapter(arrayListOf(parentModel, parentModel, parentModel))
        }
    }
    private fun initGroupieRecycler(){
        val movieCategories = listOf(
            GetMovieContainers().getPopularMovies(),
            GetMovieContainers().getPopularMovies(),
        )

        val onItemClickListener = OnItemClickListener { item, _ ->
            if (item is MovieModel) {
                Toast.makeText(this@MainActivity, "Clicked on movie called: " + item.movieContent.title, Toast.LENGTH_SHORT).show()
            }
        }

        recycler_parent.adapter = groupAdapter.apply {
            addAll(movieCategories)
        }
        groupAdapter.setOnItemClickListener { item, view ->
            if (item is MovieContainerItem) {
                Toast.makeText(this@MainActivity, "Clicked on container", Toast.LENGTH_SHORT).show()
            }
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
