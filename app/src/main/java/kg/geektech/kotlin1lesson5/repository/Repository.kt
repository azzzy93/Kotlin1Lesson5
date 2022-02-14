package kg.geektech.kotlin1lesson5.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource) {

    fun getPlaylists(pageToken: String?): LiveData<Resource<YouTubePlaylists>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylists(pageToken)
            emit(response)
        }

    fun getDetailPlaylists(
        playlistId: String,
        pageToken: String?
    ): LiveData<Resource<YouTubePlaylists>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getDetailPlaylists(playlistId, pageToken)
            emit(response)
        }

    fun getVideos(videosId: String): LiveData<Resource<YouTubePlaylists>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getVideos(videosId)
            emit(response)
        }
}