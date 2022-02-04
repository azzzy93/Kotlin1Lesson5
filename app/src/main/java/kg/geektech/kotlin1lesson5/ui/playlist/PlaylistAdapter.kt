package kg.geektech.kotlin1lesson5.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.kotlin1lesson5.core.extensions.load
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ListForPlaylistsBinding

class PlaylistAdapter(private val onItemClick: OnItemClick?) :
    RecyclerView.Adapter<PlaylistAdapter.MainViewHolder>() {

    private var list = arrayListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistAdapter.MainViewHolder {
        val binding =
            ListForPlaylistsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ListForPlaylistsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            item.snippet?.thumbnails?.high?.url?.let { binding.ivBanner.load(it) }
            binding.tvTitle.text = item.snippet?.localized?.title
            val videoCount = item.contentDetails?.itemCount.toString() + " video series"
            binding.tvVideoCount.text = videoCount

            itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}