package uz.gita.music_player_xr.utils

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import uz.gita.music_player_xr.App
import uz.gita.music_player_xr.R
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.service.MusicService

object MusicPlaying {

    var mediaPlayer: MediaPlayer? = null

    val musicLiveData = MutableLiveData<MusicData>()

    var positionMusic = -1

    var mutableMusicPosition = MutableLiveData<Int>()

    var listMusics: List<MusicData> = emptyList()

    private var stateMusic: Int = 0

    private var intent: Intent? = null

    val playingObserver = MutableLiveData<Boolean>()

    fun clickMusic(pos: Int) {

        positionMusic = if (pos == listMusics.size) {
            0
        } else {
            pos
        }

        mutableMusicPosition.value = positionMusic

        musicLiveData.value = listMusics[positionMusic]

        if (mediaPlayer != null && mediaPlayer?.isPlaying!!) {
            mediaPlayer?.stop()
        }

        intent = Intent(App.context, MusicService::class.java)

        mediaPlayer = MediaPlayer.create(App.context, Uri.parse(listMusics[positionMusic].path))
        startMusic()

        mediaPlayer?.setOnBufferingUpdateListener { mp, percent ->
            if (percent == 100) {
                clickMusic(positionMusic + 1)
            }
        }

    }

    fun setMusicList(listMusics: List<MusicData>) {
        this.listMusics = listMusics
    }

    fun startMusic() {
        mediaPlayer?.start()
        stateMusic = R.drawable.ic_baseline_pause_24
        intent?.putExtra("stateMusic", stateMusic)
        ContextCompat.startForegroundService(App.context, intent!!)
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
        stateMusic = R.drawable.ic_baseline_play_arrow_54
        intent?.putExtra("stateMusic", stateMusic)
        ContextCompat.startForegroundService(App.context, intent!!)
    }
}