package kg.geektech.kotlin1lesson5.data.remote

import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
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
        @Query("pageToken") pageToken: String?
    ): Call<YouTubePlaylists>

    @GET("playlistItems")
    fun getDetailPlaylists(
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Call<YouTubePlaylists>

}