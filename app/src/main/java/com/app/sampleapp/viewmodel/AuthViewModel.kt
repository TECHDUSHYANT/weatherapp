package com.app.sampleapp.viewmodel

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.sampleapp.data.model.LoginModel.User
import com.app.sampleapp.repository.AuthRepository
import com.app.sampleapp.ui.utils.networkState.AuthState
import com.app.sampleapp.ui.utils.networkState.UserState
import com.app.sampleapp.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authRepository: AuthRepository,
) : ViewModel() {

    private val _userRegistrationStatus = MutableLiveData<Resource<AuthResult>>()
    val userRegistrationStatus: LiveData<Resource<AuthResult>> = _userRegistrationStatus

    private val _userSignUpStatus = MutableLiveData<Resource<AuthResult>>()
    val userSignUpStatus: LiveData<Resource<AuthResult>> = _userSignUpStatus

    private val _userdata = MutableLiveData<Resource<AuthResult>>()
    val userData: LiveData<Resource<AuthResult>> = _userdata

    fun createUser(userEmailAddress: String, userLoginPassword: String) {
        var error =
            if (userEmailAddress.isEmpty() ||  userLoginPassword.isEmpty()) {
                "Empty Strings"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmailAddress).matches()) {
                "Not a valid Email"
            } else null

        error?.let {
            _userRegistrationStatus.postValue(Resource.Error(it))
            return
        }
        _userRegistrationStatus.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.Main) {
            val registerResult = authRepository.createUser(userEmailAddress = userEmailAddress, userLoginPassword = userLoginPassword)
            _userRegistrationStatus.postValue(registerResult)
        }
    }

    fun signInUser(userEmailAddress: String, userLoginPassword: String) {
        if (userEmailAddress.isEmpty() || userLoginPassword.isEmpty()) {
            _userSignUpStatus.postValue(Resource.Error("Empty Strings"))
        } else {
            _userSignUpStatus.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                val loginResult = authRepository.login(userEmailAddress, userLoginPassword)

                _userSignUpStatus.postValue(loginResult)
            }
        }


    }

    fun getData() : LiveData<User> {
        return authRepository.getResponseFromDatabase()
    }

    fun uploadprofileData(user: User,selectedPhotoUri: Uri?) : LiveData<User> {
        return authRepository.uploadImage(user,selectedPhotoUri)
    }

}