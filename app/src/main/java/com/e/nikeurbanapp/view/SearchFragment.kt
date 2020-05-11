package com.e.nikeurbanapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.nikeurbanapp.R
import com.e.nikeurbanapp.adapter.DefinitionAdapter
import com.e.nikeurbanapp.model.Definition
import com.e.nikeurbanapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var adapter: DefinitionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DefinitionAdapter()
        view.apply {

            //Search Button
            btn_Search.setOnClickListener {
                indeterminateBar?.visibility = View.VISIBLE
                viewModel.defineTerm(et_toolbarSearch.text.toString())
                clearSearch()
            }
            //Clear Button
            iv_Clear.setOnClickListener {
                et_toolbarSearch.text?.clear()
                iv_Clear.visibility = View.INVISIBLE
            }
            //Filter Button
            btn_filter.setOnClickListener {
                filterMenu(it as AppCompatImageView)
            }
            //RecyclerView
            rv_definitionList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SearchFragment.adapter
            }
        }
    }

    private fun clearSearch(){
            iv_Clear.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        searchInputObservable()
    }

    private fun searchInputObservable() {
        viewModel.definitions.observe(this, Observer {
            adapter?.submitList(it.list)
            indeterminateBar?.visibility = View.INVISIBLE
        })
    }

    private fun filterMenu(filter: AppCompatImageView) {
        viewModel.definitions.value?.list?.let {
            PopupMenu(filter.context, filter).apply {
                menuInflater.inflate(R.menu.filter_menu, menu)
                setOnMenuItemClickListener { item ->
                    val sortedList: List<Definition> = when(item.itemId) {
                        R.id.up_votes_ascending -> { it.toMutableList().apply { sortBy { it.thumbsUp } }.toList() }
                        R.id.up_votes_descending -> { it.toMutableList().apply { sortByDescending { it.thumbsUp } }.toList() }
                        R.id.down_votes_ascending ->  { it.toMutableList().apply { sortBy { it.thumbsDown } }.toList() }
                        R.id.down_votes_descending ->{ it.toMutableList().apply { sortByDescending { it.thumbsDown } }.toList() }
                        else -> it
                    } as List<Definition>
                    adapter?.submitList(sortedList)
                    true
                }
            }.show()
        }
    }
}


//        popupMenu.menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)
//        popupMenu.setOnMenuItemClickListener{ item ->
//            when(item.itemId) {
//                R.id.up_votes_ascending ->
//                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
//                R.id.up_votes_descending ->
//                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
//                R.id.down_votes_ascending ->
//                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
//                R.id.down_votes_descending ->
//                    Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
//            }
//            true
//        }
//        popupMenu.show()


