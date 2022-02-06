package kg.geektech.kotlin1lesson5.data.remote

import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Response<YouTubePlaylists>

    @GET("playlistItems")
    suspend fun getDetailPlaylists(
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("key") key: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Response<YouTubePlaylists>

}