package com.e.nikeurbanapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.nikeurbanapp.R
import com.e.nikeurbanapp.adapter.DefinitionAdapter
import com.e.nikeurbanapp.databinding.FragmentSearchBinding
import com.e.nikeurbanapp.model.Definition
import com.e.nikeurbanapp.viewModel.MainViewModel
//import kotlinx.android.synthetic.main.fragment_search.*
//import kotlinx.android.synthetic.main.fragment_search.view.*
//import kotlinx.android.synthetic.main.snippet_toolbar.*
//import kotlinx.android.synthetic.main.snippet_toolbar.view.*

class SearchFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var adapter: DefinitionAdapter? = null
    var query: CharSequence? = ""
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // NO  OPERATION
        }

        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // NO  OPERATION
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val isBackSpace = query?.length ?: -1 >= s?.length ?: -1
            query = s.toString()
            binding.rvDefinitionList.visibility = View.VISIBLE
            binding.toolbar.ivClear.visibility = View.VISIBLE
            binding.welcomeView.cvWelcome.visibility = View.GONE
            binding.noResultView.cvNoResult.visibility = View.GONE

            s?.let {
                if (it.isNotEmpty() && !isBackSpace) {
                    viewModel.defineTerm(it.toString())
                    binding.indeterminateBar?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
//        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DefinitionAdapter()

        binding.toolbar.etToolbarSearch.addTextChangedListener(textWatcher)
//        activity?.et_toolbarSearch?.addTextChangedListener(textWatcher)
        view.apply {
            val ivClear: ImageView = findViewById(R.id.iv_Clear)

            //Clear Button
            ivClear.setOnClickListener {
                binding.toolbar.etToolbarSearch.text?.clear()
                binding.welcomeView.cvWelcome.visibility = View.VISIBLE
                binding.rvDefinitionList.visibility = View.GONE
                binding.toolbar.ivClear.visibility = View.INVISIBLE
            }

            //Filter Button
            binding.toolbar.btnFilter.setOnClickListener {
                showFilterMenu(it as AppCompatImageView)
            }

            //RecyclerView
            binding.rvDefinitionList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SearchFragment.adapter
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
            binding.indeterminateBar?.visibility = View.INVISIBLE
            if (it.list.isNullOrEmpty()) {
                binding.noResultView.cvNoResult.visibility = View.VISIBLE
            }
        })
    }

    private fun showFilterMenu(filter: AppCompatImageView) {
        viewModel.definitions.value?.list?.let {
            PopupMenu(filter.context, filter).apply {
                menuInflater.inflate(R.menu.filter_menu, menu)
                setOnMenuItemClickListener { item ->
                    val sortedList: List<Definition> = when (item.itemId) {
                        R.id.up_votes_ascending -> {
                            it.toMutableList().apply { sortBy { it.thumbsUp } }.toList()
                        }
                        R.id.up_votes_descending -> {
                            it.toMutableList().apply { sortByDescending { it.thumbsUp } }.toList()
                        }
                        R.id.down_votes_ascending -> {
                            it.toMutableList().apply { sortBy { it.thumbsDown } }.toList()
                        }
                        R.id.down_votes_descending -> {
                            it.toMutableList().apply { sortByDescending { it.thumbsDown } }.toList()
                        }
                        else -> it
                    }
                    adapter?.submitList(sortedList)
                    true
                }
            }.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}