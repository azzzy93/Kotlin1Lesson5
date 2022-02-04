package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ActivityDetailPlaylistBinding
import kg.geektech.kotlin1lesson5.utils.Constants

class DetailPlaylistActivity :
    BaseActivity<DetailPlaylistViewModel, ActivityDetailPlaylistBinding>(),
    DetailPlaylistAdapter.OnItemClick {

    override val viewModel: DetailPlaylistViewModel by lazy {
        ViewModelProvider(this)[DetailPlaylistViewModel::class.java]
    }
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
                        binding.appBar.visibility = View.VISIBLE
                        binding.fabPlay.visibility = View.VISIBLE
                        binding.rvPlaylistsDetail.visibility = View.VISIBLE
                        binding.includeNoInternet.root.visibility = View.GONE

                        binding.tvTitlePlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_TITLE)

                        binding.tvDescPlaylist.text =
                            intent.getStringExtra(Constants.KEY_PLAYLIST_DESC)

                        intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let { getData(it) }
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.appBar.visibility = View.GONE
                        binding.fabPlay.visibility = View.GONE
                        binding.rvPlaylistsDetail.visibility = View.GONE
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

    private fun getData(playlistId: String) {
        viewModel.getDetailPlaylists(playlistId, null).observe(this) {
            it.items?.let { it1 -> adapter.setList(it1) }
            val videoCount = it.pageInfo?.totalResults.toString() + " video series"
            binding.tvVideoCount.text = videoCount
        }
    }

    override fun initListener() {
        binding.tvBack.setOnClickListener {
            finish()
        }
    }

    override fun onClick(item: Item) {

    }
}