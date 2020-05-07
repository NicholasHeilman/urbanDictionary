package com.e.nikeurbanapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.e.nikeurbanapp.R
import com.e.nikeurbanapp.databinding.SearchListKeyBinding
import com.e.nikeurbanapp.extensions.bind
import com.e.nikeurbanapp.model.Definition

class DefinitionAdapter() : DataBoundListAdapter<Definition>(
        diffCallback = object : DiffUtil.ItemCallback<Definition>() {
            override fun areItemsTheSame(oldItem: Definition, newItem: Definition): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Definition, newItem: Definition): Boolean =
                oldItem == newItem
        }
    ) {
    private var onClick: View.OnClickListener? = null

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding =
        parent.bind(R.layout.search_list_key)

    override fun bind(binding: ViewDataBinding, item: Definition) {
        when (binding) {
            is SearchListKeyBinding ->binding.definition = item
        }
        binding.root.tag = item
        binding.root.setOnClickListener(onClick)
    }
}