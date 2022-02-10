package kg.geektech.kotlin1lesson5.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kg.geektech.kotlin1lesson5.BuildConfig.API_KEY
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.data.remote.ApiService
import kg.geektech.kotlin1lesson5.utils.Constants
import kotlinx.coroutines.Dispatchers

class Repository(private val apiService: ApiService) {

    fun getPlaylists(pageToken: String?): LiveData<Resource<YouTubePlaylists?>> =
        liveData(Dispatchers.IO) {

            emit(Resource.loading())

            val response = apiService.getPlaylists(
                Constants.PART,
                Constants.CHANNEL_ID,
                API_KEY,
                Constants.MAX_RESULTS,
                pageToken
            )

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.body(), response.code()))
            }
        }

    fun getDetailPlaylists(
        playlistId: String,
        pageToken: String?
    ): LiveData<Resource<YouTubePlaylists?>> =
        liveData(Dispatchers.IO) {

            emit(Resource.loading())

            val response = apiService.getDetailPlaylists(
                Constants.PART,
                playlistId,
                API_KEY,
                Constants.MAX_RESULTS,
                pageToken
            )

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.body(), response.code()))
            }
        }

    fun getVideos(videosId: String): LiveData<Resource<YouTubePlaylists>> =
        liveData(Dispatchers.IO) {
            val response = apiService.getVideos(
                Constants.PART,
                videosId,
                API_KEY
            )

            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.message(), response.body(), response.code()))
            }
        }
}