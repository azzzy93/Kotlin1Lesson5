package kg.geektech.kotlin1lesson5.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.geektech.kotlin1lesson5.BuildConfig.API_KEY
import kg.geektech.kotlin1lesson5.`object`.Constants
import kg.geektech.kotlin1lesson5.base.BaseViewModel
import kg.geektech.kotlin1lesson5.model.YouTubePlaylists
import kg.geektech.kotlin1lesson5.remote.ApiService
import kg.geektech.kotlin1lesson5.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun playlist(pageToken: String?): LiveData<YouTubePlaylists> {
        return getPlaylists(pageToken)
    }

    private fun getPlaylists(pageToken: String?): LiveData<YouTubePlaylists> {
        val data = MutableLiveData<YouTubePlaylists>()

        apiService.getPlaylists(Constants.PART, Constants.CHANNEL_ID, API_KEY, Constants.MAX_RESULTS, pageToken)
            .enqueue(object : Callback<YouTubePlaylists> {
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