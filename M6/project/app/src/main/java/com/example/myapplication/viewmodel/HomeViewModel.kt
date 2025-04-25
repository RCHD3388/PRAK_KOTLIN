package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _tweets = MutableLiveData<List<TweetEntity>>()
    val tweets: LiveData<List<TweetEntity>>
        get() = _tweets

    private val _postErrorMessage = MutableLiveData<String>("")
    val postErrorMessage: LiveData<String>
        get() = _postErrorMessage

    private fun refreshList(){
        viewModelScope.launch {
            _tweets.value = App.db.tweetDao().fetch()
        }
    }

    fun init(){
        refreshList()
    }

    fun createNewTweet(tweet: String){
        val user = UserLoginViewModel.COloggedinUser
        viewModelScope.launch {
            if(tweet.isNotEmpty()){
                val tweetEntity = TweetEntity(user_username = user.username, user_name = user.name, tweet = tweet, like = 0, comment = 0, retweet = 0);
                App.db.tweetDao().insert(tweetEntity);
                refreshList()
            }else{
                _postErrorMessage.value = "Tweet tidak boleh kosong";
            }
        }
    }

    fun likeTweet(tweet: TweetEntity){

    }
    fun retweetTweet(tweet: TweetEntity){

    }
}