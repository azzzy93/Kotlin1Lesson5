package kg.geektech.kotlin1lesson5.ui.video_play

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kg.geektech.kotlin1lesson5.R
import kg.geektech.kotlin1lesson5.core.extensions.showToast
import kg.geektech.kotlin1lesson5.core.network.Status
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.databinding.ActivityExoPlayerBinding
import kg.geektech.kotlin1lesson5.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExoPlayerActivity : BaseActivity<YoutubeVideoViewModel, ActivityExoPlayerBinding>(),
    Player.Listener {

    override val viewModel: YoutubeVideoViewModel by viewModel()
    private var exoPlayer: ExoPlayer? = null
    private var playWhenReady = true
    private var currentMediaItemIndex = 0
    private var currentPosition: Long = 0
    private var videoId: String = ""

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityExoPlayerBinding {
        return ActivityExoPlayerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        internetConnectionChek()
        intent.getStringExtra(Constants.VIDEO_ID)?.let { viewModel.setVideoId(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean("playWhenReady")
            currentMediaItemIndex = savedInstanceState.getInt("currentMediaItemIndex")
            currentPosition = savedInstanceState.getLong("currentPosition")
        }
    }

    override fun initViewModel() {
        viewModel.getVideos.observe(this) { resource ->
            if (resource.status == Status.SUCCESS) {
                binding.tvTitleVideo.text = resource.data?.items?.get(0)?.snippet?.title
                binding.tvVideoDesc.text =
                    resource.data?.items?.get(0)?.snippet?.description
                videoId = resource.data?.items?.get(0)?.id!!
                initPlayer()
            } else if (resource.status == Status.ERROR) {
                showToast(getString(R.string.something_went_wrong))
            }
        }
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().also {
            binding.player.player = it
            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(
                    ytFiles: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {
                    Log.d("Aziz", "onExtractionComplete: null")
                    if (ytFiles != null) {
                        Log.d("Aziz", "onExtractionComplete: not null")
                        val itag = 22
                        val audioTag = 140
                        val videoUrl = ytFiles[itag].url
                        val audioUrl = ytFiles[audioTag].url

                        val audioSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(audioUrl))
                        val videoSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(videoUrl))

                        it?.setMediaSource(
                            MergingMediaSource(
                                true, videoSource, audioSource
                            ), true
                        )
                        it?.prepare()
                        it?.seekTo(currentMediaItemIndex, currentPosition)
                        it?.addListener(this@ExoPlayerActivity)
                        it?.playWhenReady = playWhenReady
                    }
                }
            }.extract(videoId)
        }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.release()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (exoPlayer != null) {
            outState.putBoolean("playWhenReady", exoPlayer!!.playWhenReady)
            outState.putInt("currentMediaItemIndex", exoPlayer!!.currentMediaItemIndex)
            outState.putLong("currentPosition", exoPlayer!!.currentPosition)
        }

    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_BUFFERING) {
            binding.progressBar.isVisible = true
        } else if (playbackState == Player.STATE_READY) {
            binding.progressBar.isVisible = false
        }
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