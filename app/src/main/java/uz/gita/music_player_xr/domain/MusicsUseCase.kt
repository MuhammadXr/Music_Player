package uz.gita.music_player_xr.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.music_player_xr.data.model.MusicData

// Created by Jamshid Isoqov an 10/7/2022
interface MusicsUseCase {


    suspend fun deleteMusic(musicData: MusicData)

    suspend fun refreshMusics()

    fun getAllMusics():Flow<List<MusicData>>


}