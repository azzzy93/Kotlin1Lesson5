package kg.geektech.kotlin1lesson5.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.kotlin1lesson5.databinding.ListForPlaylistsBinding
import kg.geektech.kotlin1lesson5.extensions.load
import kg.geektech.kotlin1lesson5.model.Item

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var list = arrayListOf<Item>()
    private lateinit var onItemClick: OnItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val binding =
            ListForPlaylistsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    inner class MainViewHolder(private val binding: ListForPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            binding.ivBanner.load(item.snippet.thumbnails.high.url)
            binding.tvTitle.text = item.snippet.localized.title
            val videoCount = item.contentDetails.itemCount.toString() + " video series"
            binding.tvVideoCount.text = videoCount
            binding.tvPlaylist.text = item.kind

            itemView.setOnClickListener {
                onItemClick.onClick(item)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}