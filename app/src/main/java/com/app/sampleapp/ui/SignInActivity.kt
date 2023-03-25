package com.app.sampleapp.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.app.sampleapp.R
import com.app.sampleapp.data.model.LoginModel.User
import com.app.sampleapp.databinding.SignActivityBinding
import com.app.sampleapp.utils.Resource
import com.app.sampleapp.utils.displayToast
import com.app.sampleapp.viewmodel.AuthViewModel
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {


    private lateinit var binding: SignActivityBinding

    private val authViewModel: AuthViewModel by viewModels()

    private var email = ""
    private var password = ""

    var selectedPhotoUri: Uri? = null
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val fireStoreDatabase = FirebaseStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.sign_activity)

      authViewModel.userRegistrationStatus.observe(this, androidx.lifecycle.Observer {
          when (it) {
              is Resource.Loading -> {
                  binding.progressCircular.isVisible = true
              }
              is Resource.Success -> {
                  binding.progressCircular.isVisible = false
                  email = binding.edtEmailID.text.toString()
                  password =binding.edtPassword.text.toString()

                  val user =
                      User(
                          uid = it.data!!.user!!.uid,
                          username =binding.edtusername.text.toString(),
                          image = "",
                          email = email,
                          shortbio =binding.edtshortbio.text.toString()
                      )


                  binding.progressCircular.visibility = View.VISIBLE
                  uploadImage(user,selectedPhotoUri)
              }
              is Resource.Error -> {
                  binding.progressCircular.isVisible = false
                  Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
              }
              else -> {}
          }
      })



        binding.profileImage.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }

        }


        binding.btnSignUp.setOnClickListener {
            with(binding) {
                email = edtEmailID.text.toString()
                password = edtPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.createUser(email, password)
                } else {
                    this@SignInActivity.displayToast("Email and Password Must be Entered.")
                }
            }
        }

    }
    fun uploadImage(user: User,selectedPhotoUri: Uri?){

        authViewModel.uploadprofileData(user,selectedPhotoUri).observe(this, {
            if (it!=null)
            {
                startActivity(Intent(this@SignInActivity, DashActivity::class.java))
            }

        })

    }
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                selectedPhotoUri = fileUri
                binding.profileImage.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}