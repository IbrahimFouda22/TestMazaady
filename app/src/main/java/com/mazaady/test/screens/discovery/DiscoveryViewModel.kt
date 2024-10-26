package com.mazaady.test.screens.discovery

import android.app.Application
import com.mazaady.domain.usecase.ManageCategoryUseCase
import com.mazaady.test.base.BaseViewModel
import com.mazaady.test.base.ErrorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    application: Application,
    private val manageCategoryUseCase: ManageCategoryUseCase,
) : BaseViewModel<DiscoveryUiState>(application, DiscoveryUiState()) {
    init {
        getAllMainCats()
    }

    private fun getAllMainCats() {
        tryToExecute(
            function = {
                updateState {
                    it.copy(
                        isLoading = true
                    )
                }
                manageCategoryUseCase.getAllMainCats()
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        mainCats = items.map { item ->
                            item.toUiState()
                        },
                    )
                }
            },
            onError = ::handleError,
        )
    }

    fun getProperties(categoryId: Int) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(
                        isLoading = true, properties = emptyList(), optionChild = emptyList()
                    )
                }
                manageCategoryUseCase.getProperties(categoryId)
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        changeProperties = true,
                        properties = items.map { item ->
                            item.toUiState()
                        },
                    )
                }
            },
            onError = ::handleError,
        )
    }

    fun getOptionChild(optionId: Int) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(
                        isLoading = true, optionChild = emptyList()
                    )
                }
                manageCategoryUseCase.getOptionsChild(optionId)
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        optionChild = items.map { item ->
                            item.toUiState()
                        },
                    )
                }
            },
            onError = ::handleError,
        )
    }

    fun resetPropAndOptionChild(){
        updateState {
            it.copy(
                properties = emptyList(),
                optionChild = emptyList()
            )
        }
    }
    fun changeProperties(boolean: Boolean){
        updateState {
            it.copy(
                changeProperties = boolean
            )
        }
    }
    fun visibilityProcessTypeField(boolean: Boolean) {
        updateState {
            it.copy(
                visibilityFieldProcessType = boolean
            )
        }
    }

    fun resetError() {
        updateState {
            it.copy(
                error = ""
            )
        }
    }

    private fun handleError(error: ErrorUiState) {
        val errorMessage = when (error) {
            is ErrorUiState.NoInternet -> error.message
            is ErrorUiState.UnAuthException -> error.message
            is ErrorUiState.EmptyResponse -> error.message
            is ErrorUiState.Server -> error.message
        }
        updateState {
            it.copy(
                error = errorMessage,
                isLoading = false,
            )
        }
    }
}