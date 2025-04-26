package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.App
import com.example.myapplication.entity.NotificationEntity
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.entity.UTLikeEntity
import com.example.myapplication.entity.UTRetweetEntity
import com.example.myapplication.entity.dto.UserTweetDto
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val _tweets = MutableLiveData<List<UserTweetDto>>()
    val tweets: LiveData<List<UserTweetDto>>
        get() = _tweets

    private val _postErrorMessage = MutableLiveData<String>("")
    val postErrorMessage: LiveData<String>
        get() = _postErrorMessage

    private fun refreshList(){
        viewModelScope.launch {
            val originalTweets: List<TweetEntity> = App.db.tweetDao().fetchByUsername(UserLoginViewModel.COActivateUser.username)

            val userLikes: List<UTLikeEntity>? = App.db.likeDao().get(UserLoginViewModel.COloggedinUser.username)
            _tweets.value = originalTweets.map { tweetEntity ->
                UserTweetDto(
                    tweetEntity = tweetEntity,
                    originalEntity = if(tweetEntity.retweeted_from != null){
                        App.db.tweetDao().get(tweetEntity.retweeted_from)
                    } else {null},
                    current_user_like_status = if(userLikes != null){
                        val userLike = if(tweetEntity.retweeted_from != null){
                            userLikes.find { it.tweet_id == tweetEntity.retweeted_from }
                        }else{
                            userLikes.find { it.tweet_id == tweetEntity.tweet_id }
                        }
                        if(userLike != null && userLike.liked == true){
                            true
                        }else{
                            false
                        }
                    }else{
                        false
                    }
                )
            }
        }
    }

    fun init(){
        refreshList()
    }
    

    fun likeTweet(tweet: UserTweetDto){
        var currentTweet = tweet.tweetEntity

        viewModelScope.launch {
            if(currentTweet.retweeted_from != null){
                currentTweet = App.db.tweetDao().get(currentTweet.retweeted_from!!)!!;
            }
            val user = UserLoginViewModel.COloggedinUser
            val currentLike = App.db.likeDao().getOne(user.username, currentTweet.tweet_id);

            if(currentLike != null) {
                var updatedLike: UTLikeEntity? = null;
                if(currentLike.deleted == false){
                    updatedLike = currentLike.copy(liked = false, deleted = true)
                }else{
                    updatedLike = currentLike.copy(liked = !currentLike.liked)
                }
                App.db.likeDao().update(updatedLike)
            }else{
                App.db.likeDao().insert(UTLikeEntity(
                    user_username = user.username,
                    tweet_id = currentTweet.tweet_id,
                    deleted = false,
                    liked = true
                ));
                createNotif(user.username, currentTweet.user_username, "${user.username} liked your tweet!")
            }
            var newCurrentTweet =
                currentTweet.copy(
                    like = App.db.likeDao().getTweetLikes(
                        currentTweet.tweet_id)?.count {
                        it.liked
                    } ?: 0
                )
            App.db.tweetDao().update(newCurrentTweet)

            // update retweeted data
            updateRetweeted(newCurrentTweet)
        }
    }
    fun retweetTweet(tweet: UserTweetDto){
        val user = UserLoginViewModel.COloggedinUser
        var currentTweet = tweet.tweetEntity
        viewModelScope.launch {
            if(currentTweet.retweeted_from != null){
                currentTweet = App.db.tweetDao().get(currentTweet.retweeted_from!!)!!;
            }
            val currentRetweet = App.db.retweetDao().get(user.username, currentTweet.tweet_id.toString());
            val currentRetweetedTweet = App.db.tweetDao().getRetweeted(user.username, currentTweet.tweet_id);

            if(currentRetweet != null) {
                var updatedRetweet: UTRetweetEntity? = null;
                if(currentRetweet.deleted == false){
                    updatedRetweet = currentRetweet.copy(retweeted = false, deleted = true)
                }else{
                    updatedRetweet = currentRetweet.copy(retweeted = !currentRetweet.retweeted)
                }
                App.db.retweetDao().update(updatedRetweet)
                if(currentRetweetedTweet != null){
                    App.db.tweetDao().delete(currentRetweetedTweet)
                }else{
                    App.db.tweetDao().insert(TweetEntity(
                        user_username = user.username,
                        user_name = user.name,
                        tweet = currentTweet.tweet,
                        retweeted_from = currentTweet.tweet_id,
                        like = 0, comment = 0, retweet = 0
                    ));
                }
            }else{
                App.db.retweetDao().insert(UTRetweetEntity(
                    user_username = user.username,
                    tweet_id = currentTweet.tweet_id,
                    deleted = false,
                    retweeted = true
                ));
                App.db.tweetDao().insert(TweetEntity(
                    user_username = user.username,
                    user_name = user.name,
                    tweet = currentTweet.tweet,
                    retweeted_from = currentTweet.tweet_id,
                    like = 0, comment = 0, retweet = 0
                ));
                createNotif(user.username, currentTweet.user_username, "${user.username} reposted your tweet!")
            }
            var newCurrentTweet = currentTweet.copy(
                retweet = App.db.retweetDao().getTweetRetweets(
                    currentTweet.tweet_id)?.count {
                    it.retweeted
                } ?: 0
            )
            App.db.tweetDao().update(newCurrentTweet)

            // update retweeted data
            updateRetweeted(newCurrentTweet)
        }
    }
    fun updateRetweeted(tweet: TweetEntity){
        viewModelScope.launch {
            val retweetedTweetList = App.db.tweetDao().getRetweetedTweetFromOriginal(tweet.tweet_id)
            if(retweetedTweetList != null){
                retweetedTweetList.forEach {
                    App.db.tweetDao().update(tweet.copy(
                        retweeted_from = tweet.tweet_id,
                        tweet_id = it.tweet_id,
                        user_name = it.user_name,
                        user_username = it.user_username
                    ))
                }
            }
            refreshList()
        }
    }

    fun createNotif(from_username: String, to_username: String, note: String){
        viewModelScope.launch {
            App.db.notificationDao().insert(
                NotificationEntity(
                    from_username = from_username,
                    to_username = to_username,
                    note = note
                )
            )
        }
    }
}