package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geektech.kotlin1lesson5.R
import kg.geektech.kotlin1lesson5.core.extensions.load
import kg.geektech.kotlin1lesson5.data.model.Item
import kg.geektech.kotlin1lesson5.databinding.ListForPlaylistDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailPlaylistAdapter(private val onItemClick: OnItemClick?) :
    RecyclerView.Adapter<DetailPlaylistAdapter.MainViewHolder>() {

    private var list = arrayListOf<Item>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPlaylistAdapter.MainViewHolder {
        val binding =
            ListForPlaylistDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailPlaylistAdapter.MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Item>) {
        this.list.clear()
        this.list.addAll(list)
        this.list.forEachIndexed { index, _ ->
            notifyItemChanged(index)
        }
    }

    inner class MainViewHolder(private val binding: ListForPlaylistDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            if (item.snippet?.thumbnails?.high?.url != null) {
                binding.ivBanner.load(item.snippet.thumbnails.high.url)
            } else {
                binding.ivBanner.setImageResource(R.drawable.youtube_logo)
            }
            binding.tvTitle.text = item.snippet?.title
            val videoCount = item.contentDetails?.videoPublishedAt
            binding.tvVideoTime.text = formatDate(videoCount)

            itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
            itemView.setOnLongClickListener {
                onItemClick?.onLongClick(item)
                return@setOnLongClickListener true
            }
        }

        private fun formatDate(dateString: String?): String? {
            return if (dateString == null) {
                null
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date: Date = format.parse(dateString) ?: Calendar.getInstance().time
                val newFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
                newFormat.format(date)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
        fun onLongClick(item: Item)
    }
}