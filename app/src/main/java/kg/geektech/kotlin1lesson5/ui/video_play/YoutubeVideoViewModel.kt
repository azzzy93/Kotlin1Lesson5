package kg.geektech.kotlin1lesson5.ui.video_play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.repository.Repository

class YoutubeVideoViewModel(private val repository: Repository) : BaseViewModel() {

    private val _videoId = MutableLiveData<String>()

    val getVideos = _videoId.switchMap {
        repository.getVideos(it)
    }

    fun setVideoId(videoId: String) {
        _videoId.postValue(videoId)
    }
}