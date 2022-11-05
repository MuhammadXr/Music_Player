package uz.gita.music_player_xr.presentation.viewmodels

import kotlinx.coroutines.flow.Flow
import uz.gita.music_player_xr.data.model.MusicData

interface HomeViewModel {

    fun getAllMusics(): Flow<List<MusicData>>

    fun refreshAllMusics()
}