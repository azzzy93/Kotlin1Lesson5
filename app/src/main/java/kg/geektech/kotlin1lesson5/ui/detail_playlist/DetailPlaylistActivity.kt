package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.kotlin1lesson5.R
import kg.geektech.kotlin1lesson5.core.extensions.showToast
import kg.geektech.kotlin1lesson5.core.network.Status
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ActivityDetailPlaylistBinding
import kg.geektech.kotlin1lesson5.ui.video_play.ExoPlayerActivity
import kg.geektech.kotlin1lesson5.ui.video_play.YoutubeVideoPlayerActivity
import kg.geektech.kotlin1lesson5.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPlaylistActivity :
    BaseActivity<DetailPlaylistViewModel, ActivityDetailPlaylistBinding>(),
    DetailPlaylistAdapter.OnItemClick {

    override val viewModel: DetailPlaylistViewModel by viewModel()
    private val adapter: DetailPlaylistAdapter by lazy {
        DetailPlaylistAdapter(this)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initRecyclerView()
        internetConnectionChek()
    }

    private fun internetConnectionChek() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkChangeFilter = NetworkRequest.Builder().build()
        cm.registerNetworkCallback(networkChangeFilter,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    runOnUiThread {
                        binding.toolbar.visibility = View.VISIBLE
                        binding.coordinatorLayout.visibility = View.VISIBLE
                        binding.includeNoInternet.root.visibility = View.GONE

                        binding.tvTitlePlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_TITLE)

                        binding.tvDescPlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_DESC)

                        getData()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.toolbar.visibility = View.GONE
                        binding.coordinatorLayout.visibility = View.GONE
                        binding.includeNoInternet.root.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun initRecyclerView() {
        binding.rvPlaylistsDetail.apply {
            layoutManager = LinearLayoutManager(this@DetailPlaylistActivity)
            adapter = this@DetailPlaylistActivity.adapter
        }
    }

    private fun getData() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
        intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let { playlistId ->
            viewModel.getDetailPlaylists(playlistId, null).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.items?.let { it1 -> adapter.setList(it1) }
                        val videoCount =
                            it.data?.pageInfo?.totalResults.toString() + " video series"
                        binding.tvVideoCount.text = videoCount
                        viewModel.loading.postValue(false)
                    }
                    Status.ERROR -> {
                        showToast(it.message.toString())
                        viewModel.loading.postValue(false)
                    }
                    Status.LOADING -> {
                        viewModel.loading.postValue(true)
                    }
                }
            }
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

    override fun onClick(item: Item) {
        val status: String? = item.status?.privacyStatus
        if (status == "public") {
            Intent(this, ExoPlayerActivity::class.java).apply {
                putExtra(Constants.VIDEO_ID, item.contentDetails?.videoId)
                startActivity(this)
            }
        } else {
            showToast(getString(R.string.video_private))
        }
    }

    override fun onLongClick(item: Item) {
        val status: String? = item.status?.privacyStatus
        if (status == "public") {
            Intent(this, YoutubeVideoPlayerActivity::class.java).apply {
                putExtra(Constants.VIDEO_ID, item.contentDetails?.videoId)
                startActivity(this)
            }
        } else {
            showToast(getString(R.string.video_private))
        }
    }
}