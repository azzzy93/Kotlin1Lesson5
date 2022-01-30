package kg.geektech.kotlin1lesson5.remote

import kg.geektech.kotlin1lesson5.model.YouTubePlaylists
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String? = null
    ): Call<YouTubePlaylists>

}