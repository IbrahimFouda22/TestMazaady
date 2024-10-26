package com.mazaady.test.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mazaady.domain.exception.EmptyResponseException
import com.mazaady.domain.exception.InternalServerException
import com.mazaady.domain.exception.NoInternetException
import com.mazaady.domain.exception.ServerException
import com.mazaady.domain.exception.UnAuthException
import com.mazaady.test.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(
    application: Application,
    initialState: S,
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private fun getString(resourceId: Int): String {
        return getApplication<Application>().getString(resourceId)
    }
    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorUiState) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        runWithErrorCheck(
            onError = onError,
            dispatcher = dispatcher
        ) {
            onSuccess(function())
        }
    }

    protected fun updateState(updater: (S) -> S) {
        _state.update(updater)
    }

    private fun runWithErrorCheck(
        onError: (ErrorUiState) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        function: suspend () -> Unit,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                function()
            } catch (e: EmptyResponseException) {
                onError(ErrorUiState.EmptyResponse(getString(R.string.no_response)))
            }catch (e: UnAuthException) {
                onError(ErrorUiState.UnAuthException(getString(R.string.not_authorized)))
            } catch (e: NoInternetException) {
                onError(ErrorUiState.NoInternet(getString(R.string.no_internet)))
            } catch (e: ServerException) {
                onError(ErrorUiState.Server(getString(R.string.server_error)))
            } catch (e: InternalServerException) {
                onError(ErrorUiState.Server(getString(R.string.server_error)))
            } catch (e: Exception) {
                onError(ErrorUiState.Server(e.message.toString()))
            }
        }
    }
}