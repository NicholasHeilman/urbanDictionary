package com.e.nikeurbanapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.e.nikeurbanapp.model.UrbanResponse
import com.e.nikeurbanapp.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var definitionSearch: MutableLiveData<UrbanResponse> = MutableLiveData()
    val definitions: LiveData<UrbanResponse>
        get() = definitionSearch


    fun defineTerm(term: String) = viewModelScope.launch {
        definitionSearch.postValue(Repository.getDefinition(term))
    }
}