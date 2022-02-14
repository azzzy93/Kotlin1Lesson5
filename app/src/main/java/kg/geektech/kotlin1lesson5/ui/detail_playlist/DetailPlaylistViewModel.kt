package kg.geektech.kotlin1lesson5.ui.detail_playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.repository.Repository

class DetailPlaylistViewModel(private val repository: Repository) : BaseViewModel() {

    private val _pageToken = MutableLiveData<String?>()
    private lateinit var playlistId: String

    val getDetailPlaylists = _pageToken.switchMap {
        repository.getDetailPlaylists(playlistId, it)
    }

    fun setParams(playlistId: String, pageToken: String?) {
        this.playlistId = playlistId
        _pageToken.postValue(pageToken)
    }
}