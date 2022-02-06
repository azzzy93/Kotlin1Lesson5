package kg.geektech.kotlin1lesson5.ui.playlist

import androidx.lifecycle.LiveData
import kg.geektech.kotlin1lesson5.App
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists

class PlaylistViewModel : BaseViewModel() {

    fun getPlaylists(pageToken: String?): LiveData<Resource<YouTubePlaylists?>> {
        return App.repository.getPlaylists(pageToken)
    }

}