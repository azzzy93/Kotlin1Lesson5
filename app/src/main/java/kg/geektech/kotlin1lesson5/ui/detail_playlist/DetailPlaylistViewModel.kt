package kg.geektech.kotlin1lesson5.ui.detail_playlist

import androidx.lifecycle.LiveData
import kg.geektech.kotlin1lesson5.App
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists

class DetailPlaylistViewModel : BaseViewModel() {

    fun getDetailPlaylists(playlistId: String, pageToken: String?): LiveData<Resource<YouTubePlaylists?>> {
        return App.repository.getDetailPlaylists(playlistId, pageToken)
    }

}