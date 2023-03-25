package com.app.sampleapp.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null,boolean: Boolean =false) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Done<T>(message: String="",boolean: Boolean,data: T? = null) : Resource<T>(data,message,boolean)

}