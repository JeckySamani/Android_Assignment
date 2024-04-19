package com.app.assignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assignment.models.ImageModel
import com.app.assignment.network.NetworkResult
import com.app.assignment.repoes.GetImagesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetImagesViewModel @Inject constructor(private val repository: GetImagesRepo) :
    ViewModel() {

    private val _getImages: MutableLiveData<NetworkResult<List<ImageModel>>> = MutableLiveData()
    val getImages: LiveData<NetworkResult<List<ImageModel>>> = _getImages

    fun getImages(clientId: String, perPage: String, page: String) = viewModelScope.launch {
        repository.getImages(clientId, perPage, page).collect { values ->
            _getImages.value = values
        }
    }

}