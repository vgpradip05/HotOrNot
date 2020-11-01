package com.pradip.profile.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pradip.core.dispatchers.Dispatcher
import com.pradip.core.networking.DataResult
import com.pradip.core.viewmodel.BaseUiModel
import com.pradip.core.viewmodel.BaseViewModel
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.profile.home.di.VideoListModule
import com.pradip.profile.home.interfaces.FetchProfile


class FetchVideosViewModel(
    private val discoverRepo: FetchProfile.Repository,
    private val dispatcher: Dispatcher

) : BaseViewModel(dispatcher) {


    /** Exposed LiveData **/

    private val _uiModel: MutableLiveData<ViewProfileUiModel> = MutableLiveData()

    val uiModel: LiveData<ViewProfileUiModel>
        get() = _uiModel


    fun getProfiles() {
        emitUiState(_uiModel, ViewProfileUiModel(loading = true))
        launchOnBack {
            when (val result = discoverRepo.getProfileList()) {
                is DataResult.Success -> {
                    val data = result.data
                    emitUiState(_uiModel, ViewProfileUiModel(profiles = data))
                }

                is DataResult.Failure -> {
                    val exception = result.ex
                    emitUiState(_uiModel, ViewProfileUiModel(error = exception))
                }
            }
        }
    }
    fun updateStatus(profile:ProfileL){
        launchOnBack {
            discoverRepo.updateStatus(profile)
        }
    }

    override fun onCleared() {
        VideoListModule.unload()
        super.onCleared()
    }
}


class ViewProfileUiModel(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val profiles: List<ProfileL>? = null
) : BaseUiModel()