package uz.gita.music_player_xr.presentation.screens.songs

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.music_player_xr.R
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.databinding.PageSongsBinding
import uz.gita.music_player_xr.presentation.screens.dialogs.AboutDialog
import uz.gita.music_player_xr.presentation.viewmodels.HomeViewModel
import uz.gita.music_player_xr.presentation.viewmodels.impl.HomeViewModelImpl
import uz.gita.music_player_xr.service.MusicService
import uz.gita.music_player_xr.utils.MusicPlaying


@AndroidEntryPoint
class SongsPage : Fragment(R.layout.page_songs), ServiceConnection {

    private val binding: PageSongsBinding by viewBinding(PageSongsBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val adapter by lazy { SongsAdapter(true) }
    private var musicService: MusicService? = null
    private var isFirst = true
    private lateinit var list: List<MusicData>

    private var isPlaying = MusicPlaying.mediaPlayer?.isPlaying ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(requireContext(), MusicService::class.java)
        requireActivity().bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSongs.adapter = adapter

        viewModel.getAllMusics().onEach {
            list = it
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        adapter.setItemClickListener {
            MusicPlaying.setMusicList(list)
            MusicPlaying.clickMusic(it)
        }

        binding.about.setOnClickListener {
            val dialog = AboutDialog(requireContext())
            dialog.show()
        }

        binding.iconPlayOrPause.setOnClickListener {
            isPlaying = MusicPlaying.mediaPlayer?.isPlaying ?: false
            if (isPlaying) {
                MusicPlaying.pauseMusic()
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_play_arrow_54)
            } else {
                MusicPlaying.startMusic()
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
            }
            isPlaying = !isPlaying
        }

        MusicPlaying.playingObserver.observe(viewLifecycleOwner) {
            if (it) {
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_play_arrow_54)
            }
        }

        binding.iconNextSong.setOnClickListener {
            MusicPlaying.clickMusic(MusicPlaying.positionMusic + 1)
            isPlaying = true
            binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
        }

        binding.bottomMusicContainer.setOnClickListener {
            findNavController().navigate(SongsPageDirections.actionSongsPageToMusicDetailScreen())
        }

        MusicPlaying.musicLiveData.observe(viewLifecycleOwner, musicObserver)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    @SuppressLint("SetTextI18n")
    private val musicObserver = Observer<MusicData> {

        if (!binding.bottomMusicContainer.isVisible) {
            binding.bottomMusicContainer.visibility = View.VISIBLE
        }


        binding.apply {
            Glide.with(requireContext())
                .load(it.image)
                .placeholder(R.drawable.img)
                .into(imgAlbum)
            tvSingerSong.text = "${it.artistName} - ${it.title}"
            tvSingerSong.maxLines = 2
            isPlaying = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFirst) {
            if (MusicPlaying.mediaPlayer?.isPlaying == true) {
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                binding.iconPlayOrPause.setImageResource(R.drawable.ic_baseline_play_arrow_54)
            }
        } else {
            isFirst = false
        }
    }
}