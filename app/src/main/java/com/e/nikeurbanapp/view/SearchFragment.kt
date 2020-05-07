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
import com.e.nikeurbanapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.snippet_toolbar.*

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

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
//            //Filter Button
//            btn_filter.setOnClickListener {
//                let { filterMenu(it) }
//            }

            rv_definitionList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SearchFragment.adapter
            }
        }
    }

    private fun clearSearch(){
            iv_Clear?.visibility = View.VISIBLE
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

//    private fun filterMenu(filter: AppCompatImageView) {
//        viewModel.definitions.value?.list?.let{
//            PopupMenu(filter.context, filter).apply {
//                menuInflater.inflate(R.menu.filter_menu, menu)
//        }
//    }
//    }
}
