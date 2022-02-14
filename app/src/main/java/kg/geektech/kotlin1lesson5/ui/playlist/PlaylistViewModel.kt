package kg.geektech.kotlin1lesson5.ui.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.repository.Repository

class PlaylistViewModel(private val repository: Repository) : BaseViewModel() {

    private val _pageToken = MutableLiveData<String?>()

    val getPlaylists = _pageToken.switchMap {
        repository.getPlaylists(it)
    }

    fun setPageToken(pageToken: String?) {
        _pageToken.postValue(pageToken)
    }
}