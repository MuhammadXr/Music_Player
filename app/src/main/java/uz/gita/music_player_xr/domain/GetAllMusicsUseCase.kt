package uz.gita.music_player_xr.domain

import uz.gita.music_player_xr.data.model.MusicData

// Created by Jamshid Isoqov an 10/7/2022
interface GetAllMusicsUseCase {
    suspend fun getAllMusics(): List<MusicData>
}