package kg.geektech.kotlin1lesson5.ui.playlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.kotlin1lesson5.core.extensions.showToast
import kg.geektech.kotlin1lesson5.core.network.Status
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ActivityPlaylistBinding
import kg.geektech.kotlin1lesson5.ui.detail_playlist.DetailPlaylistActivity
import kg.geektech.kotlin1lesson5.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : BaseActivity<PlaylistViewModel, ActivityPlaylistBinding>(),
    PlaylistAdapter.OnItemClick {

    override val viewModel: PlaylistViewModel by viewModel()
    private val adapter: PlaylistAdapter by lazy {
        PlaylistAdapter(this)
    }

    override fun initView() {
        initRecyclerView()
        viewModel.setPageToken(null)
    }

    private fun initRecyclerView() {
        binding.rvPlaylists.apply {
            layoutManager = LinearLayoutManager(this@PlaylistActivity)
            adapter = this@PlaylistActivity.adapter
        }
    }

    override fun haveInternet() {
        binding.includeNoInternet.root.visibility = View.GONE
        binding.rvPlaylists.visibility = View.VISIBLE
        getData()
    }

    override fun noInternet() {
        binding.rvPlaylists.visibility = View.GONE
        binding.includeNoInternet.root.visibility = View.VISIBLE
    }

    private fun getData() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.getPlaylists.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it?.data?.items?.let { it1 -> adapter.setList(it1) }
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
            checkInternet()
        }
    }
}