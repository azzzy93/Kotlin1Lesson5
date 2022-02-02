package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.view.LayoutInflater
import kg.geektech.kotlin1lesson5.utils.Constants
import kg.geektech.kotlin1lesson5.core.ui.BaseActivity
import kg.geektech.kotlin1lesson5.core.ui.BaseViewModel
import kg.geektech.kotlin1lesson5.databinding.ActivityDetailPlaylistBinding
import kg.geektech.kotlin1lesson5.core.extensions.showToast

class DetailPlaylistActivity : BaseActivity<BaseViewModel, ActivityDetailPlaylistBinding>() {

    override val viewModel: BaseViewModel = BaseViewModel()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlaylistBinding {
        return ActivityDetailPlaylistBinding.inflate(layoutInflater)
    }

    override fun initView() {
        intent.getStringExtra(Constants.KEY_PLAYLIST_ID)?.let { showToast(it) }
    }
}