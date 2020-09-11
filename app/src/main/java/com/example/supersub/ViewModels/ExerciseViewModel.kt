package com.example.supersub.ViewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.supersub.Models.Exercise
import com.example.supersub.Network.SuperSubApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseViewModel(application: Application):BaseViewModel(application) {
    // The internal MutableLiveData list that stores the most recent response
    private val _exerciseresponse = MutableLiveData<Exercise>()
    // The external immutable LiveData for the response list
    val exerciseresponse: LiveData<Exercise>
        get() = _exerciseresponse

    val loaderror= MutableLiveData<Boolean>() //it'll show error while retriving the data
    val loading= MutableLiveData<Boolean>() //it'll show loading so that data is not yet arrived from api

    init {
        loaderror.value=false
        loading.value=false
    }

    fun getExerciseData(id:String) {
        loading.value=true
        launch {
           SuperSubApiService.getClient.getExercise(id).enqueue(
               object :Callback<Exercise>{
                   override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                       if (response.isSuccessful){
                           _exerciseresponse.value=response.body()
                           loaderror.value=false
                           loading.value=false
                       }
                   }

                   override fun onFailure(call: Call<Exercise>, t: Throwable) {
                       loading.value=false
                       loaderror.value=true
                       Log.d("error",""+t.message)
                       Toast.makeText(getApplication(),t.message, Toast.LENGTH_SHORT).show()
                   }
               }
           )
        }
    }
}