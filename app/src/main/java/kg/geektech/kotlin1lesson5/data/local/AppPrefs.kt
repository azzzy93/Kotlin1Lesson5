package kg.geektech.kotlin1lesson5.data.local

import android.content.Context
import com.google.gson.Gson
import kg.geektech.kotlin1lesson5.data.model.YouTubePlaylists

class AppPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("youtube_api", Context.MODE_PRIVATE)

    var isOnBoard: Boolean
        get() = prefs.getBoolean("onBoard", false)
        set(value) = prefs.edit().putBoolean("onBoard", value).apply()

    var youtube: YouTubePlaylists
        get() {
            val json = prefs.getString("youtube", null)
            return Gson().fromJson(json, YouTubePlaylists::class.java)
        }
        set(value) {
            prefs.edit().putString("youtube", Gson().toJson(value)).apply()
        }
}