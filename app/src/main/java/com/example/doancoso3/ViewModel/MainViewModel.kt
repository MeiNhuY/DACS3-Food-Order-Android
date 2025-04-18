package com.example.doancoso3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Repository.MainRepository
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel


class MainViewModel : ViewModel(){
    private val respository = MainRepository()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return respository.loadBanner()
    }
    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return respository.loadCategory()
    }
    fun loadFiltered(id:String):LiveData<MutableList<FoodModel>>{
        return respository.loadFiltered(id)
    }
}

