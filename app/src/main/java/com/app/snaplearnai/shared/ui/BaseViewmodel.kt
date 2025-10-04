package com.app.snaplearnai.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewmodel<VS>(initialState: VS) : ViewModel() {

    private val _uiState: MutableStateFlow<VS> =
        MutableStateFlow<VS>(initialState)

    val uiStateFlow: StateFlow<VS> = _uiState.asStateFlow()

    val uiState: VS get() = _uiState.value

    protected fun setState(update: (VS) -> VS) {
        viewModelScope.launch {
            _uiState.emit(update(_uiState.value))
        }
    }

    private val eventChannel = Channel<OneTimeEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    protected fun sendEvent(event: OneTimeEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}