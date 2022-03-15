package com.zemoga.zemogatest.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.zemogatest.data.model.PostDataModel
import com.zemoga.zemogatest.domain.DetailsRepository
import com.zemoga.zemogatest.domain.PostRepository
import com.zemoga.zemogatest.presenter.ui.PostState
import com.zemoga.zemogatest.utils.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application): AndroidViewModel(application), CoroutineScope {


    private val states: MutableLiveData<ScreenState<PostState>> = MutableLiveData()
    private val repository = DetailsRepository(application)



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


    fun fetchComments(postId: Long) {
        states.value = ScreenState.Loading
        viewModelScope.launch {
            repository.getComments(postId)?.body()?.let { body ->
                states.value = ScreenState.Render(PostState.ShowCommentsData(body))
            } ?: run {
                states.value = ScreenState.ErrorServer
            }
        }
    }

    fun addPostToFavorite(id: Long, userId: Long, title: String, body: String){
        viewModelScope.launch {
            repository.addToFavorite(id, userId, title, body)
        }

    }

    fun removeFromFavorite(id: Long){
        viewModelScope.launch {
            repository.deletePost(id)

        }
    }

    fun isFavorite(id: Long){
         viewModelScope.launch {
            states.value =  ScreenState.Render(PostState.highlightIfFavorite(repository.isFavorite(id)))
        }
    }

    fun deletePost(id: Long){
        viewModelScope.launch {
            repository.deletePost(id)

        }
        viewModelScope.launch {
            repository.deleteFromPosts(id)

        }
    }

}

