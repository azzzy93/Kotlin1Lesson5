package kg.geektech.kotlin1lesson5.ui.video_play

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import kg.geektech.kotlin1lesson5.BuildConfig.API_KEY
import kg.geektech.kotlin1lesson5.R
import kg.geektech.kotlin1lesson5.core.extensions.showToast
import kg.geektech.kotlin1lesson5.core.network.Status
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.databinding.ActivityYoutubeVideoPlayerBinding
import kg.geektech.kotlin1lesson5.utils.Constants

class YoutubeVideoPlayerActivity :
    BaseActivity<YoutubeVideoViewModel, ActivityYoutubeVideoPlayerBinding>() {

    override val viewModel: YoutubeVideoViewModel by lazy {
        ViewModelProvider(this)[YoutubeVideoViewModel::class.java]
    }
    private lateinit var playerFragment: YouTubePlayerFragment
    private var youTubePlayer: YouTubePlayer? = null

    override fun initView() {
        internetConnectionChek()
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
                        youTubePlayer?.play()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.includeNoInternet.root.visibility = View.VISIBLE
                        binding.containerForInternetConnection.visibility = View.GONE
                        youTubePlayer?.pause()
                    }
                }
            }
        )
    }

    override fun initViewModel() {
        intent.getStringExtra(Constants.VIDEO_ID)?.let { it ->
            viewModel.getVideos(it).observe(this) { resource ->
                if (resource.status == Status.SUCCESS) {
                    binding.tvTitleVideo.text = resource.data?.items?.get(0)?.snippet?.title
                    binding.tvVideoDesc.text =
                        resource.data?.items?.get(0)?.snippet?.description
                    initPlayer(resource.data?.items?.get(0)?.id!!)
                } else if (resource.status == Status.ERROR) {
                    showToast(getString(R.string.something_went_wrong))
                }
            }
        }
    }

    private fun initPlayer(videosId: String) {
        playerFragment =
            fragmentManager.findFragmentById(R.id.playerYoutube) as YouTubePlayerFragment
        playerFragment.initialize(API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                if (p1 != null) {
                    p1.loadVideo(videosId)
                    p1.play()
                    youTubePlayer = p1
                }
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                showToast(getString(R.string.video_private))
            }
        })
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityYoutubeVideoPlayerBinding {
        return ActivityYoutubeVideoPlayerBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        binding.tvBack.setOnClickListener {
            finish()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}