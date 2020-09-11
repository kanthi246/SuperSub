package com.example.supersub.ViewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.supersub.Models.Explore
import com.example.supersub.Network.SuperSubApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExploreViewModel(application: Application):BaseViewModel(application) {

    // The internal MutableLiveData list that stores the most recent response
    private val _exploreresponse = MutableLiveData<Explore>()
    // The external immutable LiveData for the response list
    val exploreresponse: LiveData<Explore>
        get() = _exploreresponse

    val loaderror=MutableLiveData<Boolean>() //it'll show error while retriving the data
    val loading=MutableLiveData<Boolean>() //it'll show loading so that data is not yet arrived from api

    init {
        loaderror.value=false
        loading.value=false
    }
    fun refresh(){
        getExploreData()
    }

    private fun getExploreData() {
        loading.value=true
        launch {
            SuperSubApiService.getClient.getExplore()
                .enqueue(object :Callback<Explore>{
                    override fun onResponse(call: Call<Explore>, response: Response<Explore>) {
                        if(response.isSuccessful){
                            _exploreresponse.value=response.body()
                            loaderror.value=false
                            loading.value=false
                        }
                    }

                    override fun onFailure(call: Call<Explore>, t: Throwable) {
                        loading.value=false
                        loaderror.value=true
                        Log.d("error",""+t.message)
                        Toast.makeText(getApplication(),t.message,Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

}