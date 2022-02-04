package kg.geektech.kotlin1lesson5.ui.playlist

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ActivityPlaylistBinding
import kg.geektech.kotlin1lesson5.ui.detail_playlist.DetailPlaylistActivity
import kg.geektech.kotlin1lesson5.utils.Constants

class PlaylistActivity : BaseActivity<PlaylistViewModel, ActivityPlaylistBinding>(),
    PlaylistAdapter.OnItemClick {

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }
    private val adapter: PlaylistAdapter by lazy {
        PlaylistAdapter(this)
    }

    override fun initView() {
        initRecyclerView()
        internetConnectionChek()
    }

    private fun initRecyclerView() {
        binding.rvPlaylists.apply {
            layoutManager = LinearLayoutManager(this@PlaylistActivity)
            adapter = this@PlaylistActivity.adapter
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
                        binding.rvPlaylists.visibility = View.VISIBLE
                        getData()
                    }
                }

                override fun onLost(network: Network) {
                    runOnUiThread {
                        binding.rvPlaylists.visibility = View.GONE
                        binding.includeNoInternet.root.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun getData() {
        viewModel.getPlaylists(null).observe(this) {
            it.items?.let { it1 -> adapter.setList(it1) }
        }
    }

    override fun onClick(item: Item) {
        Intent(this, DetailPlaylistActivity::class.java).apply {
            putExtra(Constants.KEY_PLAYLIST_ID, item.id)
            putExtra(Constants.KEY_PLAYLIST_TITLE, item.snippet?.title)
            putExtra(Constants.KEY_PLAYLIST_DESC, item.snippet?.description)
            startActivity(this)
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        binding.includeNoInternet.btnTryAgain.setOnClickListener {
            internetConnectionChek()
        }
    }
}