package com.app.sampleapp.ui.utils.networkState

import com.app.sampleapp.data.model.LoginModel.User


data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)