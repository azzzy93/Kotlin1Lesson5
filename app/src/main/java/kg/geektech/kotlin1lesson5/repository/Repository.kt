package kg.geektech.kotlin1lesson5.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geektech.kotlin1lesson5.App.Companion.apiService
import kg.geektech.kotlin1lesson5.BuildConfig
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getPlaylists(pageToken: String?): LiveData<YouTubePlaylists> {
        val data = MutableLiveData<YouTubePlaylists>()

        apiService.getPlaylists(
            Constants.PART,
            Constants.CHANNEL_ID,
            BuildConfig.API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        ).enqueue(object : Callback<YouTubePlaylists> {
            override fun onResponse(
                call: Call<YouTubePlaylists>,
                response: Response<YouTubePlaylists>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<YouTubePlaylists>, t: Throwable) {
            }
        })

        return data
    }

    fun getDetailPlaylists(playlistId: String, pageToken: String?): LiveData<YouTubePlaylists> {
        val data = MutableLiveData<YouTubePlaylists>()

        apiService.getDetailPlaylists(
            Constants.PART,
            playlistId,
            BuildConfig.API_KEY,
            Constants.MAX_RESULTS,
            pageToken
        ).enqueue(object : Callback<YouTubePlaylists> {
            override fun onResponse(
                call: Call<YouTubePlaylists>,
                response: Response<YouTubePlaylists>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<YouTubePlaylists>, t: Throwable) {
            }
        })

        return data
    }
}