package com.app.sampleapp.repository


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.app.sampleapp.data.model.LoginModel.User
import com.app.sampleapp.ui.DashActivity
import com.app.sampleapp.ui.LoginActivity
import com.app.sampleapp.utils.Resource
import com.app.sampleapp.utils.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.type.DateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

import java.io.IOException
import java.util.*
import javax.inject.Inject

class AuthRepository
@Inject
constructor() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreDatabase = FirebaseStorage.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    suspend fun createUser(userEmailAddress: String,userLoginPassword: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val registrationResult = firebaseAuth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword).await()
                Resource.Success(registrationResult)
            }
        }
    }

    suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)
            }
        }
    }
    fun getResponseFromDatabase() : MutableLiveData<User> {

        val mutableLiveData = MutableLiveData<User>()
        val databaseReference: DatabaseReference = firebaseDatabase.getReference("users").child(firebaseAuth.currentUser!!.uid)
        databaseReference.get().addOnCompleteListener { task ->
            var response = User()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response = it.getValue(User::class.java)!!
                }
            } else {
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }
    fun uploadImage(user: User,selectedPhotoUri: Uri?) : MutableLiveData<User>{
        val mutableLiveData = MutableLiveData<User>()
        try{

            val profileReference =
                fireStoreDatabase.reference.child("userprofile").child(user.uid)
            selectedPhotoUri?.let { profileReference.putFile(it) }?.addOnCompleteListener{
                if (it.isSuccessful) {
                    profileReference.downloadUrl.addOnSuccessListener{

                        var userData  = User(user.uid,user.username ,it.toString(),user.email,user.shortbio)
                        firebaseDatabase.reference.child("users").child(user.uid).setValue(userData)
                        mutableLiveData.value = userData
                    }

                }

            }

        } catch (e: HttpException) {

        }
        return mutableLiveData
    }



}


