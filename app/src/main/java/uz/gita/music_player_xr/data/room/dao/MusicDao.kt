package uz.gita.music_player_xr.data.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.music_player_xr.data.model.MusicData

// Created by Jamshid Isoqov an 10/7/2022

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(musicData: MusicData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusic(musicList: List<MusicData>)

    @Delete
    suspend fun deleteMusic(musicData: MusicData)

    @Query("SELECT*FROM musics")
    fun getAllMusics(): Flow<List<MusicData>>

    @Query("DELETE FROM musics")
    suspend fun deleteAllMusics()


    @Transaction
    suspend fun clearAndUpdateData(musicList: List<MusicData>) {
        deleteAllMusics()
        insertMusic(musicList)
    }

}