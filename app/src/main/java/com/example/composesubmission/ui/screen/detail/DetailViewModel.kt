package com.example.composesubmission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesubmission.data.Repository
import com.example.composesubmission.model.Agent
import com.example.composesubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Agent>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Agent>> get() = _uiState

    fun getAgentById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAgentById(id))
        }
    }

    fun addToFavorite(id: String, isFavorite:Boolean) =
        viewModelScope.launch {
            repository.updateAgent(id, isFavorite)
                .collect{
                    if (it) getAgentById(id)
                }
        }

}