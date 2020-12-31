package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.model.Entity
import com.example.viewmodel.model.EntityRepository
import kotlin.random.Random

class EntityListViewModel : ViewModel() {

    private val entityRepository = EntityRepository()

    private val entityLiveData = MutableLiveData<List<Entity>>(entityRepository.generateEntities(20))

    private val showToastLiveData = MutableLiveData<Int>()

    val entities : LiveData<List<Entity>>
        get() = entityLiveData

    val showToast : LiveData<Int>
        get() = showToastLiveData

    fun addEntity(entity: Entity) {
        entityLiveData.postValue(entityRepository.addEntity(entity, entityLiveData.value.orEmpty()))
    }

    fun deleteEntity(position : Int) {
        entityLiveData.postValue(entityRepository.deleteEntity(position, entityLiveData.value.orEmpty()))
        showToastLiveData.postValue(position)
    }
}