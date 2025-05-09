package com.example.doancoso3.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doancoso3.Domain.BannerModel
import com.example.doancoso3.Domain.CategoryModel
import com.example.doancoso3.Domain.FoodModel
import com.google.firebase.database.*

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banners")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Bạn có thể log lỗi ở đây nếu muốn
            }
        })

        return listData
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý lỗi nếu cần
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>> {
        val listData = MutableLiveData<MutableList<FoodModel>>()
        val ref = firebaseDatabase.getReference("Foods")
        val query: Query = ref.orderByChild("CategoryId").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<FoodModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(FoodModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun searchByName(queryText: String): LiveData<Pair<List<CategoryModel>, List<FoodModel>>> {
        val resultLiveData = MutableLiveData<Pair<List<CategoryModel>, List<FoodModel>>>()

        val categoriesRef = firebaseDatabase.getReference("Category")
        val foodsRef = firebaseDatabase.getReference("Foods")

        val categoriesResult = mutableListOf<CategoryModel>()
        val foodsResult = mutableListOf<FoodModel>()

        var categoryDone = false
        var foodDone = false

        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val item = child.getValue(CategoryModel::class.java)
                    if (item != null && item.name.contains(queryText, ignoreCase = true)) {
                        categoriesResult.add(item)
                    }
                }
                categoryDone = true
                if (foodDone) {
                    resultLiveData.value = Pair(categoriesResult, foodsResult)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        foodsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val item = child.getValue(FoodModel::class.java)
                    if (item != null && item.Title.contains(queryText, ignoreCase = true)) {
                        foodsResult.add(item)
                    }
                }
                foodDone = true
                if (categoryDone) {
                    resultLiveData.value = Pair(categoriesResult, foodsResult)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return resultLiveData
    }


}
