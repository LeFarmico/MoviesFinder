package com.lefarmico.moviesfinder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lefarmico.moviesfinder.adapterDelegates.CategoryAdapter
import com.lefarmico.moviesfinder.adapterDelegates.item.Movie
import com.lefarmico.moviesfinder.adapterDelegates.item.MoviesCategory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

//    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
//    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        top_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.fav -> {
                    Toast.makeText(this, "Add to favorite", Toast.LENGTH_SHORT).show()
                    true
                }
            else -> false
            }
        }

        val adapter = CategoryAdapter()
        adapter.items = arrayListOf(
            MoviesCategory("Hottest",
            arrayListOf(
                Movie(R.drawable.jaws, "Челюсти"),
                Movie(R.drawable.jaws, "Челюсти"),
                Movie(R.drawable.jaws, "Челюсти"),
                Movie(R.drawable.jaws, "Челюсти"),
                Movie(R.drawable.jaws, "Челюсти"),
                Movie(R.drawable.jaws, "Челюсти"),
            ))
        )
        items_container.adapter = adapter

//        val movieCategories = listOf(
//            GetMovieContainers().getPopularMovies(),
//            GetMovieContainers().getPopularMovies(),
//        )
//
//        val onItemClickListener = OnItemClickListener { item, _ ->
//            if (item is MovieItem) {
//                Toast.makeText(this@MainActivity, "Clicked on movie called: " + item.movieContent.title, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        items_container.adapter = groupAdapter.apply {
//            addAll(movieCategories)
//        }
//        groupAdapter.setOnItemClickListener { item, view ->
//            if (item is MovieContainerItem) {
//                Toast.makeText(this@MainActivity, "Clicked on container", Toast.LENGTH_SHORT).show()
//            }
//        }

//        actionMode = startActionMode(object : ActionMode.Callback {
//            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//                val inflater = mode?.menuInflater
//                inflater?.inflate(R.menu.top_app_bar, menu)
//                mode?.title = "Select option"
//                return true
//            }
//
//            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//                return false
//            }
//
//            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
//                return when (item?.itemId) {
//                    R.id.fav -> {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Add to favorite",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        true
//                    }
//                    R.id.search -> {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Search movie",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        true
//                    }
//                    R.id.more -> {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Open menu",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        true
//                    }
//                    else -> false
//                }
//            }
//            override fun onDestroyActionMode(mode: ActionMode?) {}
//        })
    }
}