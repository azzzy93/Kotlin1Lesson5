package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.view.LayoutInflater
import kg.geektech.kotlin1lesson5.`object`.Constants
import kg.geektech.kotlin1lesson5.base.BaseActivity
import kg.geektech.kotlin1lesson5.base.BaseViewModel
import kg.geektech.kotlin1lesson5.databinding.ActivityDetailPlaylistBinding
import kg.geektech.kotlin1lesson5.extensions.showToast

class DetailPlaylistActivity : BaseActivity<BaseViewModel, ActivityDetailPlaylistBinding>() {

    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override fun initView() {
        if (intent != null) {
            intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let { showToast(it) }
        }
    }
}