package kg.geektech.kotlin1lesson5.ui.video_play

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kg.geektech.kotlin1lesson5.BuildConfig.API_KEY
import kg.geektech.kotlin1lesson5.core.extensions.showToast
import kg.geektech.kotlin1lesson5.databinding.ActivityYoutubeVideoPlayerBinding

class YoutubeVideoPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var binding: ActivityYoutubeVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
    }

    private fun initView() {
        internetConnectionChek()

        if (intent != null) {
            binding.tvTitleVideo.text = intent.getStringExtra("VIDEO_TITLE")
            binding.tvVideoDesc.text = intent.getStringExtra("VIDEO_DESC")
        }
    }

    private fun initListener() {
        binding.tvBack.setOnClickListener {
            finish()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun internetConnectionChek() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkChangeFilter = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(networkChangeFilter,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        binding.includeNoInternet.root.visibility = View.GONE
                        binding.containerForInternetConnection.visibility = View.VISIBLE
                        initPlayer()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.includeNoInternet.root.visibility = View.VISIBLE
                        binding.containerForInternetConnection.visibility = View.GONE
                    }
                }
            }
        )
    }

    private fun initPlayer() {
        binding.player.initialize(API_KEY, this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(intent.getStringExtra("VIDEO_ID"))
        p1?.play()
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        showToast("Видео не доступно!")
    }
}