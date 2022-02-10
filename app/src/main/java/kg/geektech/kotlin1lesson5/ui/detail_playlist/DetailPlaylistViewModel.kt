package kg.geektech.kotlin1lesson5.ui.detail_playlist

import androidx.lifecycle.LiveData
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.repository.Repository

class DetailPlaylistViewModel(private val repository: Repository) : BaseViewModel() {

    fun getDetailPlaylists(
        playlistId: String,
        pageToken: String?
    ): LiveData<Resource<YouTubePlaylists?>> {
        return repository.getDetailPlaylists(playlistId, pageToken)
    }
}