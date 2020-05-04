package com.e.nikeurbanapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.nikeurbanapp.R
import com.e.nikeurbanapp.adapter.DefinitionAdapter
import com.e.nikeurbanapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_list.*
import kotlinx.android.synthetic.main.snippet_toolbar.*

class SearchListFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var adapter: DefinitionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DefinitionAdapter()

        view.apply {
            btn_Search.setOnClickListener {
                indeterminateBar?.visibility = View.VISIBLE
                viewModel.defineTerm(toolbarSearch.text.toString())
            }

            rv_definition.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SearchListFragment.adapter
            }
        }
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
}
