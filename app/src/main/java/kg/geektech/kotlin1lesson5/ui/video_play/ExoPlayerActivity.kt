package kg.geektech.kotlin1lesson5.ui.video_play

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.databinding.ActivityExoPlayerBinding

class ExoPlayerActivity : BaseActivity<BaseViewModel, ActivityExoPlayerBinding>(), Player.Listener {

    override val viewModel: BaseViewModel = BaseViewModel()
    private var exoPlayer: ExoPlayer? = null

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityExoPlayerBinding {
        return ActivityExoPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        internetConnectionChek()
        initPlayer()

        if (savedInstanceState != null) {
            val mediaItem = savedInstanceState.getInt("MediaItem")
            val seekTime = savedInstanceState.getLong("SeekTime")
            exoPlayer?.seekTo(mediaItem, seekTime)
        }
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().also {
            binding.player.player = it
            it.addListener(this)
            val mediaItem1 =
                MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
            it.addMediaItem(mediaItem1)
            it.prepare()
            it.play()
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
                        exoPlayer?.play()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.includeNoInternet.root.visibility = View.VISIBLE
                        binding.containerForInternetConnection.visibility = View.GONE
                        exoPlayer?.pause()
                    }
                }
            }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        exoPlayer?.let { outState.putLong("SeekTime", it.currentPosition) }
        exoPlayer?.let { outState.putInt("MediaItem", it.currentMediaItemIndex) }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.release()
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