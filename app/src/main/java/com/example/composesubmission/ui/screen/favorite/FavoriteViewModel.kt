package com.example.composesubmission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesubmission.data.Repository
import com.example.composesubmission.model.Agent
import com.example.composesubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agent>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>> get() = _uiState

   fun getFavoriteAgent() {
       viewModelScope.launch {
           repository.getFavoriteAgent()
               .catch {
                   _uiState.value = UiState.Error(it.message.toString())
               }
               .collect {
                   _uiState.value = UiState.Success(it)
               }
       }
   }
}