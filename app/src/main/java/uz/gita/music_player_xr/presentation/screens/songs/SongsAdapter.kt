package uz.gita.music_player_xr.presentation.screens.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.music_player_xr.R
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.databinding.ItemSongBinding
import uz.gita.music_player_xr.utils.extensions.longToMin

class SongsAdapter(private val isFavourite: Boolean) :
    ListAdapter<MusicData, SongsAdapter.SongsViewHolder>(SongsAdapterComparator) {

    private var itemItemClick: ((Int) -> Unit)? = null


    inner class SongsViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {
                itemItemClick?.invoke(absoluteAdapterPosition)
            }
        }

        fun bind() {

            val data = getItem(absoluteAdapterPosition)

            binding.tvSongName.apply {
                text = data.title
                isSelected = true
                setSingleLine()
            }

            binding.tvSongDescription.apply {
                isSelected = true
                setSingleLine()
                text = data.artistName
            }

            binding.tvSongDuration.text = data.duration.longToMin()

            Glide
                .with(binding.root.context)
                .load(data.image)
                .placeholder(R.drawable.img)
                .into(binding.ivArtist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder(
            ItemSongBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_song,
                    parent,
                    false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind()
    }

    fun setItemClickListener(block: (Int) -> Unit) {
        itemItemClick = block
    }
}

object SongsAdapterComparator : DiffUtil.ItemCallback<MusicData>() {

    override fun areItemsTheSame(oldItem: MusicData, newItem: MusicData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MusicData, newItem: MusicData): Boolean {

        return oldItem.id == newItem.id && oldItem.path == newItem.path && oldItem.favourite == newItem.favourite
    }
}