package com.example.composesubmission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesubmission.data.Repository
import com.example.composesubmission.model.Agent
import com.example.composesubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Agent>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Agent>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllAgent() {
        viewModelScope.launch {
            repository.getAllAgent()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun searchAgent(name: String) {
        _query.value = name
        viewModelScope.launch {
            repository.searchAgent(_query.value)
                .catch {
            _uiState.value = UiState.Error(it.message.toString())
        }
            .collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}