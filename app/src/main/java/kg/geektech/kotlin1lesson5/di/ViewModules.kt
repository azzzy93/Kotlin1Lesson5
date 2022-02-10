package kg.geektech.kotlin1lesson5.di

import kg.geektech.kotlin1lesson5.ui.detail_playlist.DetailPlaylistViewModel
import kg.geektech.kotlin1lesson5.ui.playlist.PlaylistViewModel
import kg.geektech.kotlin1lesson5.ui.video_play.YoutubeVideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { PlaylistViewModel(get()) }
    viewModel { DetailPlaylistViewModel(get()) }
    viewModel { YoutubeVideoViewModel(get()) }
}