package kg.geektech.kotlin1lesson5.ui.detail_playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Item>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ListForPlaylistDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            item.snippet?.thumbnails?.high?.url?.let { binding.ivBanner.load(it) }
            binding.tvTitle.text = item.snippet?.title
            val videoCount = item.contentDetails?.videoPublishedAt
            binding.tvVideoTime.text = formatDate(videoCount)

            itemView.setOnClickListener {
                onItemClick?.onClick(item)
            }
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        private fun formatDate(dateString: String?): String? {
            return if (dateString == null) {
                null
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val date: Date? = format.parse(dateString)
                val newFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
                newFormat.format(date)
            }
        }
    }

    interface OnItemClick {
        fun onClick(item: Item)
    }
}