package kg.geektech.kotlin1lesson5.data.remote

import kg.geektech.kotlin1lesson5.BuildConfig
import kg.geektech.kotlin1lesson5.core.network.BaseDataSource
import kg.geektech.kotlin1lesson5.core.network.Resource
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.data.remote.ApiService
import kg.geektech.kotlin1lesson5.utils.Constants

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylists(pageToken: String?): Resource<YouTubePlaylists> {
        return getResult {
            apiService.getPlaylists(
                Constants.PART,
                Constants.CHANNEL_ID,
                BuildConfig.API_KEY,
                Constants.MAX_RESULTS,
                pageToken
            )
        }
    }

    suspend fun getDetailPlaylists(
        playlistId: String,
        pageToken: String?
    ): Resource<YouTubePlaylists> {
        return getResult {
            apiService.getDetailPlaylists(
                Constants.PART,
                playlistId,
                BuildConfig.API_KEY,
                Constants.MAX_RESULTS,
                pageToken
            )
        }
    }

    suspend fun getVideos(videosId: String): Resource<YouTubePlaylists> {
        return getResult {
            apiService.getVideos(
                Constants.PART,
                videosId,
                BuildConfig.API_KEY
            )
        }
    }
}