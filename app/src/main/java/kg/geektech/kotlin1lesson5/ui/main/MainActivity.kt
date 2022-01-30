package kg.geektech.kotlin1lesson5.ui.main

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.kotlin1lesson5.`object`.Constants
import kg.geektech.kotlin1lesson5.base.BaseActivity
import kg.geektech.kotlin1lesson5.databinding.ActivityMainBinding
import kg.geektech.kotlin1lesson5.model.Item
import kg.geektech.kotlin1lesson5.ui.detail_playlist.DetailPlaylistActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), MainAdapter.OnItemClick {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun getData() {
        val adapter = MainAdapter()
        adapter.setOnItemClick(this)
        binding.rvPlaylists.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPlaylists.adapter = adapter
        viewModel.playlist(null).observe(this) {
            if (it != null) {
                adapter.setList(it.items)
            }
        }
    }

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

    override fun onClick(item: Item) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra(Constants.KEY_PLAYLIST_ID, item.id)
        startActivity(intent)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        binding.includeNoInternet.btnTryAgain.setOnClickListener {
            internetConnectionChek()
        }
    }
}