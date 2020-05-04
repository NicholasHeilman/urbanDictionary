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

    private var _definitions: MutableLiveData<UrbanResponse> = MutableLiveData()
    val definitions: LiveData<UrbanResponse>
        get() = _definitions


    fun defineTerm(term: String) = viewModelScope.launch {
        _definitions.postValue(Repository.getDefinition(term))
    }
}