package com.example.doancoso3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Repository.MainRepository

class MainViewModel : ViewModel(){
    private val respository = MainRepository()
    
    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return respository.loadBanner()
    }
}