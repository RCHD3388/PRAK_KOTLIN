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
import com.example.myapplication.entity.UTCommentEntity
import com.example.myapplication.entity.UserEntity
import kotlinx.coroutines.launch

class CommentViewModel: ViewModel() {
    private val _comments = MutableLiveData<List<UTCommentEntity>>()
    val comments: LiveData<List<UTCommentEntity>>
        get() = _comments

    private val _userTweetDto = MutableLiveData<UserTweetDto>()
    val userTweetDto: LiveData<UserTweetDto>
        get() = _userTweetDto

    private fun getOriginal(): TweetEntity{
        return userTweetDto.value!!.originalEntity ?: userTweetDto.value!!.tweetEntity
    }

    private fun refreshList(){
        viewModelScope.launch {
            _comments.value = App.db.commentDao().fetchByTweet(getOriginal().tweet_id)
            val userLike = App.db.likeDao().getOne(UserLoginViewModel.COloggedinUser.username, getOriginal().tweet_id);
            _userTweetDto.value = UserTweetDto(
                tweetEntity = App.db.tweetDao().get(_userTweetDto.value!!.tweetEntity.tweet_id)!!,
                originalEntity = if(_userTweetDto.value!!.originalEntity != null){
                    App.db.tweetDao().get(_userTweetDto.value!!.originalEntity!!.tweet_id)!!
                }else{
                    null
                },
                current_user_like_status = userLike?.liked ?: false
            )
        }
    }

    fun init(tweet: UserTweetDto){
        _userTweetDto.value = tweet
        refreshList()
    }

    fun createNewComment(user: UserEntity, comment: String){
        val currentTweet = getOriginal()
        viewModelScope.launch {
            val tweetComment = UTCommentEntity(
                user_name = user.name,
                user_username = user.username,
                tweet_id = currentTweet.tweet_id,
                comment = comment,
            );
            App.db.commentDao().insert(tweetComment);
            val newCurrentTweet = currentTweet.copy(
                comment = App.db.commentDao().fetchByTweet(getOriginal().tweet_id)
                    .count() ?: 0
            )
            App.db.tweetDao().update(newCurrentTweet)
            createNotif(user.username, currentTweet.user_username, "${user.username} replied: \"${comment}\"")

            updateRetweeted(newCurrentTweet)
        }
    }


    fun likeTweet(){
        val tweet = _userTweetDto.value!!
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
    fun retweetTweet(){
        val tweet = _userTweetDto.value!!
        val user = UserLoginViewModel.COloggedinUser
        var currentTweet = tweet.tweetEntity
        viewModelScope.launch {
            if(currentTweet.retweeted_from != null){
                currentTweet = App.db.tweetDao().get(currentTweet.retweeted_from!!)!!;
            }
            // kondisi retweeted post user dengan post terkait untuk saat ini di RETWEETED
            val currentRetweet = App.db.retweetDao().get(user.username, currentTweet.tweet_id.toString());
            // mendapatkan tweet hasil retweeted di TWEET
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