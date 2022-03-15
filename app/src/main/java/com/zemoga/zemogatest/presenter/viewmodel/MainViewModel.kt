package com.zemoga.zemogatest.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.zemoga.zemogatest.data.model.PostDataModel
import com.zemoga.zemogatest.domain.PostRepository
import com.zemoga.zemogatest.presenter.ui.PostState
import com.zemoga.zemogatest.utils.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

    private val states: MutableLiveData<ScreenState<PostState>> = MutableLiveData()
    private val repository = PostRepository(application)

    var characterFullList: MutableList<PostDataModel>? = mutableListOf()

    private val viewModelJob = Job()
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Default

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getState(): MutableLiveData<ScreenState<PostState>> {
        return states
    }

    fun getCharacterData(id: Long): PostDataModel? {
        return characterFullList?.firstOrNull { it.id == id }
    }

    fun fetchPostData() {
        states.value = ScreenState.Loading
        viewModelScope.launch {
            repository.getPost()?.body()?.let { body ->
                characterFullList = body.toMutableList()
                states.value = ScreenState.Render(PostState.ShowPostData(body))
                insertPosts(body)
            } ?: run {
                states.value = ScreenState.ErrorServer
            }
        }
    }

    private fun insertPosts(posts: List<PostDataModel>){
        viewModelScope.launch{
            for(post in posts){
                repository.insertPost(post)
            }

        }
    }

    fun getCachedPost(){
        states.value = ScreenState.Loading
        viewModelScope.launch {
            characterFullList = repository.getAllPosts().toMutableList()
            states.value  = ScreenState.Render(PostState.ShowPostData(repository.getAllPosts()))
        }
    }

    fun getFavorites(){
        states.value = ScreenState.Loading
        viewModelScope.launch {
            val modelPostList = ArrayList<PostDataModel>()
            for (fav in repository.getAllFavorites()){
                modelPostList.add(PostDataModel(fav.userId, fav.id,fav.title!!,fav.body!!))
            }
            states.value  = ScreenState.Render(PostState.ShowPostData(modelPostList))
        }
    }

    fun deleteAllPosts(){
        viewModelScope.launch {
            repository.deleteAll()
            characterFullList?.clear()
            states.value = ScreenState.Render(PostState.ShowPostData(ArrayList()))
        }
        viewModelScope.launch {
            repository.deleteAllFromFav()
        }
    }

    fun isFavorite(id: Long): LiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value =  repository.isFavorite(id)
        }

        return result
    }

}
